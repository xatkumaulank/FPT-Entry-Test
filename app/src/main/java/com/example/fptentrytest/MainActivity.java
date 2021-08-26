package com.example.fptentrytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.fptentrytest.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mDataBaseHelper;
    List<Vehicle> vehicleList;
    VehicleAdapter vehicleAdapter;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        mDataBaseHelper = new DataBaseHelper(MainActivity.this);
        vehicleList = new ArrayList<>();
        vehicleAdapter = new VehicleAdapter(this,vehicleList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        binding.rcvDisplace.setLayoutManager(linearLayoutManager);
        binding.rcvDisplace.setAdapter(vehicleAdapter);
        vehicleAdapter.setData(mDataBaseHelper.getEveryOne());



        binding.btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBaseHelper.addOne(new Vehicle(1,"Honda","Motobike",13));
                vehicleAdapter.setData(mDataBaseHelper.getEveryOne());
            }
        });


    }
}