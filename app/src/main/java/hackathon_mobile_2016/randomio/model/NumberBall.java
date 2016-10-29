package hackathon_mobile_2016.randomio.model;



/**
 * Created by bubu on 28/10/2016.
 */
public class NumberBall {

    private int value;
    private int owner;
    private double x;
    private double y;
    public NumberBall() {

    }
    public NumberBall(int value, int owner) {
        this.value = value;
        this.owner = owner;
    }

    public NumberBall(int value) {
        this(value, 0);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }



}
