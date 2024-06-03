package com.example.csi460_midterm_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;

//Class is used to allow user to enter in information for a new dog and add it to the list
public class AddDog extends AppCompatActivity {

    //Declared Variables for our layout components and DB handler
    EditText dogName, dogBreed, dogAge, dogGender, dogFurColor, dogVaccinated;
    ImageView dogImg;
    Button addImgBtn, submitBtn, seeAllBtn;
    DBHandler dbHandler;

    //Request code for picking an image
    private static final int PICK_IMAGE_REQUEST = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        //Initializes Variables
        dogName = findViewById(R.id.dogName);
        dogBreed = findViewById(R.id.dogBreed);
        dogAge = findViewById(R.id.dogAge);
        dogGender = findViewById(R.id.dogGender);
        dogFurColor = findViewById(R.id.dogFurColor);
        dogVaccinated = findViewById(R.id.dogVaccinated);
        dogImg = findViewById(R.id.dogImg);

        //Initializes Buttons
        addImgBtn = findViewById(R.id.imgBtn);
        submitBtn = findViewById(R.id.submitBtn);
        seeAllBtn = findViewById(R.id.seeAllBtn);

        dbHandler = new DBHandler(AddDog.this);


        //Button for selecting an image
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uses intent to let the user go and select an image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the dog information entered by the user and saves it to string variables
                String name = dogName.getText().toString();
                String breed = dogBreed.getText().toString();
                String ageString = dogAge.getText().toString();
                String gender = dogGender.getText().toString();
                String furColor = dogFurColor.getText().toString();
                String vaccination = dogVaccinated.getText().toString();

                //Validates whether all text areas have a value, displays a toast message if not
                if(name.isEmpty() || breed.isEmpty() || ageString.isEmpty() || gender.isEmpty() || furColor.isEmpty() || vaccination.isEmpty()){
                    Toast.makeText(AddDog.this, "One or More Input Fields Are Missing", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Parses the miles to make sure its a valid number
                int age;
                try {
                    age = Integer.parseInt(ageString);
                }catch(NumberFormatException e){
                    Toast.makeText(AddDog.this, "Please Enter a Valid Number for Age", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if the user input for vaccination is either yes or no
                if (!vaccination.equalsIgnoreCase("Yes") && !vaccination.equalsIgnoreCase("No")) {
                    Toast.makeText(AddDog.this, "Please type Either Yes or No For Vaccination Status", Toast.LENGTH_SHORT).show();
                    return;
                }


                //Gets the bitmap from the image view
                Bitmap bitImg = ((BitmapDrawable) dogImg.getDrawable()).getBitmap();

                //Calls method from dbHandler to add new dog information to the SQLite database
                dbHandler.addDog(name, breed, age, gender, furColor, vaccination, bitImg);

                //Displays message that the dogs information has been saved to the db
                Toast.makeText(AddDog.this, "Dog's Information Has Been Saved.", Toast.LENGTH_SHORT).show();

                //Clears the text fields and sets imageview back to placeholder image
                dogName.setText("");
                dogBreed.setText("");
                dogAge.setText("");
                dogGender.setText("");
                dogFurColor.setText("");
                dogVaccinated.setText("");
                dogImg.setImageResource(R.drawable.dog_placeholder);
            }
        });


        seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to go to the activity to see/read the list of dogs.
                Intent intent = new Intent(AddDog.this, DogsList.class);
                startActivity(intent);
            }
        });

    }

    //Gets and displays the selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imgUri = data.getData();

            //Displays the dog's img in the ImageView
            dogImg.setImageURI(imgUri);
        }
    }

}