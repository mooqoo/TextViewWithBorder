package com.example.textviewwithborder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

class TextViewWithBorder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    val DEFAULT_STROKE_COLOR = Color.BLACK
    val DEFAULT_STROKE_WIDTH = 2
    val DEFAULT_STROKE_JOIN = Paint.Join.ROUND
    val DEFAULT_STROKE_MITER = 10

    // stroke variable
    private var strokeWidth: Float = DEFAULT_STROKE_WIDTH.toFloat()
    private var strokeColor: Int = DEFAULT_STROKE_COLOR
    private var strokeJoin: Paint.Join = DEFAULT_STROKE_JOIN
    private var strokeMiter: Float = DEFAULT_STROKE_MITER.toFloat()

    init {
        if (attrs != null) {
            val a = getContext().obtainStyledAttributes(attrs, R.styleable.textViewWithBorder)

            if (a.hasValue(R.styleable.textViewWithBorder_strokeColor)) {
                strokeColor = a.getColor(R.styleable.textViewWithBorder_strokeColor, DEFAULT_STROKE_COLOR)
            }
            if (a.hasValue(R.styleable.textViewWithBorder_strokeWidth)) {
                strokeWidth = a.getDimensionPixelSize(R.styleable.textViewWithBorder_strokeWidth, DEFAULT_STROKE_WIDTH).toFloat()
            }
            if (a.hasValue(R.styleable.textViewWithBorder_strokeMiter)) {
                strokeMiter = a.getDimensionPixelSize(R.styleable.textViewWithBorder_strokeMiter, DEFAULT_STROKE_MITER).toFloat()
            }
            if (a.hasValue(R.styleable.textViewWithBorder_strokeJoinStyle)) {
                when (a.getInt(R.styleable.textViewWithBorder_strokeJoinStyle, 2)) {
                    0 -> strokeJoin = Paint.Join.MITER
                    1 -> strokeJoin = Paint.Join.BEVEL
                    2 -> strokeJoin = Paint.Join.ROUND
                }
            }
            this.setStroke(strokeWidth, strokeColor, strokeJoin, strokeMiter)
        }
    }

    private fun setStroke(width: Float, color: Int, join: Paint.Join, miter: Float) {
        strokeWidth = width
        strokeColor = color
        strokeJoin = join
        strokeMiter = miter
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val restoreColor = this.currentTextColor
        if (strokeColor != null) {
            val paint = this.paint
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = strokeJoin
            paint.strokeMiter = strokeMiter
            this.setTextColor(strokeColor)
            paint.strokeWidth = strokeWidth
            super.onDraw(canvas)

            paint.style = Paint.Style.FILL
            this.setTextColor(restoreColor)
        }
    }
}

