package hackathon_mobile_2016.randomio.views.animations;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import hackathon_mobile_2016.randomio.views.NumberView;

/**
 * Created by bubu on 29/10/2016.
 */

public class CircleAngleAnimation extends Animation {

    private NumberView numberView;

    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(NumberView numberView, int newAngle) {
        this.oldAngle = numberView.getAngle();
        this.newAngle = newAngle;
        this.numberView = numberView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        numberView.setAngle(angle);
        numberView.requestLayout();
    }
}
