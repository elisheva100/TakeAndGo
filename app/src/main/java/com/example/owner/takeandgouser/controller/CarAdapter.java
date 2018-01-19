package com.example.owner.takeandgouser.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.entities.Car;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends ArrayAdapter<Car> {
    public CarAdapter(Context context, List<Car> cars) {
        super(context, 0, cars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //-- Get the data item for this position
        Car car = getItem(position);

        //-- Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_cars, parent, false);
        }
        //-- Lookup view for data population
        TextView carNumber = (TextView) convertView.findViewById(R.id.CarNumber);
        TextView branchNumber = (TextView) convertView.findViewById(R.id.BranchNumber);
        TextView carModel = (TextView) convertView.findViewById(R.id.modelTpe);
        TextView mileage = (TextView) convertView.findViewById(R.id.mileage);



        // Populate the data into the template view using the data object
        carNumber.setText("Car number:" + car.getNumber());
        branchNumber.setText("Branch number: " + car.getBranchNumber());
        carModel.setText("Model id: " + car.getModelType());
        mileage.setText("Kilometers: " + car.getMileage());




        // Return the completed view to render on screen
        return convertView;
    }
}