package com.example.csi460_midterm_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;

//Update class gets the information based on the dog selected and updates any information or removes the dog based on the user request
public class UpdateDog extends AppCompatActivity {

    //Variables
    private EditText dogName, dogBreed, dogAge, dogGender, dogFurColor, dogVaccinated;
    private ImageView dogImg;
    private Button updateBtn, changeImgBtn, deleteBtn;
    private DBHandler dbHandler;
    String name, breed, gender, furColor, vaccination;
    Integer id, age;
    byte[] imgByteArr;
    Bitmap imgBit;

    //Request code for picking an image
    private static final int PICK_IMAGE_REQUEST = 1002;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dog);

        //Initializes the variables
        dogName = findViewById(R.id.dogName);
        dogBreed = findViewById(R.id.dogBreed);
        dogAge = findViewById(R.id.dogAge);
        dogGender = findViewById(R.id.dogGender);
        dogFurColor = findViewById(R.id.dogFurColor);
        dogVaccinated = findViewById(R.id.dogVaccinated);
        dogImg = findViewById(R.id.dogImg);

        //Initializes buttons
        updateBtn = findViewById(R.id.updateBtn);
        changeImgBtn = findViewById(R.id.changeImgBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        //Creates new instance of DbHandler with context of UpdateDog class
        dbHandler = new DBHandler(UpdateDog.this);

        //Gets the dog data passed from intent
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        breed = getIntent().getStringExtra("breed");
        age = getIntent().getIntExtra("age", 0);
        gender = getIntent().getStringExtra("gender");
        furColor = getIntent().getStringExtra("furColor");
        vaccination = getIntent().getStringExtra("vaccination");
        imgByteArr = getIntent().getByteArrayExtra("image");

        //Convert the image byte array to a bitmap
        if(imgByteArr != null){
            imgBit = BitmapFactory.decodeByteArray(imgByteArr, 0, imgByteArr.length);
        }

        //Set the values of our layout components
        dogName.setText(name);
        dogBreed.setText(breed);
        dogAge.setText(age.toString());
        dogGender.setText(gender);
        dogFurColor.setText(furColor);
        dogVaccinated.setText(vaccination);
        if(imgBit != null){
            dogImg.setImageBitmap(imgBit);
        }else{
            dogImg.setImageResource(R.drawable.dog_placeholder);
        }

        //Button to select/change the image
        changeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uses intent to let the user go and select an image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        //Update button gets and updates the old values with the new ones
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the dog information entered by the user
                name = dogName.getText().toString();
                breed = dogBreed.getText().toString();
                String ageString = dogAge.getText().toString();
                gender = dogGender.getText().toString();
                furColor = dogFurColor.getText().toString();
                vaccination = dogVaccinated.getText().toString();

                //Validates whether all text areas have a value, displays a toast message if not
                if(name.isEmpty() || breed.isEmpty() || ageString.isEmpty() || gender.isEmpty() || furColor.isEmpty() || vaccination.isEmpty()){
                    Toast.makeText(UpdateDog.this, "One or More Input Fields Are Missing", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Parses the age to make sure its a valid number
                try {
                    age = Integer.parseInt(ageString);
                }catch(NumberFormatException e){
                    Toast.makeText(UpdateDog.this, "Please Enter a Valid Number for Age", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if the user input for vaccination is either yes or no
                if (!vaccination.equalsIgnoreCase("Yes") && !vaccination.equalsIgnoreCase("No")) {
                    Toast.makeText(UpdateDog.this, "Please type Either Yes or No For Vaccination Status", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Gets the bitmap from the image view
                imgBit = ((BitmapDrawable) dogImg.getDrawable()).getBitmap();

                //Calls update method and passes our input values
                dbHandler.updateDog(id, name, breed, age, gender, furColor, vaccination, imgBit);
                //Message to let user know that the car has been updated
                Toast.makeText(UpdateDog.this, "Dog's Information Has Been Updated", Toast.LENGTH_SHORT).show();

                //Go back to MainActivity
                Intent intent = new Intent(UpdateDog.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Delete button to remove dog's information from the database
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Alert dialog box used to confirm deletion
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(UpdateDog.this);
                adBuilder.setTitle("Remove Dog");
                adBuilder.setMessage("Are You Sure You Want To Remove This Dog's Information?");

                adBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                adBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Calls delete method from DB handler
                        dbHandler.deleteDog(id);
                        //Displays message to the user
                        Toast.makeText(UpdateDog.this, "Dog's Information Has Been Deleted", Toast.LENGTH_SHORT).show();



                        //Returns to the dog list activity
                        Intent intent = new Intent(UpdateDog.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog dialog = adBuilder.create();
                dialog.show();
            }
        });
    }


    //Gets and displays a selected dog image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imgUri = data.getData();

            //Displays the dog img in the ImageView
            dogImg.setImageURI(imgUri);
        }
    }
}