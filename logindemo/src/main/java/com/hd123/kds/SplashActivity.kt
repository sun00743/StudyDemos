package com.hd123.kds

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.hd123.kds.bussiness.home.MainActivity
import com.hd123.kds.databinding.ActivitySplashBinding
import com.hd123.kds.login.ui.LoginActivity
import com.hd123.kds.user.UserManager
import com.hd123.kds.util.log.AppLogger
import com.hd123.kds.util.SetHome
import com.hd123.kds.util.Values

class SplashActivity : AppCompatActivity() {

    val mBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)

        requestPermission()
    }

    /**
     * 请求用户授予应用权限
     */
    private fun requestPermission() {
        var request: (() -> Unit)? = null
        val requestLaunch = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()) {
            it.entries.forEach { element ->
                if (!element.value) {
                    request?.invoke()
                    return@registerForActivityResult
                }
            }

            startLogin()
        }
        request = {
            requestLaunch.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE))
        }
        request.invoke()
    }

    private fun startLogin() {
        SetHome.setHome(this)
        AppLogger.init()

        Handler(Looper.getMainLooper()).postDelayed({
            if (isHasLogin()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 1000)
    }

    private fun isHasLogin() = Values.isLogined && UserManager.getUserCache() != null

}