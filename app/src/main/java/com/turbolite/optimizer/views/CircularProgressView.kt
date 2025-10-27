package com.turbolite.optimizer.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.turbolite.optimizer.R

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    
    private var progress = 0
    private var currentProgress = 0f
    
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 24f
        color = ContextCompat.getColor(context, R.color.glass_border)
    }
    
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 24f
        strokeCap = Paint.Cap.ROUND
        shader = android.graphics.LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            intArrayOf(
                ContextCompat.getColor(context, R.color.neon_cyan),
                ContextCompat.getColor(context, R.color.electric_purple),
                ContextCompat.getColor(context, R.color.neon_green)
            ),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    
    private val rect = RectF()
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        
        val padding = 40f
        rect.set(padding, padding, w - padding, h - padding)
        
        progressPaint.shader = android.graphics.LinearGradient(
            0f, 0f, w.toFloat(), h.toFloat(),
            intArrayOf(
                ContextCompat.getColor(context, R.color.neon_cyan),
                ContextCompat.getColor(context, R.color.electric_purple),
                ContextCompat.getColor(context, R.color.neon_green)
            ),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        canvas.drawArc(rect, -90f, 360f, false, backgroundPaint)
        
        val sweepAngle = (currentProgress / 100f) * 360f
        canvas.drawArc(rect, -90f, sweepAngle, false, progressPaint)
    }
    
    fun setProgress(newProgress: Int, animate: Boolean = true) {
        progress = newProgress.coerceIn(0, 100)
        
        if (animate) {
            val animator = ValueAnimator.ofFloat(currentProgress, progress.toFloat())
            animator.duration = 800
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                currentProgress = animation.animatedValue as Float
                invalidate()
            }
            animator.start()
        } else {
            currentProgress = progress.toFloat()
            invalidate()
        }
    }
}
