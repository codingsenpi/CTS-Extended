package com.akslabs.circletosearch

import android.app.ActivityOptions
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log

/**
 * Pure background trigger receiver.
 * Allows companion bridges (like HyperCTS Bridge) to trigger Circle To Search
 * directly via broadcast without launching or touching any Activity windows.
 */
class TriggerReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_TRIGGER_SEARCH = "com.codingsenpi.ctsextended.action.TRIGGER_SEARCH"
        const val ACTION_TRIGGER_SEARCH_LEGACY = "com.akslabs.circletosearch.action.TRIGGER_SEARCH"
        private const val TAG = "CtsTriggerReceiver"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d(TAG, "Trigger broadcast received: ${intent?.action}")
        if (intent?.action != ACTION_TRIGGER_SEARCH && intent?.action != ACTION_TRIGGER_SEARCH_LEGACY) {
            return
        }

        // 1. Primary path: Accessibility service captures current screen with 0 window switches
        val a11y = CircleToSearchAccessibilityService.instance
        if (a11y != null) {
            CircleToSearchAccessibilityService.triggerCapture()
            return
        }

        // 2. Secondary path: VoiceInteractionService captures screenshot directly via system assistant session
        val voiceService = CircleToSearchVoiceService.instance
        if (voiceService != null) {
            Log.d(TAG, "Accessibility service not bound, delegating to active VoiceInteractionSession")
            try {
                voiceService.showSession(Bundle(), VoiceInteractionSession.SHOW_WITH_SCREENSHOT)
                return
            } catch (e: Exception) {
                Log.e(TAG, "Failed to show VoiceInteractionSession", e)
            }
        }

        // 3. Fallback: Launch AssistEntryActivity via PendingIntent with BAL exemption
        Log.w(TAG, "Neither service active, falling back to AssistEntryActivity")
        val fallback = Intent(context, AssistEntryActivity::class.java).apply {
            action = Intent.ACTION_ASSIST
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        try {
            val options = ActivityOptions.makeBasic()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                options.setPendingIntentBackgroundActivityStartMode(
                    ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED
                )
            }
            val pi = PendingIntent.getActivity(
                context,
                0,
                fallback,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                options.toBundle()
            )
            pi.send(context, 0, null, null, null, null, options.toBundle())
        } catch (e: Exception) {
            Log.e(TAG, "Failed PendingIntent fallback", e)
            try {
                context.startActivity(fallback)
            } catch (e2: Exception) {
                Log.e(TAG, "Failed direct fallback", e2)
            }
        }
    }
}

