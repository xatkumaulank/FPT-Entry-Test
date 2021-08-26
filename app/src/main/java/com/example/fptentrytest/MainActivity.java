package com.example.fptentrytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fptentrytest.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mDataBaseHelper;
    List<Vehicle> vehicleList;
    VehicleAdapter vehicleAdapter;
    ActivityMainBinding binding;
    ItemClickListenner listenner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        mDataBaseHelper = new DataBaseHelper(MainActivity.this);
        vehicleList = new ArrayList<>();
        setOnClickListener();
        vehicleAdapter = new VehicleAdapter(this,vehicleList,listenner);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        binding.rcvDisplace.setLayoutManager(linearLayoutManager);
        binding.rcvDisplace.setAdapter(vehicleAdapter);
        vehicleAdapter.setData(mDataBaseHelper.getEveryOne());

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                vehicleAdapter.setData(mDataBaseHelper.searchEveryoneByName(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBaseHelper.getIdOfEveryOne().contains(Integer.parseInt(binding.edtIdVehicle.getText().toString().trim()))){
                    Toast.makeText(MainActivity.this,"id này đã tồn tại",Toast.LENGTH_SHORT).show();
                }else {
                    mDataBaseHelper.addOne(getEditTextData());
                }
                clearText();
                vehicleAdapter.setData(mDataBaseHelper.getEveryOne());
            }
        });


    }
    private void setOnClickListener(){
        listenner = new ItemClickListenner() {
            @Override
            public void onClick(View view, int position) {
                openDialog(Gravity.BOTTOM, position);
            }
        };
    }
    private Vehicle getEditTextData(){
        int id = Integer.parseInt(binding.edtIdVehicle.getText().toString().trim());
        String name = binding.edtNameVehicle.getText().toString().trim();
        String type = binding.edtTypeVehicle.getText().toString().trim();
        int price = Integer.parseInt(binding.edtPriceVehicle.getText().toString().trim());
        return new Vehicle(id,name,type,price);
    }
    private void clearText(){
        binding.edtIdVehicle.setText("");
        binding.edtNameVehicle.setText("");
        binding.edtPriceVehicle.setText("");
        binding.edtTypeVehicle.setText("");
    }
    private void openDialog(int gravity, int position){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_item_vehicle);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);

        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        Vehicle vehicle = mDataBaseHelper.getOne(position);
        TextView tv_id = dialog.findViewById(R.id.id_vehicle);
        TextView tv_name = dialog.findViewById(R.id.name_vehicle);
        TextView tv_type = dialog.findViewById(R.id.type_vehicle);
        TextView tv_price = dialog.findViewById(R.id.price_vehicle);

        tv_id.setText(String.valueOf(vehicle.getId()));
        tv_name.setText(vehicle.getName());
        tv_type.setText(vehicle.getType());
        tv_price.setText(String.valueOf(vehicle.getPrice()));

        dialog.show();
    }
}