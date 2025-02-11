package com.example.wheeldeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    public List<AdsInfo> adsInfoList = new ArrayList<>();

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_s, parent, false);
        return new AdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        AdsInfo adsInfo = adsInfoList.get(position);

        holder.titleTV.setText(adsInfo.getaBrand() +" , " + adsInfo.getaModel());
        holder.areaTV.setText(adsInfo.getaLocation());
        holder.miniTV.setText("Capacity: " + adsInfo.getaCapacity() + " CC "+ "  |  "+ adsInfo.getaMilage()+" Km");// Capacity displayed as a string
        holder.priceTV.setText("Price:" + adsInfo.getaPrice() + ".00");  // Formatting price nicely

        if (adsInfo.getImageUrl() != null && !adsInfo.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(adsInfo.getImageUrl())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.images_23);
        }
    }


    @Override
    public int getItemCount() {
        return adsInfoList.size();
    }

    public void setAdsInfoList(List<AdsInfo> adsInfo) {
        if (adsInfo != null) {
            this.adsInfoList = adsInfo;
            notifyDataSetChanged();
        }
    }

    public static class AdsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTV, areaTV, miniTV, priceTV;

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.addTitle);
            areaTV = itemView.findViewById(R.id.addArea);
            miniTV = itemView.findViewById(R.id.addMiniDetails);
            priceTV = itemView.findViewById(R.id.addPrice);
            imageView = itemView.findViewById(R.id.imgCar);
        }
    }
}