package hackathon_mobile_2016.randomio.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.services.Network;
import hackathon_mobile_2016.randomio.utils.Utils;
import hackathon_mobile_2016.randomio.model.Room;

public class Mainform extends AppCompatActivity {
    String TAG = "ductri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainform);

        //Typeface titleFont = Typeface.createFromAsset(getAssets(), "fonts/Title.ttf");

        //TextView createRoom = (TextView)findViewById(R.id.lblCreateRoom);
        //createRoom.setTypeface(titleFont);

        final EditText roomNameEditText = (EditText)findViewById(R.id.roomNameTxt);
        final RadioGroup gameModeRadioGroup = (RadioGroup)findViewById(R.id.modeRadio);
        final EditText maxNoPlayer = (EditText)findViewById(R.id.maxPlayersTxt);
        Button createRoom = (Button)findViewById(R.id.btnCreateRoom);

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference roomsManager = Network.firebaseDatabase.getReference("room");
                //Add new room
                String key = roomsManager.push().getKey();
                int gameMode=1;
                switch (gameModeRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.mode_easy: gameMode=0;
                        break;
                    case R.id.mode_medium: gameMode = 1;
                        break;
                    case R.id.mode_hard: gameMode = 2;
                        break;
                    default:
                        gameMode = 1;
                }

                Room room = new Room(1, roomNameEditText.getText().toString(), gameMode,
                        Integer.parseInt(maxNoPlayer.getText().toString()), 0, "xxx");
                roomsManager.child(key).setValue(room);
            }
        });

    }


}
