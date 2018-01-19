package com.example.owner.takeandgouser.controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Car;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableCarsFragment extends Fragment {
    ListView listCars;
    CarAdapter carAdapter;
    private View view;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-16 22:19:10 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    public AvailableCarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_available_cars, container, false);
        initByListView();
        return view;
    }
    public void initByListView()
    {

        try
        {
            new AsyncTask<Car, Void, List<Car>>() {
                @Override
                protected void onPostExecute(final List<Car> myItemList) {
                    setAdapter(myItemList);
                }

                @Override
                protected List<Car> doInBackground(Car... params) {
                    try {
                        return DBManagerFactory.getManager().getAvailableCars();
                    }
                    catch (Exception e) {
                        return null;
                    }
                }

            }.execute();
        }
        catch (Exception e) {
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void setAdapter(List<Car>carsList) {
        this.carAdapter = new CarAdapter(this.getContext(),carsList);
        this.listCars.setAdapter(this.carAdapter);

    }

}
