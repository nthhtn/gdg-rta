package com.example.flynn.myapplication2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WaitingRoomActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
//        init();
    }

    public void init(){
        TableLayout table = (TableLayout) findViewById(R.id.tableWaiting);


        for (int i = 0; i <6; i++) {

            TableRow row= (TableRow) findViewById(R.id.tableRow);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView idTV = new TextView(this);
            TextView playerTV = new TextView(this);
            TextView teamTV = new TextView(this);
            TextView statusTV = new TextView(this);

            idTV.setText("1");
            playerTV.setText("Conan kun");
            teamTV.setText("Blue");
            statusTV.setText("Ready");

            row.addView(idTV);
            row.addView(playerTV);
            row.addView(teamTV);
            row.addView(statusTV);
            table.addView(row, i);
        }
    }
}
