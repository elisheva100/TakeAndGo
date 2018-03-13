package com.example.owner.takeandgouser.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.backEnd.DB_manager;
import com.example.owner.takeandgouser.model.entities.Car;

/**
 * Created by Owner on 07/03/2018.
 */

public class MyCarDetailsView {

    public static View getView(Context context, final Car car) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.my_car_details, null);
        final DB_manager manager = DBManagerFactory.getManager();
        TextView carNumber = (TextView) view.findViewById(R.id.carNumber);
        TextView carModelNumber = (TextView) view.findViewById(R.id.carModelNumber);
        TextView carMileage = (TextView) view.findViewById(R.id.carMileage);
        TextView carBranch = (TextView) view.findViewById(R.id.carBranch);

        carNumber.setText("" + car.getNumber());
        carModelNumber.setText("" + car.getModelType());
        carMileage.setText("" + car.getMileage());
        carBranch.setText("" + car.getBranchNumber());

    return view;

}
}
