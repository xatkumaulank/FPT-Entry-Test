package com.example.fptentrytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    Context context;
    List<Vehicle> vehicleList;
    ItemClickListenner itemClickListenner;

    public VehicleAdapter(Context context, List<Vehicle> vehicleList, ItemClickListenner itemClickListenner) {
        this.context = context;
        this.vehicleList = vehicleList;
        this.itemClickListenner = itemClickListenner;
    }

    public void setData(List<Vehicle> data){
        this.vehicleList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_item_vehicle,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        if (vehicleList == null){
            return;
        }
        Vehicle vehicle = vehicleList.get(position);
        holder.id_vehicle.setText(String.valueOf(vehicle.getId()));
        holder.name_vehicle.setText(vehicle.getName());
        holder.type_vehicle.setText(vehicle.getType());
        holder.price_vehicle.setText(String.valueOf(vehicle.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (vehicleList != null){
            return vehicleList.size();
        }
        return 0;
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView id_vehicle, name_vehicle, type_vehicle, price_vehicle;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            id_vehicle = itemView.findViewById(R.id.id_vehicle);
            name_vehicle = itemView.findViewById(R.id.name_vehicle);
            type_vehicle = itemView.findViewById(R.id.type_vehicle);
            price_vehicle = itemView.findViewById(R.id.price_vehicle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListenner.onClick(v,getAdapterPosition());
        }
    }
}
