package com.wsdydeni.library_view.toolbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.wsdydeni.library_res.R
import kotlin.properties.Delegates

/*
 * Create by wsdydeni on 2020/10/9 22:33
 */

class CustomToolbar : View {

    private var mHeight by Delegates.notNull<Float>()

    private var viewRect = Rect()

    private var iconDstRect = Rect()

    private var textRect = Rect()

    private var mText: String = ""

    private var mMenuText: String = ""

    private var mBackgroundColor by Delegates.notNull<Int>()

    private var navigationDrawable: Drawable? = null

    private var menuDrawable: Drawable? = null

    private var isShowMenu = false

    private var isShowNavigation = false

    private var isShowMenuText = false

    private var backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private var textPaint = TextPaint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    fun setText(text: String) {
        mText = text
        invalidate()
    }

    fun setNavigationDrawable(drawable: Drawable) {
        isShowNavigation = true
        navigationDrawable = drawable
        invalidate()
    }

    fun setMenuDrawable(drawable: Drawable) {
        isShowMenu = true
        menuDrawable = drawable
        invalidate()
    }

    fun setMenuText(text: String) {
        isShowMenuText = true
        mMenuText = text
        invalidate()
    }

    private fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return spValue * fontScale + 0.5f
    }

    fun setTextSize(spValue: Float) {
        textPaint.textSize = sp2px(context, spValue)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minimumWidth = suggestedMinimumWidth
        val minimumHeight = suggestedMinimumHeight
        val width = measureWidth(minimumWidth, widthMeasureSpec)
        val height = measureHeight(minimumHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制背景
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
        // 沉浸式状态栏下的View绘制范围
        when {
            isShowNavigation -> { // 左边有Icon时 文字在Icon旁边显示
                // 绘制Icon
                val bitmap = carveToBitmap(navigationDrawable!!)
                iconDstRect.left = sp2px(context,18f).toInt()
                iconDstRect.right = (bitmap.width + sp2px(context,18f)).toInt()
                iconDstRect.top = (height - bitmap.height - (mHeight - bitmap.height) / 4 + 5).toInt()
                iconDstRect.bottom = (height - ((mHeight - bitmap.height) / 4) - 3).toInt()
                canvas.drawBitmap(bitmap, null, iconDstRect, null)

                if(isShowMenuText) {
                    // 绘制最右侧文字
                    textPaint.textAlign = Paint.Align.RIGHT
                    textRect.left = (width - sp2px(context,20f) - textPaint.measureText(mMenuText)).toInt()
                    textRect.right = (width - sp2px(context,20f)).toInt()
                    textRect.top = (height - mHeight).toInt()
                    textRect.bottom = height
                    val fontMetrics = textPaint.fontMetrics
                    val distance = (fontMetrics.bottom - fontMetrics.top) / 9 * 5
                    val baseline = textRect.centerY() + distance
                    canvas.drawText(mMenuText, textRect.right.toFloat(),baseline,textPaint)
                }

                // 绘制左侧Icon文字
                textPaint.textAlign = Paint.Align.LEFT
                viewRect.left = iconDstRect.right + 80
                viewRect.right = width - sp2px(context,18f).toInt() - textRect.left
                viewRect.top = (height - mHeight).toInt()
                viewRect.bottom = height
                val fontMetrics1 = textPaint.fontMetrics
                val distance1 = (fontMetrics1.bottom - fontMetrics1.top) / 9 * 5
                val baseline1 = viewRect.centerY() + distance1
                // 文本过长时显示省略号
                val textWidth = textPaint.measureText(mText)
                var info = mText
                if(viewRect.right - iconDstRect.right + 80 < textWidth) {
                    info = TextUtils.ellipsize(mText,textPaint, (width - 100 - viewRect.left).toFloat(), TextUtils.TruncateAt.END).toString()
                }
                canvas.drawText(info, (iconDstRect.right + 80).toFloat(), baseline1, textPaint)
            }
            isShowMenu -> { // 右边有Icon时 文字最左侧显示
                // 绘制Icon
                val bitmap = carveToBitmap(menuDrawable!!)
                iconDstRect.left = width - sp2px(context,12f).toInt() - bitmap.width
                iconDstRect.right = width - sp2px(context,12f).toInt()
                iconDstRect.top = (height - bitmap.height - (mHeight - bitmap.height) / 4 + 10).toInt()
                iconDstRect.bottom = (height - (mHeight - bitmap.height) / 4).toInt()
                canvas.drawBitmap(bitmap,null,iconDstRect,null)
                // 绘制文字
                textPaint.textAlign = Paint.Align.LEFT
                viewRect.left = sp2px(context,14f).toInt()
                viewRect.right = width - iconDstRect.left - sp2px(context,14f).toInt()
                viewRect.top = (height - mHeight).toInt()
                viewRect.bottom = height
                val fontMetrics = textPaint.fontMetrics
                val distance = (fontMetrics.bottom - fontMetrics.top) / 9 * 5
                val baseline = viewRect.centerY() + distance
                canvas.drawText(mText, sp2px(context,14f), baseline, textPaint)
            }
            else -> {  //左右同时没有Icon时 文字居中显示
                textPaint.textAlign = Paint.Align.CENTER
                viewRect.left = 0
                viewRect.right = width
                viewRect.top = (height - mHeight).toInt()
                viewRect.bottom = height
                val fontMetrics = textPaint.fontMetrics
                val distance = (fontMetrics.bottom - fontMetrics.top) / 9 * 5
                val baseline = viewRect.centerY() + distance
                canvas.drawText(mText, viewRect.centerX().toFloat(), baseline, textPaint)
            }
        }
    }

    private fun carveToBitmap(drawable: Drawable) : Bitmap {
        return try {
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: OutOfMemoryError) {
            throw OutOfMemoryError("CustomToolbar carveToBitmap failed")
        }
    }

    private fun measureWidth(defaultWidth: Int, measureSpec: Int): Int {
        var defaultWidth1 = defaultWidth
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.AT_MOST -> defaultWidth1 = paddingLeft + paddingRight
            MeasureSpec.EXACTLY -> defaultWidth1 = specSize
            MeasureSpec.UNSPECIFIED -> TODO()
        }
        return defaultWidth1
    }

    private fun measureHeight(defaultHeight: Int, measureSpec: Int): Int {
        var defaultHeight1 = defaultHeight
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.AT_MOST -> defaultHeight1 = paddingTop + paddingBottom
            MeasureSpec.EXACTLY -> defaultHeight1 = specSize
            MeasureSpec.UNSPECIFIED -> TODO()
        }
        return defaultHeight1
    }

    private fun initView(attrs: AttributeSet?) {
        mBackgroundColor = ContextCompat.getColor(context, R.color.background)
        backgroundPaint.color = mBackgroundColor
        val array = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        mHeight = array.getDimension(0, 0f)
        array.recycle()
        attrs?.let {
            val array1 = context.obtainStyledAttributes(it, R.styleable.CustomToolbar)
            mText = array1.getString(R.styleable.CustomToolbar_text) ?: ""
            array1.recycle()
        }
        textPaint.color = ContextCompat.getColor(context, R.color.text_black)
        textPaint.textSize = sp2px(context, 18f)
        textPaint.isAntiAlias = true
        textPaint.isFakeBoldText = true
    }

    private var onClick: (() -> Unit)? = null

    fun setOnClickListener(listener: () -> Unit) {
        onClick = listener
        invalidate()
    }

    private var onMenuClick: (() -> Unit)? = null

    fun setOnMenuTextClickListener(listener: () -> Unit) {
        onMenuClick = listener
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            if(x > iconDstRect.left && x < iconDstRect.right && y < iconDstRect.bottom && y > iconDstRect.top) {
                onClick?.invoke()
            }
            if(isShowMenuText && x > textRect.left && x < textRect.right && y < textRect.bottom && y > textRect.top) {
                Log.e("CustomToolbar","onTouchEvent onMenuClick")
                onMenuClick?.invoke()
            }
        }
        return true
    }

    constructor(context: Context?) : super(context) { initView(null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { initView(attrs) }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initView(attrs) }

}