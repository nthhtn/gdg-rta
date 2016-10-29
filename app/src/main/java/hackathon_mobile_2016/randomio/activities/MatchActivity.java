package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import hackathon_mobile_2016.randomio.R;
import hackathon_mobile_2016.randomio.enums.GameMode;
import hackathon_mobile_2016.randomio.model.NumberBall;
import hackathon_mobile_2016.randomio.services.MatchService;

public class MatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.matchView);
        List<NumberBall> listNumberBalls = MatchService.generateNewMatch(100, GameMode.HARD);
        List<Button> listButtons = convertToButtons(listNumberBalls, GameMode.HARD);
        layout.removeAllViews();
        for (Button btn : listButtons) {
            layout.addView(btn);
        }
    }

    private List<Button> convertToButtons(List<NumberBall> listNumberBalls, GameMode gamemode) {
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
                    // TODO: use button.getText() here
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

}
