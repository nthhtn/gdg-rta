package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.enums.GameMode;
import hackathon_mobile_2016.randomio.enums.Team;
import hackathon_mobile_2016.randomio.model.NumberBall;
import hackathon_mobile_2016.randomio.services.MatchService;
import hackathon_mobile_2016.randomio.views.NumberView;

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
        List<View> listButtons = convertToViews(listNumberBalls);
        replaceAllNumberButtonToLayout(listButtons);
    }

    private List<View> convertToViews(List<NumberBall> listNumberBalls) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int widthRatio = width / 16;
        int heightRatio = height / 9;

        List<View> listButtons = new ArrayList<>();
        for (NumberBall numberBall : listNumberBalls) {
            NumberView numberView = (NumberView) inflateViewFromXML(R.layout.view_number);
            numberView.setText(String.valueOf(numberBall.getValue()));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
            params.leftMargin = (int) (numberBall.getX() * widthRatio);
            params.topMargin = (int) (numberBall.getY() * heightRatio * 0.9);
            numberView.setLayoutParams(params);

            numberView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View numberView) {
                    NumberView view = (NumberView) numberView;
                    int chosenNumber = Integer.parseInt(view.getText());
                    NumberBall numberInfo = MatchService.getNumberBallInfo(chosenNumber, 123);
                    if (numberInfo.getOwner() == Team.NO_TEAM) {
                        MatchService.chooseNumber(chosenNumber, currentTeam);
                        view.runDrawCircle();
                        return;
                    }
                }
            });
            listButtons.add(numberView);
        }

        return listButtons;
    }

    private View inflateViewFromXML(int resource) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resource, null, false);
    }

    private void replaceAllNumberButtonToLayout(List<View> listButtons) {
        layout.removeAllViews();
        for (View button : listButtons) {
            layout.addView(button);
        }
    }

}
