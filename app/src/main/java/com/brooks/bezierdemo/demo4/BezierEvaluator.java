
package com.brooks.bezierdemo.demo4;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author: lisongda
 * @date: 16/10/9.
 * @description:
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {
    PointF p1;
    PointF p2;

    public BezierEvaluator(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF point = new PointF();
        point.x = startValue.x * (1 - fraction) * (1 - fraction) * (1 - fraction)
                + 3 * p1.x * fraction * (1 - fraction) * (1 - fraction)
                + 3 * p2.x * fraction * fraction * (1 - fraction)
                + endValue.x * fraction * fraction * fraction;
        point.y = startValue.y * (1 - fraction) * (1 - fraction) * (1 - fraction)
                + 3 * p1.y * fraction * (1 - fraction) * (1 - fraction)
                + 3 * p2.y * fraction * fraction * (1 - fraction)
                + endValue.y * fraction * fraction * fraction;
        return point;
    }
}
