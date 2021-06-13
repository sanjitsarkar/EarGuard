package com.xanjit.focusly.workers

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.xanjit.focusly.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.sql.Timestamp
import kotlin.time.seconds

var TAG = "EarGuard"

class GetHeadsetUsageTimeWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
//                    Log.d("EarGuard", "StartTime Worker")

        var startTime = inputData.getLong("startTime", System.currentTimeMillis())
//        var stopped = inputData.getBoolean("isStopped", false)

//        if (!isStopped and !stopped) {
//            Log.d("EarGuard", "StartTime Worker" + startTime)
        Log.d(TAG, isStopped.toString())
        Thread {
            while (!isStopped) {
                Thread.sleep(1000)
                var usageTime = System.currentTimeMillis()
                usageTime -= startTime
//                Log.d(TAG, usageTime.toString())
                if ((usageTime / 1000) == 5L) {
                    CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main)
                    {
//                            Toast.makeText(
//                                applicationContext,
//                                "Time limit exceeded", Toast.LENGTH_LONG
//                            ).show()
                        val notificationChannel = NotificationChannel(
                            "123",
                            "EarGuard",
                            NotificationManager.IMPORTANCE_HIGH
                        )
                        notificationChannel.description =
                            "You have exceeded the maximum usage limit of listening on headset"
                        notificationChannel.name = "EarGuard"
                        notificationChannel.lockscreenVisibility = 1
                        notificationChannel.shouldShowLights()
                        notificationChannel.enableVibration(true)
                        notificationChannel.setSound(
                            Uri.parse("R.raw.sad"),
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
                        )
                        notificationChannel.shouldVibrate()
                        var notificationManager =
                            applicationContext.getSystemService(NotificationManager::class.java)
                        notificationManager.createNotificationChannel(notificationChannel)
                        notificationManager.notify(
                            123, NotificationCompat.Builder(
                                applicationContext, "123"
                            ).setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setLargeIcon(
                                    BitmapFactory.decodeResource(
                                        applicationContext.resources,
                                        R.drawable.ic_launcher_foreground
                                    )
                                )
                                .setSound(
                                    Uri.parse("R.raw.sad")
                                )

                                .setContentTitle("EarGuard")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setContentText("You have exceeded the maximum usage limit of listening on headset")
                                .setAutoCancel(false)
                                .setChannelId("123")
                                .build()
                        )

                    }

                    break
                }


            }
        }.start()

//        }
        return Result.success()
    }

//    @SuppressLint("RestrictedApi")
//    override fun isRunInForeground(): Boolean {
//        return super.isRunInForeground()
//    }

    override fun onStopped() {
        Log.d(TAG, "Stopped Worker")
        super.onStopped()

    }

}