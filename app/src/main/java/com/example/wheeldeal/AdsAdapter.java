package com.example.wheeldeal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {
    private List<AdsInfo> adsInfoList;
    private OnItemClickListener listener;
    //private AdapterView.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(AdsInfo adsInfo);
    }
    // Constructor to initialize the adapter
    public AdsAdapter() {
        this.adsInfoList = new ArrayList<>(); // Initialize with an empty list
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_s, parent, false);  // Ensure list_view_s exists with correct IDs
        return new AdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        AdsInfo adsInfo = adsInfoList.get(position);


        holder.titleTV.setText(adsInfo.getaBrand());
        holder.areaTV.setText(adsInfo.getaLocation());
        holder.miniTV.setText("Capacity: " + adsInfo.getaCapacity() + " CC | Milage: " + adsInfo.getaMilage());// Capacity displayed as a string
        holder.priceTV.setText("Price:" + adsInfo.getaPrice() + ".00");  // Formatting price nicely

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(adsInfo);
            }
        });
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
        else{
            this.adsInfoList = new ArrayList<>();
        }
    }

    public static class AdsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV, areaTV, miniTV, priceTV;

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.addTitle);      // Ensure these IDs match your XML layout
            areaTV = itemView.findViewById(R.id.addArea);
            miniTV = itemView.findViewById(R.id.addMiniDetails);
            priceTV = itemView.findViewById(R.id.addPrice);
        }
    }
}