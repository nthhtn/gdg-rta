package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import hackathon_mobile_2016.randomio.R;

import hackathon_mobile_2016.randomio.model.NumberBall;
import hackathon_mobile_2016.randomio.model.RoomMember;
import hackathon_mobile_2016.randomio.services.MatchService;

import hackathon_mobile_2016.randomio.services.Network;

import hackathon_mobile_2016.randomio.services.ScoreGameService;
import hackathon_mobile_2016.randomio.views.NumberView;


public class MatchActivity extends Activity {
    private String TAG = "ductri";
    private RelativeLayout layout;

    private int currentTeam;
    private int gameMode;
    private int maxNumber;
    private boolean isHost = true;
    private String roomId;
    List<View> listViews = null;

    private RoomMember player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHost = Boolean.parseBoolean(getIntent().getStringExtra("isHost"));
        roomId = getIntent().getStringExtra("roomId");
        currentTeam = Integer.parseInt(getIntent().getStringExtra("team"));
        gameMode = Integer.parseInt(getIntent().getStringExtra("mode"));
        maxNumber = Integer.parseInt(getIntent().getStringExtra("maxNumber"));
        String playerId = getIntent().getStringExtra("playerId");
        String playerName = getIntent().getStringExtra("playerName");
        player = new RoomMember(playerId, playerName, 0, currentTeam, 1);

        setContentView(R.layout.activity_match);
        layout = (RelativeLayout) findViewById(R.id.matchView);

        if (isHost) {
            final List<NumberBall> listNumberBalls = MatchService.generateNewMatch(200, 1);
            MatchService.serverStartMatch(listNumberBalls, roomId, new MatchService.GameRender() {
                @Override
                public void success(List<NumberBall> numberBalls) {

                    listViews = convertToViews(numberBalls);
                    replaceAllNumberButtonToLayout(listViews);
                }

                @Override
                public void renderChoosenNumber(int number) {
                    Log.i(TAG, "Number "+Integer.toString(number)+ " is choosen");
                    for (View v:listViews) {
                        NumberView numberView = (NumberView)v;
                        if (numberView.getText().equals(Integer.toString(number))) {
                            if (!numberView.isClicked) {
                                numberView.runDrawCircle();
                            }

                            break;
                        }
                    }
                }
            });
        } else {
            MatchService.clientStartMatch(roomId, new MatchService.GameRender() {
                @Override
                public void success(List<NumberBall> numberBalls) {
                    Log.i(TAG, Integer.toString(numberBalls.size()));
                    listViews = convertToViews(numberBalls);
                    replaceAllNumberButtonToLayout(listViews);
                }

                @Override
                public void renderChoosenNumber(int number) {
                    Log.i(TAG, "Number "+Integer.toString(number)+ " is choosen");
                    for (View v:listViews) {
                        NumberView numberView = (NumberView)v;
                        if (numberView.getText().equals(Integer.toString(number))) {
                            if (!numberView.isClicked) {
                                numberView.runDrawCircle();
                            }

                            break;
                        }
                    }
                }

            });
        }

        DatabaseReference points = Network.firebaseDatabase.getReference("game/"+roomId+"/point");

        //Tracking score
        DatabaseReference team1 = Network.firebaseDatabase.getReference("game/"+roomId+"/team1");
        team1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ScoreGameService.team1 = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference team2 = Network.firebaseDatabase.getReference("game/"+roomId+"/team2");
        team2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ScoreGameService.team2 = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

                    if (numberInfo.getOwner() == 0) {
                        MatchService.chooseNumber(roomId, chosenNumber, currentTeam);
                        //button.setBackground(getResources().getDrawable(R.mipmap.ic_red_circle));
                        view.isClicked = true;
                        view.runDrawCircle();
                        if (player.getTeam()==1) {
                            ScoreGameService.team1++;
                            ScoreGameService.updateScoreTeam1(roomId, ScoreGameService.team1);
                            Log.i(TAG, "Score team 1: "+Integer.toString(ScoreGameService.team1));
                        } else {
                            ScoreGameService.team2++;
                            ScoreGameService.updateScoreTeam2(roomId, ScoreGameService.team2);
                            Log.i(TAG, "Score team 2: "+Integer.toString(ScoreGameService.team2));
                        }
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
