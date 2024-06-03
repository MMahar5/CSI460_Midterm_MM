package com.example.csi460_midterm_mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

//Class for displaying our database dog information in a list
public class DogsList extends AppCompatActivity {

    //Declares Variables for our recycler view, db handler and a new array list
    private ArrayList<Dog> dogsArr;
    private RclAdapter rclAdapter;
    private RecyclerView rclView;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        //Initializes variables
        dogsArr = new ArrayList<>();
        dbHandler = new DBHandler(DogsList.this);

        //Reads the dogs list data from DbHandler and passes it to the rcl adapter class
        dogsArr = dbHandler.readDogs();
        rclAdapter = new RclAdapter(DogsList.this, dogsArr);
        rclView = findViewById(R.id.rclView);

        //Sets the layout manager and the rcl adapter for the recycler view
        LinearLayoutManager linLayout = new LinearLayoutManager(DogsList.this, RecyclerView.VERTICAL, false);
        rclView.setLayoutManager(linLayout);
        rclView.setAdapter(rclAdapter);
    }
}