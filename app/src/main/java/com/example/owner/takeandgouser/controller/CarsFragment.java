package com.example.owner.takeandgouser.controller;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Car;

import java.util.ArrayList;
import java.util.List;

public class CarsFragment extends Fragment {

    private ExpandableListView carsExpandableList;
    private SearchView searchView;
    final MyExpandableListAdapter myBaseExpandableListAdapter = new MyExpandableListAdapter();

    private static List<Car> filterList = new ArrayList<>();
    private static List<Car> Cars = new ArrayList<>();

    public CarsFragment() {// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);
        carsExpandableList = ((ExpandableListView) rootView.findViewById(R.id.carsExpandableList));
        searchView = (SearchView) rootView.findViewById(R.id.carsSearchView);
        try {
            new MyAsyncTask().execute();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return rootView;
    }


    private class MyAsyncTask extends AsyncTask<Car, Void, List<Car>> {

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
            //TODO add xml car list item + implimentation of this function
           View branchesListItem = getActivity().getLayoutInflater().inflate(R.layout.branch_list_item, parent, false); //TODO change to car list item!!
            /*Branch branch = Branches.get(groupPosition);
            TextView parking = (TextView) branchesListItem.findViewById(R.id.lblListParking);
            TextView branchNumber = (TextView) branchesListItem.findViewById(R.id.lblListBranchNumber);
            parking.setText("parking: " + String.valueOf(branch.getParking()));
            branchNumber.setText("branch number: " + String.valueOf(branch.getBranchNumber()));
            carsListByBranch = (ListView) branchesListItem.findViewById(R.id.carsListView);
            try {
                new carByBranchAsyncTask().execute(branch, null);
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }*/
            return branchesListItem;
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

}


