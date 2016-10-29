package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import hackathon_mobile_2016.randomio.R;

import hackathon_mobile_2016.randomio.model.NumberBall;
import hackathon_mobile_2016.randomio.services.MatchService;
import hackathon_mobile_2016.randomio.services.Network;

public class MatchActivity extends Activity {
    private String TAG = "ductri";
    private RelativeLayout layout;

    private int currentTeam;
    private int gameMode;
    private int maxNumber;
    private boolean isHost = true;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHost = Boolean.parseBoolean(getIntent().getStringExtra("isHost"));
        roomId = getIntent().getStringExtra("roomId");
        currentTeam = Integer.parseInt(getIntent().getStringExtra("team"));
        gameMode = Integer.parseInt(getIntent().getStringExtra("mode"));
        maxNumber = Integer.parseInt(getIntent().getStringExtra("maxNumber"));

        setContentView(R.layout.activity_match);
        layout = (RelativeLayout) findViewById(R.id.matchView);


        if (isHost) {
            final List<NumberBall> listNumberBalls = MatchService.generateNewMatch(200, 1);
            MatchService.serverStartMatch(listNumberBalls, roomId, new MatchService.GameRender() {
                @Override
                public void success(List<NumberBall> numberBalls) {

                    final List<Button> listButtons = convertToButtons(numberBalls);
                    replaceAllNumberButtonToLayout(listButtons);
                }

                @Override
                public void renderChoosenNumber(int number) {
                    Log.i(TAG, "Number "+Integer.toString(number)+ " is choosen");
                }
            });
        } else {
            MatchService.clientStartMatch(roomId, new MatchService.GameRender() {
                @Override
                public void success(List<NumberBall> numberBalls) {
                    Log.i(TAG, Integer.toString(numberBalls.size()));
                    final List<Button> listButtons = convertToButtons(numberBalls);
                    replaceAllNumberButtonToLayout(listButtons);
                }

                @Override
                public void renderChoosenNumber(int number) {
                    Log.i(TAG, "Number "+Integer.toString(number)+ " is choosen");
                }

            });
        }

        DatabaseReference points = Network.firebaseDatabase.getReference("game/"+roomId+"/point");

    }

    private List<Button> convertToButtons(List<NumberBall> listNumberBalls) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int widthRatio = width / 16;
        int heightRatio = height / 9;

        List<Button> listButtons = new ArrayList<>();
        for (NumberBall numberBall : listNumberBalls) {
            Button button = inflateButtonFromXML(R.layout.number_button);

            button.setText(String.valueOf(numberBall.getValue()));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
            params.leftMargin = (int) (numberBall.getX() * widthRatio);
            params.topMargin = (int) (numberBall.getY() * heightRatio * 0.9);
            button.setLayoutParams(params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    int chosenNumber = Integer.parseInt(button.getText().toString());
                    NumberBall numberInfo = MatchService.getNumberBallInfo(chosenNumber, 123);
                    if (numberInfo.getOwner() == 0) {
                        MatchService.chooseNumber(roomId, chosenNumber, currentTeam);
                        button.setBackground(getResources().getDrawable(R.mipmap.ic_red_circle));
                        return;
                    }
                }
            });
            listButtons.add(button);
        }

        return listButtons;
    }

    private Button inflateButtonFromXML(int resource) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (Button) inflater.inflate(resource, null, false);
    }

    private void replaceAllNumberButtonToLayout(List<Button> listButtons) {
        layout.removeAllViews();
        for (Button button : listButtons) {
            layout.addView(button);
        }
    }

}
