package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewScheduleActivity extends AppCompatActivity {
    private QueryMaker q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        //Now set up QueryMaker q and decode the message
        q = new QueryMaker(this);
        if(message == null)
        {
            System.out.println("View Schedule Activity Error: Message Received is null");
        }
        else {
            q.loadPlacesListFromAString(message, this);
            q.printMyData();
        }

    }
}