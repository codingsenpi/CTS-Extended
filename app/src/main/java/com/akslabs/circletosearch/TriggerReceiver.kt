package com.akslabs.circletosearch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Pure background trigger receiver.
 * Allows companion bridges (like HyperCTS Bridge) to trigger Circle To Search
 * directly via broadcast without launching or touching any Activity windows.
 */
class TriggerReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_TRIGGER_SEARCH = "com.akslabs.circletosearch.action.TRIGGER_SEARCH"
        private const val TAG = "CtsTriggerReceiver"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d(TAG, "Trigger broadcast received: ${intent?.action}")
        val a11y = CircleToSearchAccessibilityService.instance
        if (a11y != null) {
            CircleToSearchAccessibilityService.triggerCapture()
        } else {
            Log.w(TAG, "CircleToSearchAccessibilityService not active, falling back to AssistEntryActivity")
            val fallback = Intent(context, AssistEntryActivity::class.java).apply {
                action = Intent.ACTION_ASSIST
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(fallback)
        }
    }
}

