package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hackathon_mobile_2016.randomio.R;

public class ManageRoomActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_room);

        Button btnBegin = (Button) findViewById(R.id.btnBegin);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                intent.putExtra("isHost", "true");
                intent.putExtra("roomId", "-KVFHhZrr2qZ1d-rNnDt");
                intent.putExtra("team", "2");
                intent.putExtra("mode", "1");
                intent.putExtra("maxNumber", "10");
                intent.putExtra("playerId","2");
                intent.putExtra("playerName", "ductri");
                startActivity(intent);
            }
        });
    }
}
