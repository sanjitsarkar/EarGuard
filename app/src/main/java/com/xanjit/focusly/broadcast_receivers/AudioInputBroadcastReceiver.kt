package com.xanjit.focusly.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.*
import com.xanjit.focusly.workers.GetHeadsetUsageTimeWorker
import java.sql.Timestamp
import java.time.Duration

class AudioInputBroadcastReceiver : BroadcastReceiver() {
//    lateinit var workManager: WorkManager
//    lateinit var workRequest: WorkRequest

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        val isConnected = intent!!.getIntExtra("state", 0) === 1
        val workManager = WorkManager.getInstance(context!!)

        if (isConnected) {
            var startTime = System.currentTimeMillis()
            Toast.makeText(context,"Connected",Toast.LENGTH_SHORT).show()
            val data = Data.Builder()

            data.putLong("startTime", startTime)
            val workRequest = OneTimeWorkRequest.Builder(
                GetHeadsetUsageTimeWorker::class.java,
            ).setInputData(data.build())




                .build()
            workManager.enqueue(workRequest)
        } else {
            Toast.makeText(context,"Disconnected",Toast.LENGTH_SHORT).show()

//            val data = Data.Builder()
//
//            data.putBoolean("isStopped", true)
//            val workRequest = OneTimeWorkRequest.Builder(
//                GetHeadsetUsageTimeWorker::class.java,
//            ).setInputData(data.build()).build()
            workManager.cancelAllWork()



        }

    }

    override fun peekService(myContext: Context?, service: Intent?): IBinder {
        return super.peekService(myContext, service)
    }

}