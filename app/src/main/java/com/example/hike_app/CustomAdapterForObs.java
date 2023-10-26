package com.example.hike_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hike_app.models.ObservationsModel;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterForObs extends RecyclerView.Adapter<CustomAdapterForObs.ViewHolder> {
    private Context context;
    private List<ObservationsModel> observationsModels;

    public CustomAdapterForObs(Context context, ArrayList<ObservationsModel> observationsModels){
        this.context = context;
        this.observationsModels = observationsModels;
    }

    @NonNull
    @Override
    public CustomAdapterForObs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_observation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObservationsModel oList = observationsModels.get(position);
        holder.obs_id.setText(String.valueOf(oList.getIdObs()));
        holder.obs_name.setText(String.valueOf(oList.getNameObs()));
        holder.obs_date.setText(String.valueOf(oList.getDateObs()));
        holder.list_observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailObs.class);
                intent.putExtra("obs_id", String.valueOf(oList.getIdObs()));
                intent.putExtra("hike_id", String.valueOf(oList.getIdHike()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return observationsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView obs_id, obs_name, obs_date;
        LinearLayout list_observation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            obs_id = itemView.findViewById(R.id.obs_id_txt);
            obs_name = itemView.findViewById(R.id.obs_name_txt);
            obs_date = itemView.findViewById(R.id.obs_date_txt);
            list_observation = itemView.findViewById(R.id.list_observation);
        }
    }
    public void updateData(ArrayList<ObservationsModel> observationsModels) {
        this.observationsModels = observationsModels;
        notifyDataSetChanged();
    }
}
