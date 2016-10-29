package hackathon_mobile_2016.randomio.services;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by ductr on 10/30/2016.
 */
public class ScoreGameService {

    public static int team1 = 0;
    public static int team2 = 0;

    public static void updateScoreTeam1(String roomId, int score) {
        DatabaseReference scoreManage = Network.firebaseDatabase.getReference("game/"+roomId);
        scoreManage.child("team1").setValue(score);
    }

    public static void updateScoreTeam2(String roomId, int score) {
        DatabaseReference scoreManage = Network.firebaseDatabase.getReference("game/"+roomId);
        scoreManage.child("team2").setValue(score);
    }
}
