package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.model.Room;
import hackathon_mobile_2016.randomio.services.Network;

import static android.R.attr.typeface;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface titleFont = Typeface.createFromAsset(getAssets(), "fonts/Title.ttf");

        TextView createRoom = (TextView)findViewById(R.id.lblCreateRoom);
        createRoom.setTypeface(titleFont);

        Random random=new Random();
        String[] str={
                getResources().getString(R.string.room_name1),
                getResources().getString(R.string.room_name2),
                getResources().getString(R.string.room_name3)
        };
        int rnd=random.nextInt(3);
        EditText roomName=(EditText)findViewById(R.id.roomNameTxt);
        roomName.setText(str[rnd]);

        Button btnCreate=(Button)findViewById(R.id.btnCreateRoom);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=((EditText)findViewById(R.id.roomNameTxt)).getText().toString();
                int maxPlayer=Integer.valueOf(((EditText)findViewById(R.id.maxPlayersTxt)).getText().toString());
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.modeRadio);
                int checked=radioGroup.getCheckedRadioButtonId();
                View radioButton=radioGroup.findViewById(checked);
                int mode=radioGroup.indexOfChild(radioButton);
                Room room=new Room(1,name,mode,maxPlayer,0,"xxx");
                String key=Network.createRoom(room);
                if (key!=""){
                    Intent intent=new Intent(getApplicationContext(),ManageRoomActivity.class);
                    intent.putExtra("roomId",key);
                    intent.putExtra("gameMode", Integer.toString(mode));

                    Network.joinRoom(key,"xxx");
                    startActivity(intent);
                }
            }
        });
    }
}

