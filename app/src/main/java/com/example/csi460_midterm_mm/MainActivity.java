package com.example.csi460_midterm_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//MainActivity is a homepage screen with two buttons to either create a dog or see the current list
public class MainActivity extends AppCompatActivity {

    //Variables for the buttons
    private Button addNewBtn, seeAllBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize buttons
        addNewBtn = findViewById(R.id.addBtn);
        seeAllBtn = findViewById(R.id.seeAllBtn);

        //Click listenser for add new dog button
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uses intent to go to the AddDog activity
                Intent i = new Intent(MainActivity.this, AddDog.class);
                startActivity(i);
            }
        });

        //Click listenser for see all dogs button
        seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uses intent to go to the DogsList activity
                Intent i = new Intent(MainActivity.this, DogsList.class);
                startActivity(i);
            }
        });

    }
}