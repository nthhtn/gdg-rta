package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.model.Room;
import hackathon_mobile_2016.randomio.services.Network;

public class FindRoomActivity extends Activity {

    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);

        table=(TableLayout)findViewById(R.id.tableRoom);

        Button button=(Button)findViewById(R.id.goToCreate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        findRoom();
    }

    public void findRoom(){
        DatabaseReference roomManager = Network.firebaseDatabase.getReference("room");
        roomManager.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearTable();

                ArrayList<Room> roomList = new ArrayList<Room>();
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next=iterator.next();
                    Room room = next.getValue(Room.class);
                    room.id=next.getKey();
                    roomList.add(room);
                    Log.i("Room:",room.id);
                    addRow(room);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addRow(Room room){
        TableRow view=(TableRow)inflateViewFromXML(R.layout.table_findroom_item);
        ((TextView)view.findViewById(R.id.columnIdDemo1)).setText(room.id);
        ((TextView)view.findViewById(R.id.columnPlayerDemo2)).setText(room.name);
        switch (room.mode){
            case 0:
                ((ImageView)view.findViewById(R.id.imgMode)).setBackgroundResource(R.mipmap.easy_mode);
                break;
            case 1:
                ((ImageView)view.findViewById(R.id.imgMode)).setBackgroundResource(R.mipmap.medium_mode);
                break;
            case 2:
                ((ImageView)view.findViewById(R.id.imgMode)).setBackgroundResource(R.mipmap.hard_mode);
                break;
        }
        LinearLayout layout=(LinearLayout)view.findViewById(R.id.linearNum);
        layout.removeAllViews();
        for (int i=1;i<=6;i++){
            if (i<=room.noPlayerCurrent){
                layout.addView(inflateViewFromXML(R.layout.imageview_available));
            } else{
                layout.addView(inflateViewFromXML(R.layout.imageview_unavailable));
            }
        }
        final String key=room.id;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),WaitingRoom.class);
                intent.putExtra("roomId",key);
                intent.putExtra("userId","xxx");
                Network.joinRoom(key,"xxx");
                startActivity(intent);
            }
        });
        table.addView(view);
    }

    private void clearTable(){
        table.removeAllViews();
    }

    private View inflateViewFromXML(int resource) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return  inflater.inflate(resource, null, false);
    }

}
