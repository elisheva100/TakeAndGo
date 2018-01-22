package com.example.owner.takeandgouser.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SearchViewCompat;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Branch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class BranchesFragment extends Fragment {

    private ExpandableListView branchesExpandableList;
    private SearchView searchView;
    final MyExpandableListAdapter myBaseExpandableListAdapter = new MyExpandableListAdapter();
    private static  List<Branch> Branches = new ArrayList<>() ;
    private static  List<Branch> filterList = new ArrayList<>() ;

    public BranchesFragment() { // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branches, container, false);
        branchesExpandableList =  ((ExpandableListView) rootView.findViewById(R.id.branchesExpandableList));
        searchView = (SearchView) rootView.findViewById(R.id.mySearchView);
        try{ new MyAsyncTask().execute();}
        catch(Exception e) { Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();}
        return rootView;
    }

    private class MyAsyncTask extends AsyncTask<Branch, Void, Void> {

        @Override
        protected void onPostExecute(Void listFromBackground) {
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
        protected Void doInBackground(Branch... params) {
            try {
                Branches = DBManagerFactory.getManager().getBranches();
            } catch (Exception e) {
                Branches = null;
            }
            return null;
        }
    }

    class MyExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {
        @Override
        public int getGroupCount() {
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
            title.setText(Branches.get(groupPosition).getAdress().toString());
            return header;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View branchesListItem = getActivity().getLayoutInflater().inflate(R.layout.branch_list_item, parent, false);
            final Branch branch = Branches.get(groupPosition);
            TextView parking = (TextView) branchesListItem.findViewById(R.id.lblListParking);
            TextView branchNumber = (TextView) branchesListItem.findViewById(R.id.lblListBranchNumber);
            parking.setText(String.valueOf(branch.getParking()));
            branchNumber.setText(String.valueOf(branch.getBranchNumber()));
            return branchesListItem;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


        @Override
        public Filter getFilter() {
            return  new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                   FilterResults results = new FilterResults();
                    // We implement here the filter logic
                   if (constraint == null || constraint.length() == 0) {
                        // No filter implemented we return all the list
                        results.values = filterList;
                        results.count = filterList.size();
                   }
                    else {
                        // We perform filtering operation
                        List<Branch> tempList = new ArrayList<Branch>();
                       for (Branch p : filterList) {;
                           if (p.getAdress().toString().toUpperCase().startsWith(constraint.toString().toUpperCase()))
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
                    }
                    else {
                        Branches = (List<Branch>) results.values;
                        notifyDataSetChanged();
                    }
                }
            };
        }
    }
}

