package com.example.hike_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.hike_app.models.HikeModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList<String> originalHikeId;
    private ArrayList<String> hike_id, hike_name, hike_location, hike_date, hike_level, hike_description;
    private ArrayList<Double> hike_length;
    private ArrayList<Boolean>  hike_parking;
    private List<HikeModel> hikeModels;
    private List<HikeModel> hikeModelsAll;
//    public CustomAdapter(Context context, ArrayList<String> hike_id, ArrayList<String> hike_name, ArrayList<String> hike_location){
//        this.context = context;
//        this.hike_id = hike_id;
//        this.hike_name = hike_name;
//        this.hike_location = hike_location;
//        this.originalHikeId = new ArrayList<>(hike_id);
//
//    }
    public CustomAdapter(Context context, ArrayList<HikeModel> hikeModels){
        this.context = context;
        this.hikeModels = hikeModels;
        this.hikeModelsAll = hikeModels;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_hike, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
//        holder.hike_id.setText(String.valueOf(hike_id.get(holder.getAdapterPosition())));
//        holder.hike_name.setText(String.valueOf(hike_name.get(holder.getAdapterPosition())));
//        holder.hike_location.setText(String.valueOf(hike_location.get(holder.getAdapterPosition())));

        HikeModel hList = hikeModels.get(position);
        holder.hike_id.setText(String.valueOf(hList.getId()));
        holder.hike_name.setText(String.valueOf(hList.getHikeName()));
        holder.hike_location.setText(String.valueOf(hList.getHikeLocation()));
        holder.detailHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHike.class);
                intent.putExtra("hike_id", String.valueOf(hList.getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikeModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hike_id, hike_name, hike_location;
        LinearLayout detailHike;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hike_id = itemView.findViewById(R.id.hike_id_txt);
            hike_name = itemView.findViewById(R.id.hike_name_txt);
            hike_location = itemView.findViewById(R.id.hike_location_txt);
            detailHike = itemView.findViewById(R.id.detailHike);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchTxt = constraint.toString();
                if(searchTxt.isEmpty()){
                    hikeModels = hikeModelsAll;
                } else {
                    List<HikeModel> list = new ArrayList<>();
                    for(HikeModel hike : hikeModelsAll){
                        if(hike.getHikeName().toLowerCase().contains(constraint.toString().toLowerCase()) || hike.getHikeLocation().toLowerCase().contains(constraint.toString().toLowerCase())){
                            list.add(hike);
                        }
                    }
                    hikeModels = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = hikeModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                hikeModels = (List<HikeModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

