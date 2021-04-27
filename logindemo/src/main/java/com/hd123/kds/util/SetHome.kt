package com.hd123.kds.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.content.ContextCompat
import com.hd123.kds.R
import org.apache.log4j.Logger
import java.io.File

class SetHome {

    companion object {

        private val logger = Logger.getLogger(SetHome::class.java)

        const val KDS_HOME = "KDS_HOME"

        const val KDS_TEMP = "KDS_TEMP"

        @Throws(Exception::class)
        fun setHome(context: Context) {
            checkWritePermission(context)
            set(KDS_HOME, context.packageName, context)
            setTemp(KDS_TEMP, "temp", context)
        }

        @Throws(Exception::class)
        fun checkWritePermission(context: Context?) {
            val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
                    context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                logger.error("用户未授权文件权限")
            }
        }

        @Throws(Exception::class)
        private fun set(propertyName: String, publicPath: String, context: Context) {
            val dir = Environment.getExternalStoragePublicDirectory(publicPath)
            if (!dir!!.exists()) {
                if (!dir.mkdirs()) {
                    logger.error(String.format(
                            context.resources.getString(R.string.fail_to_create_directory),
                            dir.path))
                }
            }
            System.setProperty(propertyName, dir.canonicalPath)
        }

        @Throws(Exception::class)
        private fun setTemp(propertyName: String, publicPath: String, context: Context) {
            val dir = File(context.filesDir, publicPath)
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    logger.error(String.format(
                            context.resources.getString(R.string.fail_to_create_directory),
                            dir.path))
                }
            }
            System.setProperty(propertyName, dir.canonicalPath)
        }
    }
}