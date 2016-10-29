package hackathon_mobile_2016.randomio.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.model.RoomMember;

import hackathon_mobile_2016.randomio.model.Room;
import hackathon_mobile_2016.randomio.model.RoomMember;
import hackathon_mobile_2016.randomio.services.Network;

public class WaitingRoom extends AppCompatActivity {
    private String TAG = "ductri";

    private TableLayout tableLayout;
    private String roomId;

    //private String playerId;
    private Room room = null;
    private RoomMember player;

    private String playerId="xxx";
    private boolean ready;

    List<Integer> categories = Arrays.asList(Color.BLACK,
            Color.RED,
            Color.rgb(226, 95, 2),
            Color.rgb(0, 175, 2),
            Color.BLUE,
            Color.rgb(129, 12, 142));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        roomId = getIntent().getStringExtra("roomId");


        tableLayout = (TableLayout) findViewById(R.id.tableRoomMember);

        Button btnReady=(Button)findViewById(R.id.btnReady);

        final String memberId=playerId;


        ready=false;


        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomMemberChangesStatus(roomId,memberId,0);
            }
        });

        DatabaseReference roomMemberManager = Network.firebaseDatabase.getReference("roomMember/" + roomId);

        roomMemberManager.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearListView();
                ArrayList<RoomMember> playersList = new ArrayList<RoomMember>();
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    RoomMember p = iterator.next().getValue(RoomMember.class);
                    playersList.add(p);
                    addRow(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Cancel");
            }
        });


        //Tracking room status to start game
        Network.firebaseDatabase.getReference("room/"+roomId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Room status change");
                Room room = dataSnapshot.getValue(Room.class);
                if (room.status == 2) {
                    Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                    intent.putExtra("isHost", "false");
                    intent.putExtra("roomId", roomId);
                    intent.putExtra("team", "2"); //TODO huhu, Linh lam chua xong
                    intent.putExtra("gameMode", Integer.toString(room.mode));
                    intent.putExtra("maxNumber", Integer.toString(room.maxPlayer));
                    intent.putExtra("playerId","2");
                    intent.putExtra("playerName", "ductri");
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "fail");
            }
        });


    }

    private void playerJoining(RoomMember player) {
        Log.i(TAG, player.getName());
    }

    private void clearListView() {
        tableLayout.removeAllViews();
    }

    private void onMemberStatusChanged(RoomMember member) {
        Log.i(TAG, "");
    }

    private void addRow(RoomMember member){
        TableRow view=(TableRow)inflateViewFromXML(R.layout.table_waitingroom_item);
        ((TextView)view.findViewById(R.id.columnId)).setText(member.getUserId());
        ((TextView)view.findViewById(R.id.columnPlayer)).setText(member.getName());
        TextView team=(TextView)view.findViewById(R.id.columnTeam);
        team.setText("");
        team.setBackgroundColor(getTeamColor(member.getTeam()));
        TextView status=(TextView)view.findViewById(R.id.columnTeam);
        if (member.getStatus()==1){
            status.setText("Ready");
        }
        tableLayout.addView(view);
    }

    private int getTeamColor(int team){
        return categories.get(team);
    }

    private View inflateViewFromXML(int resource) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return  inflater.inflate(resource, null, false);
    }

    private void roomMemberChangesStatus(String roomId,String memberId,int status){
        DatabaseReference roomMemberManager = Network.firebaseDatabase.getReference("roomMember/" + roomId);
        Query query=roomMemberManager.orderByChild("userId").equalTo(memberId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                if (iterator.hasNext()){
                    RoomMember member=iterator.next().getValue(RoomMember.class);
                    Log.i("Noob",member.getName());
                } else{
                    Log.i("No","false");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
