package com.wsdydeni.module_main.ui.me

import android.app.Service
import android.content.Intent
import android.os.Handler

import android.os.IBinder
import android.util.Log


/**
 * 自杀式服务--重启app
 */
class KillSelfService : Service() {

    private val handler: Handler = Handler()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e("KillSelfService","onStartCommand")
        val stopDelayed = intent.getLongExtra("Delayed", 2000)
        handler.postDelayed({
            val launchIntent: Intent? = packageManager.getLaunchIntentForPackage(application.packageName)
            launchIntent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(launchIntent)
            this@KillSelfService.stopSelf()
        }, stopDelayed)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}