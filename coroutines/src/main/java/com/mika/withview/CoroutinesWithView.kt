package com.mika.withview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by mika on 2020/8/21.
 */

suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> {

    val listener = object : View.OnLayoutChangeListener {
        override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int,
                                    oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            v?.removeOnLayoutChangeListener(this)

            Log.d("mika", "${v.toString()} onLayout change callback, coroutine will resume")
            it.resume(Unit)
        }
    }

    it.invokeOnCancellation {
        removeOnLayoutChangeListener(listener)
    }

    addOnLayoutChangeListener(listener)

    //协程挂起
}

suspend fun View.awaitAnimationFrame() = suspendCancellableCoroutine<Unit> { continuation ->
    val runnable = Runnable {
        continuation.resume(Unit)
    }
    // If the coroutine is cancelled, remove the callback
    continuation.invokeOnCancellation { removeCallbacks(runnable) }
    // And finally post the runnable
    postOnAnimation(runnable)
}

/**
 * 动画监听
 */
class AwaitAnimatorListener(private val continuation: CancellableContinuation<Unit>) : AnimatorListenerAdapter() {
    private var endedSuccessfully = true

    override fun onAnimationEnd(animation: Animator?) {
        animation?.removeListener(this)

        // 如果协程仍处于活跃状态
        if (continuation.isActive) {
            if (endedSuccessfully) {
                continuation.resume(Unit)
            } else {
                continuation.cancel()
            }
        }
    }

    override fun onAnimationCancel(animation: Animator?) {
        endedSuccessfully = false
    }
}

suspend fun ViewPropertyAnimator.awaitAnimationEnd() = suspendCancellableCoroutine<Unit> {
    it.invokeOnCancellation { cancel() }
    setListener(AwaitAnimatorListener(it))
}

suspend fun Animator.awaitAnimationEnd() = suspendCancellableCoroutine<Unit> {

    // 增加一个处理协程取消的监听器，如果协程被取消，
    // 同时执行动画监听器的 onAnimationCancel() 方法，取消动画
    it.invokeOnCancellation { cancel() }
    addListener(AwaitAnimatorListener(it))
    //协程挂起
}


suspend fun RecyclerView.awaitScrollEnd() {
    awaitAnimationFrame()

    if (scrollState == RecyclerView.SCROLL_STATE_IDLE) return

    suspendCancellableCoroutine<Unit> {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView.removeOnScrollListener(this)
                    it.resume(Unit)
                }
            }
        }

        it.invokeOnCancellation { removeOnScrollListener(listener) }

        addOnScrollListener(listener)
    }

}
