package com.example.owner.takeandgouser.controller;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.owner.takeandgouser.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AboutUsFragment extends Fragment implements View.OnClickListener {
    private Button aboutPhoneButton;
    private Button aboutMailButton;
    private Button aboutWebsiteButton;
    private Button aboutLocationButton;
    private View view;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_about_us, container, false);
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        findViews();
        return view;
    }
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-11 20:56:04 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        aboutPhoneButton = (Button)view.findViewById( R.id.aboutPhoneButton );
        aboutMailButton = (Button)view.findViewById( R.id.aboutMailButton );
        aboutWebsiteButton = (Button)view.findViewById( R.id.aboutWebsiteButton );
        aboutLocationButton = (Button)view.findViewById( R.id.aboutLocationButton );

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
            phoneApp();
        } else if ( v == aboutMailButton ) {
            mailApp();
        } else if ( v == aboutWebsiteButton ) {
            websiteApp();
        } else if ( v == aboutLocationButton ) {
            mapApp();
        }
    }
    private void phoneApp()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:026249770"));
        Intent chooser = Intent.createChooser(intent,"Call");
        startActivity(chooser);
    }
    private void mailApp()
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        String to = "service@takengo.com";
        intent.putExtra(Intent.EXTRA_EMAIL,to);
        intent.setType("text/plain");
        Intent chooser = Intent.createChooser(intent,"Send Email");
        startActivity(chooser);

    }
private void websiteApp()
{
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("https://www.car2go.com/US/en/"));
    Intent chooser = Intent.createChooser(intent,"Open browser");
    startActivity(chooser);
}
private void mapApp()
{
    Intent intent = new Intent(Intent.ACTION_VIEW);
    //intent.setData(Uri.parse("geo:31.782113, 35.219251"));
    intent.setData(Uri.parse("http://maps.google.com/maps?daddr=31.782113,35.219251"));
    //intent.setData(Uri.parse("google.navigation:q=Ben Yehuda 1+Jerusalem"));
    Intent chooser = Intent.createChooser(intent,"Launch Maps");
    startActivity(chooser);
}
}



