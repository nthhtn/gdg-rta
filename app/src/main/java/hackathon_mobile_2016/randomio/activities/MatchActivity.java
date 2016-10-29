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


import java.util.ArrayList;
import java.util.List;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.enums.GameMode;
import hackathon_mobile_2016.randomio.enums.Team;
import hackathon_mobile_2016.randomio.model.NumberBall;
import hackathon_mobile_2016.randomio.services.MatchService;

public class MatchActivity extends Activity {

    private RelativeLayout layout;

    private Team currentTeam;
    private GameMode gameMode;
    private int maxNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        layout = (RelativeLayout) findViewById(R.id.matchView);

        currentTeam = Team.FIRST_TEAM;
        gameMode = GameMode.HARD;
        maxNumber = 100;
        List<NumberBall> listNumberBalls = MatchService.generateNewMatch(maxNumber, gameMode);
        List<Button> listButtons = convertToButtons(listNumberBalls);
        replaceAllNumberButtonToLayout(listButtons);

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
                    if (numberInfo.getOwner() == Team.NO_TEAM) {
                        MatchService.chooseNumber(chosenNumber, currentTeam);
                        button.setBackground(getResources().getDrawable(R.mipmap.ic_red_circle_2));
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
