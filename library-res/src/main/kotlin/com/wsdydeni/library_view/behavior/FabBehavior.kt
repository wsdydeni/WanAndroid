package com.wsdydeni.library_view.behavior

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wsdydeni.library_view.behavior.FabBehavior.AnimateListener

class FabBehavior(context: Context?, attrs: AttributeSet?) : FloatingActionButton.Behavior(context, attrs) {

    private var isAnimateIng = false
    private var isShow = false
    private var distance = 0f

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        if(coordinatorLayout.height.toFloat() / 5f > distance) {
            distance = coordinatorLayout.height.toFloat() / 5f
        }
        return  axes == ViewCompat.SCROLL_AXIS_VERTICAL || (super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type))
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimateIng && !isShow) {
            AnimatorUtil.showFab(child, AnimateListener(),distance = distance)
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && !isAnimateIng && isShow) {
            AnimatorUtil.hideFab(child, AnimateListener(),distance)
        }
    }

    inner class AnimateListener : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            isAnimateIng = true
            isShow = !isShow
        }

        override fun onAnimationEnd(animation: Animator) {
            isAnimateIng = false
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    }
}


object AnimatorUtil {

    private val interpolator = LinearOutSlowInInterpolator()

    fun showFab(view: View, vararg listener: AnimateListener?,distance: Float) {
        if (listener.isNotEmpty()) {
            view.animate()
                .translationY(-distance)
                .setDuration(600)
                .setInterpolator(interpolator)
                .setListener(listener[0])
                .start()
        } else {
            view.animate()
                .translationY(-distance)
                .setDuration(600)
                .setInterpolator(interpolator)
                .start()
        }
    }

    fun hideFab(view: View, listener: AnimateListener?,distance: Float) {
        view.animate()
            .translationY(distance)
            .setDuration(600)
            .setInterpolator(interpolator)
            .setListener(listener)
            .start()
    }
}







