package com.hd123.kds.util.log

import android.util.Log
import org.apache.log4j.AppenderSkeleton
import org.apache.log4j.Layout
import org.apache.log4j.Level
import org.apache.log4j.PatternLayout
import org.apache.log4j.spi.LoggingEvent

class LogCatAppender @JvmOverloads constructor(
        messageLayout: Layout? = PatternLayout("%m%n"),
        var tagLayout: Layout = PatternLayout("%c"))
    : AppenderSkeleton() {

    override fun append(le: LoggingEvent) {
        when (le.getLevel().toInt()) {
            Level.TRACE_INT -> if (le.throwableInformation != null) {
                Log.v(tagLayout.format(le), getLayout().format(le), le.throwableInformation.throwable)
            } else {
                Log.v(tagLayout.format(le), getLayout().format(le))
            }
            Level.DEBUG_INT -> if (le.throwableInformation != null) {
                Log.d(tagLayout.format(le), getLayout().format(le), le.throwableInformation.throwable)
            } else {
                Log.d(tagLayout.format(le), getLayout().format(le))
            }
            Level.INFO_INT -> if (le.throwableInformation != null) {
                Log.i(tagLayout.format(le), getLayout().format(le), le.throwableInformation.throwable)
            } else {
                Log.i(tagLayout.format(le), getLayout().format(le))
            }
            Level.WARN_INT -> if (le.throwableInformation != null) {
                Log.w(tagLayout.format(le), getLayout().format(le), le.throwableInformation.throwable)
            } else {
                Log.w(tagLayout.format(le), getLayout().format(le))
            }
            Level.ERROR_INT -> if (le.throwableInformation != null) {
                Log.e(tagLayout.format(le), getLayout().format(le), le.throwableInformation.throwable)
            } else {
                Log.e(tagLayout.format(le), getLayout().format(le))
            }
            Level.FATAL_INT -> if (le.throwableInformation != null) {
                Log.wtf(tagLayout.format(le), getLayout().format(le), le.throwableInformation.throwable)
            } else {
                Log.wtf(tagLayout.format(le), getLayout().format(le))
            }
        }
    }

    override fun close() {}
    override fun requiresLayout(): Boolean {
        return true
    }

    init {
        setLayout(messageLayout)
    }
}
