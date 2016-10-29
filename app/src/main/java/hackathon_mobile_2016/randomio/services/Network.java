package hackathon_mobile_2016.randomio.services;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hackathon_mobile_2016.randomio.model.Game;
import hackathon_mobile_2016.randomio.model.NumberBall;
import hackathon_mobile_2016.randomio.model.Room;
import hackathon_mobile_2016.randomio.model.RoomMember;

/**
 * Created by ductr on 10/29/2016.
 */
public class Network {
    private static String TAG="ductri";
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public static void serverStartMatch(String roomId, final Game game, final MatchService.GameMatchLoading gameMatchLoading) {
        DatabaseReference gamesManager = firebaseDatabase.getReference("game/" + roomId);
        gamesManager.child("current").setValue(0);

        for (NumberBall p: game.points) {
            gamesManager.child("points").push().setValue(p);
        }

        gamesManager.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameMatchLoading.success(game.points);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void clientStartMatch(String roomId, final MatchService.GameMatchLoading gameMatchLoading) {
        DatabaseReference gamesManager = firebaseDatabase.getReference("game/" + roomId);
        gamesManager.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Client start match -- Game has inited on server");
                Game game = new Game();
                Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                game.current = Integer.parseInt(iter.next().getValue().toString());
                ArrayList<NumberBall> numberBallList = new ArrayList<NumberBall>();


                Iterator<DataSnapshot> iterator = iter.next().getChildren().iterator();
                while (iterator.hasNext()) {
                    NumberBall p= iterator.next().getValue(NumberBall.class);
                    numberBallList.add(p);
                }
                game.points = numberBallList;
                gameMatchLoading.success(game.points);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "fail");
            }
        });


    }

    public static String createRoom(Room room){
        DatabaseReference roomManager = Network.firebaseDatabase.getReference("room");
        String key=roomManager.push().getKey();
        roomManager.child(key).setValue(room);
        return key;
    }

    public static void joinRoom(String roomId,String userId){
        RoomMember newMember=new RoomMember(userId,"noob",0,1,0);
        DatabaseReference roomMemberManager = Network.firebaseDatabase.getReference("roomMember/"+roomId);
        roomMemberManager.push().setValue(newMember);
    }

}
