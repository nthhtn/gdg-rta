package hackathon_mobile_2016.randomio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hackathon_mobile_2016.randomio.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button buttonLoadMatch = (Button) findViewById(R.id.buttonLoadMatch);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainform.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaitingRoom.class);
                intent.putExtra("roomId", "-KVFHhZrr2qZ1d-rNnDt");
                startActivity(intent);
            }
        });

        buttonLoadMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                intent.putExtra("isHost", "true");
                intent.putExtra("roomId", "-KVFHhZrr2qZ1d-rNnDt");
                intent.putExtra("team", 1);
                intent.putExtra("mode", 1);
                intent.putExtra("maxNumber", 10);
                startActivity(intent);
            }
        });


    }
}
