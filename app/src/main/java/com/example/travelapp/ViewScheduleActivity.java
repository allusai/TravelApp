package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewScheduleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
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
        /*if(message != null) {
            textView.setText(message.substring(0, 9)); // instead of the message/encoded string which was passed in
        }*/

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

        //Recycler View setup
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ScheduleAdapter(q.getOnlySelectedLocations(), q);
        recyclerView.setAdapter(mAdapter);
    }
}