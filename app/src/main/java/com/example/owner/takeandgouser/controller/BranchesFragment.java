package com.example.owner.takeandgouser.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    //View view;

    public List<Branch> Branches = new ArrayList<Branch>() ;
    public BranchesFragment() { // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branches, container, false);
        //long pID = getArguments().getLong("product id");
        // final ArrayList<Branch> Branches = (ArrayList<Branch>) getArguments().getSerializable("ArrayList<Branch>");
        //((ListView) rootView.findViewById(R.id.shopListView)).setAdapter(new ArrayAdapter<Shop>(getActivity(), R.layout.shop_list_row, Shops));
       try { new MyAsyncTask().execute();}
       catch (Exception e) {}
        ((ExpandableListView) rootView.findViewById(R.id.branchesExpandableList)).setAdapter(new BaseExpandableListAdapter() {
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
                    //TextView address = (TextView) shopsListItem.findViewById(R.id.lblListAddress);
                    //TextView telephone = (TextView) shopsListItem.findViewById(R.id.lblLisTelephoneNumber);
                    //Button details_button = (Button) branchesListItem.findViewById(R.id.ShopDetailsButton);
                    parking.setText(branch.getParking());
                    branchNumber.setText(branch.getBranchNumber());
//               email.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //TODO fix the email.setOnClickListener
//                        Intent implicitIntent = new Intent(Intent.ACTION_SENDTO);
//                        implicitIntent.setData(Uri.parse("mailto:"));
//                        implicitIntent.setType("*/*");
//                        implicitIntent.putExtra(Intent.EXTRA_EMAIL, /*shop.getEmail()*/ "haimnafcha@gmail.com");
//                        implicitIntent.putExtra(Intent.EXTRA_SUBJECT, "From ShopApp");
//                        if (implicitIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                            startActivity(implicitIntent);
//                        }
//                    }
//                });
//                address.setText(shop.getShopAddress());
//                telephone.setText(shop.getTelephoneNumber());
//                telephone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(Intent.ACTION_DIAL);
//                        intent.setData(Uri.parse("tel:" + shop.getTelephoneNumber()));
//                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                            startActivity(intent);
//                        }
//                    }
//                });
//                details_button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent implicitIntent = new Intent(Intent.ACTION_VIEW);
//                        implicitIntent.setData(Uri.parse("geo:0,0?q=" + branch.getShopAddress().replace(" ","+").replace("\n","+")));
//                        if (implicitIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                            startActivity(implicitIntent);
//                        }
//                    }
//                });
                    return branchesListItem;
                }

                @Override
                public boolean isChildSelectable(int groupPosition, int childPosition) {
                    return true;
                }
            });
        return rootView;
    }


    private class MyAsyncTask extends AsyncTask<Branch, Void, Void> {

        @Override
        protected void onPostExecute(Void listFromBackground) {
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
}








/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BranchesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BranchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/*public class BranchesFragment extends Fragment {
   // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BranchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BranchesFragment.
     */
   /* // TODO: Rename and change types and number of parameters
    public static BranchesFragment newInstance(String param1, String param2) {
        BranchesFragment fragment = new BranchesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_branches, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}*/
