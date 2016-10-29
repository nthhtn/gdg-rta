package hackathon_mobile_2016.randomio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.model.JoinRoom;
import hackathon_mobile_2016.randomio.model.RoomMember;
import hackathon_mobile_2016.randomio.model.Room;
import hackathon_mobile_2016.randomio.services.Network;

public class WaitingRoom extends AppCompatActivity {
    private String TAG="ductri";

    private TableLayout tableLayout;
    private String roomId;
    private String playerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        roomId = getIntent().getStringExtra("roomId");
        roomId="-KVFHhZrr2qZ1d-rNnDt";


        tableLayout=(TableLayout)findViewById(R.id.tableWaiting);


        Button button = (Button) findViewById(R.id.button3);

        DatabaseReference roomMemberManager = Network.firebaseDatabase.getReference("roomMember/"+roomId);

        roomMemberManager.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearListView();
                ArrayList<RoomMember> playersList = new ArrayList<RoomMember>();
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    RoomMember p= iterator.next().getValue(RoomMember.class);
                    playersList.add(p);
                    playerJoining(p);
                }

                for (RoomMember p:playersList) {
                    Log.i(TAG, "--------");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Cancel");
            }
        });

<<<<<<< HEAD
        //Tracking room status to start game
        Network.firebaseDatabase.getReference("rooms/"+roomId).addValueEventListener(new ValueEventListener() {
=======
        Network.firebaseDatabase.getReference("room/"+roomId).addValueEventListener(new ValueEventListener() {
>>>>>>> 2e48443159e6f8b4861c60df8846d27d3ef0cb13
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Room status change");
                Room room = dataSnapshot.getValue(Room.class);
                if (room.status==2) {
                    Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                    intent.putExtra("isHost", "false");
                    intent.putExtra("roomId", roomId);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "fail");
            }
        });

        JoinRoom.joinRoom(roomId);
    }

    private void playerJoining(RoomMember player) {
        View tableRow = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_item,null,false);
        TextView history_display_no  = (TextView) tableRow.findViewById(R.id.columnId);
        TextView history_display_date  = (TextView) tableRow.findViewById(R.id.columnTeam);
        TextView history_display_orderid  = (TextView) tableRow.findViewById(R.id.columnStatus);
        //TextView history_display_quantity  = (TextView) tableRow.findViewById(R.id.history_display_quantity);

        history_display_no.setText("1");
        history_display_date.setText(player.getName());
        tableLayout.addView(tableRow);
    }

    private void clearListView() {
        tableLayout.removeAllViews();
    }
}
