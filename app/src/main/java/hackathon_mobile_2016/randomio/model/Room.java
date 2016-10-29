package hackathon_mobile_2016.randomio.model;

/**
 * Created by ductr on 10/29/2016.
 */
public class Room {
    public int noPlayerCurrent;
    public String name;
    public int mode;
    public int maxPlayer;
    public boolean isReady;
    public String ownerId;

    public Room() {

    }

    public Room(int noPlayerCurrent, String name, int mode, int maxPlayer, boolean isReady, String ownerId) {
        this.noPlayerCurrent = noPlayerCurrent;
        this.name = name;
        this.mode = mode;
        this.maxPlayer = maxPlayer;
        this.isReady = isReady;
        this.ownerId = ownerId;
    }
}
