package com.example.owner.takeandgouser.controller;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.AgencyConsts;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Adress;
import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import  com.example.owner.takeandgouser.controller.BranchesFragment.MyExpandableListAdapter.setListViewHeightBasedOnChildren;


public class BranchesFragment extends Fragment {

    private boolean flag = false;
    private static int orderNumber = 100000;
    private String userID;
    private String date;
    private Car selectedCar;

    private ExpandableListView branchesExpandableList;
    private ListView carsListByBranch;
    private SearchView searchView;
    final MyExpandableListAdapter myBaseExpandableListAdapter = new MyExpandableListAdapter();
    ArrayAdapter<Long> carsAdaptor;
    private int lastExpandedPosition = -1;

    private static List<Branch> filterList = new ArrayList<>();
    private static List<Branch> Branches = new ArrayList<>();
    private static List<Car> carsByBranch = new ArrayList<>();

    private void openMap(Adress address) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri location = Uri.parse("geo:0,0?q=" + address.toString());
        intent.setData(location);
        startActivity(intent);
    }


    public BranchesFragment() { // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branches, container, false);
        branchesExpandableList = ((ExpandableListView) rootView.findViewById(R.id.branchesExpandableList));
        searchView = (SearchView) rootView.findViewById(R.id.mySearchView);

        try { new MyAsyncTask().execute();}
        catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private class MyAsyncTask extends AsyncTask<Branch, Void, List<Branch>> {

        @Override
        protected void onPostExecute(List<Branch> listFromBackground) {
            Branches = null;
            Branches = listFromBackground;
            filterList.clear();
            filterList.addAll(Branches);
            branchesExpandableList.setAdapter(myBaseExpandableListAdapter);
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
        protected List<Branch> doInBackground(Branch... params) {
            try {
                return DBManagerFactory.getManager().getBranches();
            } catch (Exception e) {
                return  null;
            }
        }
    }


    private class carByBranchAsyncTask extends AsyncTask<Branch, Void, List<Car>>
    {
        @Override
        protected void onPostExecute(List<Car> listFromBackground) {
            carsByBranch = null;
            carsByBranch = listFromBackground;
            if(carsByBranch != null) {
                Long[] nums = getAllCarsNumbers(carsByBranch);
                carsAdaptor = new ArrayAdapter<Long>(getActivity(), android.R.layout.simple_list_item_1, nums);
                carsListByBranch.setAdapter(carsAdaptor);
                setListViewHeightBasedOnChildren(carsListByBranch);
            }


        }

        @Override
        protected List<Car> doInBackground(Branch... params) {
            try {
                return DBManagerFactory.getManager().getAvailableCarsForBranch(params[0]);
            } catch (Exception e) {
                return null;
            }
        }
    }

    Long[] getAllCarsNumbers(List<Car> cars)
    {
        Long[] num = new Long[]{};
        List<Long> lst = new ArrayList<>();
            for (Car car : cars)
                lst.add(car.getNumber());
            return lst.toArray(num);

    }



    class MyExpandableListAdapter extends BaseExpandableListAdapter implements Filterable, View.OnClickListener {

        @Override
        public int getGroupCount() {
            if(Branches == null)
                return 0;
            return Branches.size();
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
            View header = getActivity().getLayoutInflater().inflate(R.layout.branch_list_group, parent, false);
            TextView title = (TextView) header.findViewById(R.id.lblBranchesListHeader);
            title.setText("Address: " + Branches.get(groupPosition).getAdress().toString());
            ImageButton mapView = (ImageButton) header.findViewById(R.id.buttonMap);
            mapView.setTag(Branches.get(groupPosition).getAdress());
            mapView.setFocusable(false);
            mapView.setOnClickListener(this);


            return header;

        }

        public void onGroupExpand(int groupPosition) {
            int lenght = myBaseExpandableListAdapter.getGroupCount();
            for (int i = 0; i < lenght; i++) {
                if (i != groupPosition) {
                    branchesExpandableList.collapseGroup(i);
                }
            }
        }



        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View branchesListItem = getActivity().getLayoutInflater().inflate(R.layout.branch_list_item, parent, false);
            Branch branch = Branches.get(groupPosition);
            TextView parking = (TextView) branchesListItem.findViewById(R.id.lblListParking);
            TextView branchNumber = (TextView) branchesListItem.findViewById(R.id.lblListBranchNumber);
            parking.setText("parking: " + String.valueOf(branch.getParking()));
            branchNumber.setText("branch number: " + String.valueOf(branch.getBranchNumber()));
            //onGroupExpand(groupPosition);


            carsListByBranch = (ListView) branchesListItem.findViewById(R.id.carsListView);
            try {
                new carByBranchAsyncTask().execute(branch, null);
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            carsListByBranch.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                    AlertDialog.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int option) {
                            switch (option) {
                                case Dialog.BUTTON_POSITIVE:
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    userID = sharedPreferences.getString("ID", null);
                                    selectedCar = carsByBranch.get(i);
                                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                    getAllOpenOrders();
                                    break;
                                case Dialog.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setTitle("add order");
                    alertDialogBuilder.setIcon(R.mipmap.ic_info);
                    alertDialogBuilder.setMessage("Are you sure you want to rent this car?");
                    alertDialogBuilder.setPositiveButton("Yes", onClickListener);
                    alertDialogBuilder.setNegativeButton("No", onClickListener);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                }

            });

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
                        List<Branch> tempList = new ArrayList<Branch>();
                        for (Branch p : filterList) {
                            ;
                            //if (p.getAdress().toString().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                            if (p.getAdress().getCity().toString().toUpperCase().startsWith(constraint.toString().toUpperCase()))
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
                        Branches = new ArrayList<>();
                        notifyDataSetInvalidated();
                    } else {
                        Branches = (List<Branch>) results.values;
                        notifyDataSetChanged();
                    }
                }
            };
        }


        @Override
        public void onClick(View v) {
            if (v.getTag().getClass().equals(Adress.class)) {
                Adress address = (Adress) v.getTag();
                openMap(address);
            }
        }
    }


    private void getAllOpenOrders() {
        try {
            new AsyncTask<Void, Void, List<Order>>() {
            @Override
            protected void onPostExecute(List<Order> orders) {
                if (orders == null) {
                    orderNumber = 100000;
                    flag = true;
                }

                else {
                    for (Order order : orders) {
                        if (order.getClientId().equals(userID) && order.isOpen() == true) {
                            Toast.makeText(getActivity(), "You already has an open order, please close your priviouse order!", Toast.LENGTH_LONG).show();
                            //getActivity().getFragmentManager().popBackStack();
                            flag = false;
                            getActivity().onBackPressed();
                            return;
                        } else if (order.getOrderNumber() > orderNumber) {
                            orderNumber = order.getOrderNumber();
                            flag = true;
                        }
                    }
                }
                    if (flag) {
                        orderNumber += 1;
                        createOrder(orderNumber, userID, selectedCar.getNumber(), selectedCar.getMileage(), date);
                        carsByBranch.remove(selectedCar);
                        carsListByBranch.invalidateViews();
                        //carsAdaptor.remove(carsAdaptor.getItem(pos));
                        //notifyDataSetChanged();
                        //carsListByBranch.setAdapter(carsAdaptor);

                    }
                }

                @Override
                protected List<Order> doInBackground(Void... params) {
                    return DBManagerFactory.getManager().getOrders();
                }
            }.execute();
        }
        catch (Exception e)
        {
            Toast.makeText(this.getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    private void createOrder(int orderNumber, String userID, final long carNumber, double mileage, String date) {
        final ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(AgencyConsts.OrderConst.ORDER_NUMBER, String.valueOf(orderNumber));
            contentValues.put(AgencyConsts.OrderConst.CLIENT_NUMBER, userID);
            contentValues.put(AgencyConsts.OrderConst.CAR_NUMBER, carNumber);
            contentValues.put(AgencyConsts.OrderConst.MILEAGE_START, mileage);
            contentValues.put(AgencyConsts.OrderConst.RENT_START, date);

            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    try {
                        int numOrder = DBManagerFactory.getManager().addOrder(contentValues);
                        return String.valueOf(numOrder);

                    }
                    catch (Exception e)
                    {
                        //Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG);
                        return null ;
                    }
                    //DBManagerFactory.getManager().updateCar(CarNumber);

                }

                @Override
                protected void onPostExecute(String numOrder) {
                    Toast.makeText(getActivity(), "Order number: " + numOrder + " added successfully", Toast.LENGTH_LONG).show();
                }
            }.execute();
        }
        catch (Exception e) {

        }
    }




    public static void setListViewHeightBasedOnChildren(ListView carsListByBranch) {
        ListAdapter listAdapter = carsListByBranch.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;

        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, carsListByBranch);
            listItem.measure(0, 80);
            totalHeight += 80;
        }

        ViewGroup.LayoutParams params = carsListByBranch.getLayoutParams();
        params.height = totalHeight - listAdapter.getCount();// + (carsListByBranch.getDividerHeight() * (listAdapter.getCount() - 1));
        carsListByBranch.setLayoutParams(params);
        carsListByBranch.requestLayout();
    }
}






