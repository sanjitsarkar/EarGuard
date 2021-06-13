package com.xanjit.focusly.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.xanjit.focusly.R
import kotlin.properties.Delegates
import kotlin.random.Random

class CustomService : Service() {


    private var randNum: Int = 0
    var isRandOn: Boolean = false
    private val mBinder: IBinder = CustomServiceBinder()
    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    inner class CustomServiceBinder : Binder() {
        fun getService(): CustomService {
            return this@CustomService
        }
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(getString(R.string.app_name), "Rebind")

    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(getString(R.string.app_name), "onUnbind")
        return super.onUnbind(intent)


    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(getString(R.string.app_name), "onTaskRemoved")

    }

    override fun onDestroy() {
        super.onDestroy()
        stopRand()
        Log.d(getString(R.string.app_name), "Service is stopped")

    }

    fun getRandomNum(): Int {
        return randNum
    }

    fun stopRand() {
        isRandOn = false
    }
    fun startRandNoGen()
    {
        isRandOn = true
        Log.d(getString(R.string.app_name), "Service is started on ${Thread.currentThread().name}")
//        stopSelf()
        Thread {
            while (isRandOn) {
                try {


                    Thread.sleep(1000)
                    if (isRandOn)
                        randNum = Random.nextInt(100) + 50
                    Log.d(
                        getString(R.string.app_name),
                        "Service is started on ${Thread.currentThread().name} and Random Number is $randNum"
                    )

                } catch (e: InterruptedException) {
                    Log.d(getString(R.string.app_name), "Thread is interrupted")

                }
            }

        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        return super.onStartCommand(intent, flags, startId)
        isRandOn = true
        Log.d(getString(R.string.app_name), "Service is started on ${Thread.currentThread().name}")
//        stopSelf()
        Thread {
            while (isRandOn) {
                try {


                    Thread.sleep(1000)
                    if (isRandOn)
                        randNum = Random.nextInt(100) + 50
                    Log.d(
                        getString(R.string.app_name),
                        "Service is started on ${Thread.currentThread().name} and Random Number is $randNum"
                    )

                } catch (e: InterruptedException) {
                    Log.d(getString(R.string.app_name), "Thread is interrupted")

                }
            }

        }.start()
        return START_STICKY


    }
}