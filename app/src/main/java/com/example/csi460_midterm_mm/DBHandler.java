package com.example.csi460_midterm_mm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    //Database variables for table and cols
    private static final String DB_NAME = "dogsDb.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "dogsList";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String BREED_COL = "breed";
    private static final String AGE_COL = "age";
    private static final String GENDER_COL = "gender";
    private static final String FURCOLOR_COL = "furColor";
    private static final String VACCINATED_COL = "vaccinated";
    private static final String IMAGE_PATH_COL = "image";

    //Database handler constructor


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Method to create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query to set column names and data types
        String carsDbQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + BREED_COL + " TEXT,"
                + AGE_COL + " INTEGER,"
                + GENDER_COL + " TEXT,"
                + FURCOLOR_COL + " TEXT,"
                + VACCINATED_COL + " TEXT,"
                + IMAGE_PATH_COL + " BLOB)";

        db.execSQL(carsDbQuery);
    }


    //Method to add new a new dog to our dogsDb.
    public void addDog(String dogName, String dogBreed, Integer dogAge, String dogGender, String dogFurColor, String dogVaccination, Bitmap dogImg) {

        //Sets DB and Content values
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        //Convert dog image bitmap to byte array
        ByteArrayOutputStream bitImg = new ByteArrayOutputStream();
        dogImg.compress(Bitmap.CompressFormat.PNG, 100, bitImg);
        byte[] byteArr = bitImg.toByteArray();

        //Pass key value pairs of data for the dog
        v.put(NAME_COL, dogName);
        v.put(BREED_COL, dogBreed);
        v.put(AGE_COL, dogAge);
        v.put(GENDER_COL, dogGender);
        v.put(FURCOLOR_COL, dogFurColor);
        v.put(VACCINATED_COL, dogVaccination);
        v.put(IMAGE_PATH_COL, byteArr);

        //Pass content value to the table and close dogsDb
        db.insert(TABLE_NAME, null, v);
        db.close();
    }


    //Method used to check if table exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    //Method for reading the dogs list data
    public ArrayList<Dog> readDogs(){

        //Creates database and query to read the data from the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor dogsCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        //Creates array list for storing the data for the list of dogs
        ArrayList<Dog> dogsArr = new ArrayList<>();

        //Puts cursor at the start position
        if(dogsCursor.moveToFirst()){
            //do while loop to add data to the array list
            do{
                //Converts the byte array back to bitmap for our dog image
                @SuppressLint("Range") byte[] byteArr = dogsCursor.getBlob(dogsCursor.getColumnIndex(IMAGE_PATH_COL));
                Bitmap dogImg = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);


                dogsArr.add(new Dog(dogsCursor.getInt(0), dogsCursor.getString(1), dogsCursor.getString(2),
                        dogsCursor.getInt(3), dogsCursor.getString(4),
                        dogsCursor.getString(5), dogsCursor.getString(6), dogImg));

            }while(dogsCursor.moveToNext());
        }
        dogsCursor.close();

        //Returns the array list of dogs
        return dogsArr;
    }




    //Method to update a dogs information
    public void updateDog(Integer dogId, String name, String breed, Integer age, String gender, String furColor, String vaccination, Bitmap img)
    {

        //Gets our sqlite database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        //Need to convert our dog image bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imgByteArr = stream.toByteArray();

        //Passes our updated content values
        v.put(NAME_COL, name);
        v.put(BREED_COL, breed);
        v.put(AGE_COL, age);
        v.put(GENDER_COL, gender);
        v.put(FURCOLOR_COL, furColor);
        v.put(VACCINATED_COL, vaccination);
        v.put(IMAGE_PATH_COL, imgByteArr);

        //Updates our database table with the content values where the dog is equal to the given unique id then close the dogsDb
        db.update(TABLE_NAME, v, ID_COL + "=?", new String[]{String.valueOf(dogId)});
        db.close();
    }


    //Method for removing a dog from the list
    public void deleteDog(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        //Removes the dog from the sqlite db table where the dog is equal to the given unique id then close the Db
        db.delete(TABLE_NAME, ID_COL+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

}

