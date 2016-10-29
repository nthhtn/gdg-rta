package hackathon_mobile_2016.randomio.model;


import hackathon_mobile_2016.randomio.enums.Team;

/**
 * Created by bubu on 28/10/2016.
 */
public class NumberBall {

    private int value;
    private Team owner;
    private double x;
    private double y;

    public NumberBall(int value, Team owner) {
        this.value = value;
        this.owner = owner;
    }

    public NumberBall(int value) {
        this(value, Team.NO_TEAM);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Team getOwner() {
        return owner;
    }

    public void setOwner(Team owner) {
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
