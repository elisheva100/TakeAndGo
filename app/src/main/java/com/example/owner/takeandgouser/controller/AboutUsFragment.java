package com.example.owner.takeandgouser.controller;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.owner.takeandgouser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment implements View.OnClickListener {
    private Button aboutPhoneButton;
    private Button aboutMailButton;
    private Button aboutWebsiteButton;
    private Button aboutLocationButton;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-11 20:56:04 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        aboutPhoneButton = (Button)getView().findViewById( R.id.aboutPhoneButton );
        aboutMailButton = (Button)getView().findViewById( R.id.aboutMailButton );
        aboutWebsiteButton = (Button)getView().findViewById( R.id.aboutWebsiteButton );
        aboutLocationButton = (Button)getView().findViewById( R.id.aboutLocationButton );

        aboutPhoneButton.setOnClickListener( this );
        aboutMailButton.setOnClickListener( this );
        aboutWebsiteButton.setOnClickListener( this );
        aboutLocationButton.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-01-11 20:56:04 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == aboutPhoneButton ) {
            // Handle clicks for aboutPhoneButton
        } else if ( v == aboutMailButton ) {
            // Handle clicks for aboutMailButton
        } else if ( v == aboutWebsiteButton ) {
            // Handle clicks for aboutWebsiteButton
        } else if ( v == aboutLocationButton ) {
            // Handle clicks for aboutLocationButton
        }
    }

}



