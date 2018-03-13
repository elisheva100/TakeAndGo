package com.example.owner.takeandgouser.controller;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;

import java.util.ArrayList;
import java.util.List;



public class CarsFragment extends Fragment {

    /*
    //definitions:
    private ExpandableListView carsExpandableList;
    private SearchView searchView;
    final MyExpandableListAdapter myBaseExpandableListAdapter = new MyExpandableListAdapter();
    //lists for cars:
    private static List<Car> filterList = new ArrayList<>();
    private static List<Car> Cars = new ArrayList<>();
    //host brunch details:
    private  static Branch hostBrunch;
    TextView HostBrunchTextView;
    private int HostBrunchNumber;

    public CarsFragment() {// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);
        carsExpandableList = ((ExpandableListView) rootView.findViewById(R.id.carsExpandableList));
        searchView = (SearchView) rootView.findViewById(R.id.carsSearchView);
        try {
            new GetCarsAsyncTask().execute();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return rootView;
    }


    private class GetCarsAsyncTask extends AsyncTask<Car, Void, List<Car>> {

        @Override
        protected void onPostExecute(List<Car> listFromBackground) {
            Cars = null;
            Cars = listFromBackground;
            filterList.clear();
            filterList.addAll(Cars);
            carsExpandableList.setAdapter(myBaseExpandableListAdapter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String text) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String text) {
                    myBaseExpandableListAdapter.getFilter().filter(text);
                    myBaseExpandableListAdapter.notifyDataSetChanged();
                    return false;
                }
            });
        }

        @Override
        protected List<Car> doInBackground(Car... params) {
            try {
                return DBManagerFactory.getManager().getAvailableCars();
            } catch (Exception e) {
                return  null;
            }
        }

    }



    private class HostBrunchAsyncTask extends AsyncTask<Void, Void, List<Branch>>
    {
        @Override
        protected void onPostExecute(List<Branch> listFromBackground) {

            for (Branch branch : listFromBackground)
            {
                if (branch.getBranchNumber()== HostBrunchNumber) {
                    hostBrunch = branch;
                    break;
                }
            }
            HostBrunchTextView.setText("Host brunch details:\n"
                    + "parking: " + String.valueOf(hostBrunch.getParking()) + '\n'
            + "brunch number: " + String.valueOf(hostBrunch.getBranchNumber()) + '\n'
            + "address: " + String.valueOf(hostBrunch.getAdress()));
        }

        @Override
        protected List<Branch> doInBackground(Void... params) {
            try {
                return DBManagerFactory.getManager().getBranches();
            } catch (Exception e) {
                return null;
            }
        }
    }

    class MyExpandableListAdapter extends BaseExpandableListAdapter implements Filterable, View.OnClickListener {

        @Override
        public int getGroupCount() {
            return Cars.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View header = getActivity().getLayoutInflater().inflate(R.layout.car_list_group, parent, false);
            TextView title = (TextView) header.findViewById(R.id.lblCarsListHeader);
            title.setText("Car Number: " + Cars.get(groupPosition).getNumber()+""+
                    " "+ "Model Type: " + Cars.get(groupPosition).getModelType()+"");
            return header;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
           View carsListItem = getActivity().getLayoutInflater().inflate(R.layout.car_list_item, parent, false);
            Car car= Cars.get(groupPosition);
            //defines text view:
            TextView mileage = (TextView) carsListItem.findViewById(R.id.lblListMileage);
            TextView isAvailable = (TextView) carsListItem.findViewById(R.id.lblListIsAvailable);
            HostBrunchTextView = (TextView) carsListItem.findViewById(R.id.lblListHostBrunch); //HostBrunchTextView is globally defined
            //sets text:
            mileage.setText("Mileage: " + String.valueOf(car.getMileage()));
            if(car.isAvailable())
                isAvailable.setText("The car is available" );
            else
                isAvailable.setText("The car is not available" );
            //represents host brunch details by asyn task:
            HostBrunchNumber = car.getBranchNumber();
            try {
                new HostBrunchAsyncTask().execute();
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return carsListItem;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults results = new FilterResults();
                    // We implement here the filter logic
                    if (constraint == null || constraint.length() == 0) {
                        // No filter implemented we return all the list
                        results.values = filterList;
                        results.count = filterList.size();
                    } else {
                        // We perform filtering operation
                        List<Car> tempList = new ArrayList<Car>();
                        for (Car p : filterList) {
                            if ((p.getModelType()+"").toUpperCase().startsWith(constraint.toString().toUpperCase()))
                                tempList.add(p);
                        }

                        results.values = tempList;
                        results.count = tempList.size();

                    }
                    return results;
                    // return null;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    // Now we have to inform the adapter about the new list filtered
                    if (results.count == 0) {
                        Cars = new ArrayList<>();
                        notifyDataSetInvalidated();
                    } else {
                        Cars = (List<Car>) results.values;
                        notifyDataSetChanged();
                    }
                }
            };
        }

        @Override
        public void onClick(View v) {
            }
        }
*/
}


