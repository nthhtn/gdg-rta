package hackathon_mobile_2016.randomio.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hackathon_mobile_2016.randomio.model.GameMatch;
import hackathon_mobile_2016.randomio.model.NumberBall;

/**
 * Created by ductr on 10/29/2016.
 */
public class Network {
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static void startMatch(String roomId, GameMatch gameMatch) {
        DatabaseReference gamesManager = firebaseDatabase.getReference("games/" + roomId);
        gamesManager.child("current").setValue(0);

        for (NumberBall p:gameMatch.points) {
            gamesManager.child("points").push().setValue(p);
        }
    }
}
