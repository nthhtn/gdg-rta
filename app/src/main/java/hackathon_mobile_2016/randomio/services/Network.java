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


    public static void serverStartMatch(final String roomId, final Game gameMatch, final MatchService.GameRender gameRender) {

        DatabaseReference gamesManager = firebaseDatabase.getReference("game/" + roomId);
        gamesManager.child("current").setValue(0);
        gamesManager.child("team1").setValue(0);
        gamesManager.child("team2").setValue(0);
        gamesManager.child("lastNumber").child("id").setValue("-1");
        gamesManager.child("lastNumber").child("value").setValue("0");

        for (NumberBall p:gameMatch.points) {
            gamesManager.child("points").push().setValue(p);
        }

        gamesManager.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Update map to server success, update interface
                gameRender.success(gameMatch.points);


                //TODO Lap code :(
                DatabaseReference lastNumber = Network.firebaseDatabase.getReference("game/"+roomId+"/lastNumber");
                lastNumber.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int number = Integer.parseInt(dataSnapshot.child("value").getValue().toString());
                        gameRender.renderChoosenNumber(number);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static void clientStartMatch(final String roomId, final MatchService.GameRender gameRender) {

        DatabaseReference gamesManager = firebaseDatabase.getReference("game/" + roomId);
        gamesManager.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Client start match -- Game has inited on server");
                Game gameMatch = new Game();
                Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();

                gameMatch.current = Integer.parseInt(iter.next().getValue().toString());
                ArrayList<NumberBall> numberBallList = new ArrayList<NumberBall>();

                //TODO NGUY HIEM
                iter.next();

                Iterator<DataSnapshot> iterator = iter.next().getChildren().iterator();
                while (iterator.hasNext()) {
                    NumberBall p = iterator.next().getValue(NumberBall.class);
                    numberBallList.add(p);
                }
                gameMatch.points = numberBallList;
                gameRender.success(gameMatch.points);

                //TODO Lap code :(
                DatabaseReference lastNumber = Network.firebaseDatabase.getReference("game/"+roomId+"/lastNumber");
                lastNumber.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int number = Integer.parseInt(dataSnapshot.child("value").getValue().toString());
                        gameRender.renderChoosenNumber(number);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "fail");
            }
        });
    }

    public static void choseNumber(String roomId, int number, int team) {
        DatabaseReference lastNumber = Network.firebaseDatabase.getReference("game/"+roomId+"/lastNumber");
        lastNumber.child("value").setValue(number);
        lastNumber.child("team").setValue(team);
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
