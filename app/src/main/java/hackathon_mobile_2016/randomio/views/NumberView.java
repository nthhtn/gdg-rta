package hackathon_mobile_2016.randomio.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import hackathon_mobile_2016.randomio.views.animations.CircleAngleAnimation;

/**
 * Created by bubu on 29/10/2016.
 */

public class NumberView extends View {

    private static final int START_ANGLE_POINT = -65;

    private Paint paintOfCircle;
    private final RectF rect;

    private float angle;
    private String text;

    public boolean isClicked=false;

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 5;

        paintOfCircle = new Paint();
        paintOfCircle.setAntiAlias(true);
        paintOfCircle.setStyle(Paint.Style.STROKE);
        paintOfCircle.setStrokeWidth(strokeWidth);
        //Circle color
        paintOfCircle.setColor(Color.RED);

        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, 70 + strokeWidth, 70 + strokeWidth);

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void runDrawCircle(int color) {
        paintOfCircle.setColor(color);
        CircleAngleAnimation animation = new CircleAngleAnimation(this, -345);
        animation.setDuration(400);
        this.startAnimation(animation);
    }

    public void drawCircle(int color) {
        paintOfCircle.setColor(color);
        CircleAngleAnimation animation = new CircleAngleAnimation(this, -345);
        animation.setDuration(0);
        this.startAnimation(animation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth(2);
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawPaint(paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText(text, 15, 50, paint);

        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paintOfCircle);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}
