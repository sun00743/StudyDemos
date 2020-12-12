package com.mika.demo.study.video

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.mika.demo.study.R
import com.mika.requester.listener.DownloadFileParser
import com.mika.requester.request.GetRequester
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.coroutines.MainScope
import java.io.File

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

//        val mp4Demo = File(externalCacheDir?.absolutePath, "mp4Demo")
//        if (mp4Demo.exists()) {
//            mp4Demo.delete()
//        }
//
//        var fileOk = false
//
//        val downloadFileParser = DownloadFileParser(externalCacheDir?.absolutePath ?: "", "mp4Demo")
//        GetRequester("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", downloadFileParser)
//                .inProgress { progress, length ->
//                    if (!fileOk && progress > 0.1f) {
//                        fileOk = true
//                    }
//                    Log.d("mika_video", "pro: $progress")
//                }
//                .success {
//                    Log.d("mika_video", "download finish")
//                }
//                .error { msg, code ->
//                    Log.d("mika_video", "$code  $msg")
//                }
//                .execute(MainScope())

        videoView.setVideoURI(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        videoView.setOnPreparedListener {
            Log.d("mika_video", "video Prepared")
            videoView.start()

            Log.d("mika_video", "video currentPosition:  ${videoView.currentPosition}")
            Log.d("mika_video", "video drawingTime:  ${videoView.drawingTime}")
            Log.d("mika_video", "video bufferPercentage:  ${videoView.bufferPercentage}")
            Log.d("mika_video", "video duration:  ${videoView.duration}")

            Handler(Looper.getMainLooper()).postDelayed({videoView.seekTo(40000)}, 5000)

            videoView.animate().translationY(400f).setDuration(2000).start()
        }
    }


}