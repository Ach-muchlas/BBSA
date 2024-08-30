package com.am.bbsa.utils

import android.graphics.Canvas
import android.graphics.RectF
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler

class RoundedBarChartRenderer(
    chart: com.github.mikephil.charting.charts.BarChart,
    animator: com.github.mikephil.charting.animation.ChartAnimator,
    viewPortHandler: ViewPortHandler
) : BarChartRenderer(chart, animator, viewPortHandler) {

    override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {
        val buffer = mBarBuffers[index]
        val phaseX = mAnimator.phaseX
        val phaseY = mAnimator.phaseY

        mRenderPaint.color = dataSet.color

        for (j in 0 until buffer.buffer.size step 4) {
            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break

            val left = buffer.buffer[j]
            val top = buffer.buffer[j + 1]
            val right = buffer.buffer[j + 2]
            val bottom = buffer.buffer[j + 3]

            val radius = (right - left) / 2

            c.drawRoundRect(
                RectF(left, top, right, bottom),
                radius,
                radius,
                mRenderPaint
            )
        }
    }
}
