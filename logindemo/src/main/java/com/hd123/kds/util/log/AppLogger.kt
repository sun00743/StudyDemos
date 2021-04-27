package com.hd123.kds.util.log

import com.hd123.kds.util.SetHome.Companion.KDS_HOME
import org.apache.log4j.*
import org.apache.log4j.helpers.LogLog
import java.io.File
import java.io.IOException

class AppLogger {

    companion object {

        private const val INTERNAL_DEBUGGING = false

        /**
         * 文件输出模式
         */
        private const val FILE_PATTERN = "%d [%t] %p %c - %m%n"
        private const val LOGCAT_PATTERN = "%m%n"    // 日志模式

        private const val MAX_BACKUP_SIZE = 5 // 最大备份数量
        private const val MAX_FILE_SIZE = (512 * 1024).toLong() //最大文件大小
        private const val IMMEDIATE_FLUSH = true // 马上刷新

        fun init() {
            val root = Logger.getRootLogger() // 获取跟日志级别
            //执行重设命令
            LogManager.getLoggerRepository().resetConfiguration()
            LogLog.setInternalDebugging(INTERNAL_DEBUGGING)
            // 这个输出器旨在，当用户的日志文件超过一个确定的大小去备份文件
            val rollingFileAppender: RollingFileAppender
            //     Extend this abstract class to create your own log layout format
            // A flexible layout configurable with pattern string.
            val fileLayout: Layout = PatternLayout(FILE_PATTERN)


            rollingFileAppender = try {
                RollingFileAppender(fileLayout, getFileName()) // 规定文件输出模式和文件名字输出模式
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException("Exception configuring log system", e)
            }

            rollingFileAppender.maxBackupIndex = MAX_BACKUP_SIZE // 设置最大备份索引
            rollingFileAppender.maximumFileSize = MAX_FILE_SIZE // 设置最大文件大小
            rollingFileAppender.immediateFlush = IMMEDIATE_FLUSH // 设置是否立即刷新
            root.addAppender(rollingFileAppender) // 在根上添加输出器
            root.level = Level.DEBUG

            val messageLayout: Layout = PatternLayout(LOGCAT_PATTERN)
            val tagLayout: Layout = PatternLayout("[%t] %c")
            val logCatAppender = LogCatAppender(messageLayout, tagLayout)

            root.addAppender(logCatAppender)
        }

        private fun getFileName(): String? {
            // 设置Log4j的输出路径文件夹
            val kdsHome = System.getProperty(KDS_HOME) ?: return null
            val rootPath = "$kdsHome/logs"
            val dirFile = File(rootPath)
            if (!dirFile.exists()) {
                dirFile.mkdirs()
            } // 文件夹准备

             return "$rootPath${File.separator}all.log"
        }

    }

}