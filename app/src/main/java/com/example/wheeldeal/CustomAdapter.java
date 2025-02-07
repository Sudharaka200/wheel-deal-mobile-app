package com.example.wheeldeal;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList category, brand, img1, milage, price, location;

    CustomAdapter(Context context, ArrayList category, ArrayList brand, ArrayList img1, ArrayList milage, ArrayList price, ArrayList location){
        this.context = context;
        this.category = category;
        this.brand = brand;
        this.img1 = img1;
        this.milage = milage;
        this.price = price;
        this.location = location;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view_s,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Uri imageUri = Uri.parse((String) img1.get(position));
        holder.imgViewAdd.setImageURI(imageUri);

        holder.vCategory.setText(String.valueOf(category.get(position)));
        holder.vMilage.setText(String.valueOf(milage.get(position)));
        holder.vPrice.setText(String.valueOf(price.get(position)));
        holder.vLocation.setText(String.valueOf(location.get(position)));


    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgViewAdd;
        TextView vCategory, vMilage, vPrice, vLocation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewAdd = itemView.findViewById(R.id.addImg);
            vCategory = itemView.findViewById(R.id.addTitle);
            vMilage = itemView.findViewById(R.id.addMiniDetails);
            vPrice = itemView.findViewById(R.id.addPrice);
            vLocation = itemView.findViewById(R.id.addArea);

        }
    }
}
