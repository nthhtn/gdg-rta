package com.example.flynn.myapplication2;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import static android.R.attr.typeface;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface titleFont = Typeface.createFromAsset(getAssets(), "fonts/Title.ttf");

        TextView createRoom = (TextView)findViewById(R.id.lblCreateRoom);
        createRoom.setTypeface(titleFont);


    }
}
