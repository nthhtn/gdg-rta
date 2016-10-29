package hackathon_mobile_2016.randomio.model;

/**
 * Created by kuro on 29/10/2016.
 */
public class RoomMember {
    private String userId;
    private String name;
    private int score;
    private int team;
    private int status;

    public RoomMember(){

    }

    public RoomMember(String userId,String name,int score,int team,int status){
        this.userId=userId;
        this.name=name;
        this.score=score;
        this.team=team;
        this.status=status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
