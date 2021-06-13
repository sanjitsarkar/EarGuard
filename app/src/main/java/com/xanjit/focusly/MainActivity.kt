package com.xanjit.focusly


import android.content.*
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.xanjit.focusly.broadcast_receivers.AudioInputBroadcastReceiver
import com.xanjit.focusly.services.CustomService
import com.xanjit.focusly.workers.GetHeadsetUsageTimeWorker
import java.lang.Exception


class MainActivity : ComponentActivity() {

    lateinit var receiver: BroadcastReceiver
    var TAG = "EarGuard"
    lateinit var workManager: WorkManager
    lateinit var workRequest: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        workManager = WorkManager.getInstance(applicationContext)
        workRequest = OneTimeWorkRequest.Builder(
            GetHeadsetUsageTimeWorker::class.java,
        ).addTag("Headset").build()
receiver = AudioInputBroadcastReceiver()
        setContent {


            Surface() {
                Scaffold(
                    topBar = {
                        TopAppBar() {

                        }
                    }
                ) {
                    Column() {


                        Button(
                            content = { Text("Start Worker") },
                            onClick = {
//workManager.enqueue(workRequest)
                            }
                        )



                        Button(
                            content = { Text("Stop Worker") },
                            onClick = {
//workManager.cancelAllWorkByTag("Headset")
                            }
                        )


                    }
                }
            }


        }


    }

    override fun onStart() {
        super.onStart()
        var intentFilter = IntentFilter("android.intent.action.HEADSET_PLUG");
        registerReceiver(receiver, intentFilter)

    }
}


