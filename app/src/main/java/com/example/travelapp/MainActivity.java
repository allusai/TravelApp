package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MESSAGE = "com.example.travelapp.MESSAGE";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private QueryMaker q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Set up rest of Android App w/ super.onCreate()
        setContentView(R.layout.activity_main); // Use "activity_main.xml" as the UI for this activity
        /*BottomNavigationView navView = findViewById(R.id.nav_view); // navView template is loaded
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build(); //The app bar at the bottom is set up with three tabs to switch between
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); //Make app bar functional
        NavigationUI.setupWithNavController(navView, navController); //Combine everything together to make the activity work
        */


        //Spinner View setup
        Spinner spinner = findViewById(R.id.locations_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Recycler View setup
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Pre-fetch data for Paris   1
        q = new QueryMaker(this);
        q.printMyData();
        TouristLocation[] myDataset = q.search("Paris");

        // specify an adapter (see also next example)  2
        mAdapter = new LocationAdapter(myDataset, q, getSupportFragmentManager());
        recyclerView.setAdapter(mAdapter);
    }

    /* Called when the user taps the Send button
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class); //DisplayMessageActivity is the new page we will be opening
        EditText editText = (EditText) findViewById(R.id.editText); //The search box's id is 'editText', check it out
        String message = editText.getText().toString(); //Get the current text in the search box
        intent.putExtra(EXTRA_MESSAGE, message); //Forwards the text to the next activity via the intent's "extras" array, def. data type above
        startActivity(intent); //Launch the next activity!
    } */

    /* Search: Called when user chooses a city from the list to explore*/
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        String newSelectedLocation = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), newSelectedLocation, Toast.LENGTH_SHORT).show();

        //Fetch data for Paris
        TouristLocation[] newDataset = q.search(newSelectedLocation);

        // specify an adapter (see also next example)
        mAdapter = new LocationAdapter(newDataset, q, getSupportFragmentManager());
        recyclerView.setAdapter(mAdapter);
    }

    /* Search: Called by default*/
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    /* Open Your Schedule: called when that activity button is pressed*/
    public void openSchedule(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ViewScheduleActivity.class); //DisplayMessageActivity is the new page we will be opening
        String message = q.turnDataIntoSerializedString(); //Get the current text in the search box
        intent.putExtra(EXTRA_MESSAGE, message); //Forwards the text to the next activity via the intent's "extras" array, def. data type above
        startActivity(intent); //Launch the next activity!
    }

}