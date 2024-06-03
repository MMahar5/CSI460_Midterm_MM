package com.example.csi460_midterm_mm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;


//Recycler view adapter class is created to help bind the dog data to display as a list
public class RclAdapter extends RecyclerView.Adapter<RclAdapter.ListViewHolder> {

    //Variables for the context and Array List for dogs list data
    private Context context;
    private ArrayList<Dog> dogsArr;

    //Constructor
    public RclAdapter(Context context, ArrayList<Dog> dogsArr) {
        this.context = context;
        this.dogsArr = dogsArr;
    }


    //Recycler view adapter class methods

    //OnCreate method to create a new view holder and inflate the dog_item layout
    @NonNull
    @Override
    public RclAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item, parent, false);
        return new ListViewHolder(v);
    }

    //OnBind method to display and update the dog item data at the correct position
    @Override
    public void onBindViewHolder(@NonNull RclAdapter.ListViewHolder h, int pos) {

        //Sets the text views
        Dog dg = dogsArr.get(pos);
        h.name.setText(dg.getName());
        h.breed.setText("Breed: "+dg.getBreed());
        h.age.setText("Age: "+dg.getAge().toString()+" year(s) old");
        h.gender.setText("Gender: "+dg.getGender());
        h.furColor.setText("Fur Color: "+dg.getFurColor());
        h.vaccination.setText("Vaccinated? "+dg.getVaccinated());


        //Sets the image. If there is no image then sets the placeholder image
        Bitmap bitImg = dg.getImage();
        if(bitImg != null){
            h.img.setImageBitmap(bitImg);
        }else{
            h.img.setImageResource(R.drawable.dog_placeholder);
        }

        //On click listener is used so that when the item is clicked it goes to the update activity
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDog.class);

                //Passes the current dog information with the intent
                intent.putExtra("id", dg.getId());
                intent.putExtra("name", dg.getName());
                intent.putExtra("breed", dg.getBreed());
                intent.putExtra("age", dg.getAge());
                intent.putExtra("gender", dg.getGender());
                intent.putExtra("furColor", dg.getFurColor());
                intent.putExtra("vaccination", dg.getVaccinated());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                dg.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imgByteArr = stream.toByteArray();

                intent.putExtra("image",imgByteArr);

                //Starts the update dog activity
                context.startActivity(intent);
            }
        });

    }

    //Gets the number of our dogs in our array list
    @Override
    public int getItemCount() {
        return dogsArr.size();
    }

    //Viewholder class
    public class ListViewHolder extends RecyclerView.ViewHolder {

        //Variables for all of our layout components
        private TextView name, breed, age, gender, furColor, vaccination;
        private ImageView img;

        public ListViewHolder(@NonNull View item){
            super(item);
            //Initalize our layout components
            name = item.findViewById(R.id.dgName);
            breed = item.findViewById(R.id.dgBreed);
            age = item.findViewById(R.id.dgAge);
            gender = item.findViewById(R.id.dgGender);
            furColor = item.findViewById(R.id.dgFurColor);
            vaccination = item.findViewById(R.id.dgVaccinated);
            img = item.findViewById(R.id.dgImg);
        }
    }
}
