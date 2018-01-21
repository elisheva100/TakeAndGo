package com.example.owner.takeandgouser.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Branch;

import java.util.ArrayList;
import java.util.List;


public class BranchesFragment extends Fragment {

    private ExpandableListView branchesExpandableList;
    final MyExpandableListAdapter myBaseExpandableListAdapter = new MyExpandableListAdapter();
    private static  List<Branch> Branches = new ArrayList<>() ;

    public BranchesFragment() { // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branches, container, false);
        branchesExpandableList =  ((ExpandableListView) rootView.findViewById(R.id.branchesExpandableList));
        try{ new MyAsyncTask().execute();}
        catch(Exception e) {/*Toast.makeText( getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();*/}
        return rootView;
    }

    private class MyAsyncTask extends AsyncTask<Branch, Void, Void> {

        @Override
        protected void onPostExecute(Void listFromBackground) {
            branchesExpandableList.setAdapter(myBaseExpandableListAdapter);
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

    class MyExpandableListAdapter extends BaseExpandableListAdapter{
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

    }
}

