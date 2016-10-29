package hackathon_mobile_2016.randomio.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import hackathon_mobile_2016.randomio.R;

public class WaitingRoomActivity extends Activity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
//
//        TextView red = (TextView) inflateViewFromXML(R.layout.spinner_color);
//        red.setBackgroundColor(Color.RED);

        // Spinner Drop down elements
        List<Integer> categories = new ArrayList<>();
        categories.add(Color.BLACK);
        categories.add(Color.RED);
        categories.add(Color.rgb(226,95,2));
        categories.add(Color.rgb(0,175,2));
        categories.add(Color.BLUE);
        categories.add(Color.rgb(129,12,142));

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter = new Unknown(this, R.layout.spinner_color, categories);

        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

//        init();
    }

    public void init(){


//        TableLayout table = (TableLayout) findViewById(R.id.tableWaiting);
//
//        for (int i = 0; i <6; i++) {
//
//            TableRow row= (TableRow) findViewById(R.id.tableRow);
//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//            row.setLayoutParams(lp);
//
//            TextView idTV = new TextView(this);
//            TextView playerTV = new TextView(this);
//            TextView teamTV = new TextView(this);
//            TextView statusTV = new TextView(this);
//
//            idTV.setText("1");
//            playerTV.setText("Conan kun");
//            teamTV.setText("Blue");
//            statusTV.setText("Ready");
//
//            row.addView(idTV);
//            row.addView(playerTV);
//            row.addView(teamTV);
//            row.addView(statusTV);
//            table.addView(row, i);
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private View inflateViewFromXML(int resource) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resource, null, false);
    }
}