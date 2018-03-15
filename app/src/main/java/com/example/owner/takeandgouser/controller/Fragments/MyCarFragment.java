package com.example.owner.takeandgouser.controller.Fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.controller.MyCarDetailsView;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.Order;

import java.util.List;


public class MyCarFragment extends Fragment implements View.OnClickListener {

    static int orderNumber = -1;
    View view;
    Car car;
    Order order;
    String userID;

    TextView noOrderTextView;
    Button closeOrder;
    Button viewCarDetailsButton;

    TextView pleaseWaitTextView;
    TextView carNumberText;
    Button submitCloseButton;
    EditText closeOrderKilometers;
    EditText closeOrderGasPay;
    CheckBox closeOrderAddedGas;
    CheckBox closeOrderNoAddedGas;
    LinearLayout closeOrderGasLayout;
    LinearLayout closeOrderLayout;
    LinearLayout openOrderLayout;

    public MyCarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_car, container, false);

        noOrderTextView = (TextView) view.findViewById(R.id.noOrderTextView);
        closeOrder = (Button) view.findViewById(R.id.closeOrder);
        closeOrder.setOnClickListener(this);
        viewCarDetailsButton = (Button) view.findViewById(R.id.viewCarDetailsButton);
        viewCarDetailsButton.setOnClickListener(this);
        openOrderLayout = (LinearLayout) view.findViewById(R.id.openOrderLayout);
        submitCloseButton = (Button) view.findViewById(R.id.submitCloseButton);
        closeOrderKilometers = (EditText) view.findViewById(R.id.closeOrderKilometers);
        closeOrderGasPay = (EditText) view.findViewById(R.id.closeOrderGasPay);
        closeOrderAddedGas = (CheckBox) view.findViewById(R.id.closeOrderAddedGas);
        closeOrderNoAddedGas = (CheckBox) view.findViewById(R.id.closeOrderNoAddedGas);
        closeOrderGasLayout = (LinearLayout) view.findViewById(R.id.closeOrderGasLayout);
        closeOrderLayout = (LinearLayout) view.findViewById(R.id.closeOrderLayout);
        carNumberText = (TextView) view.findViewById(R.id.carNumberText);
        pleaseWaitTextView = (TextView) view.findViewById(R.id.pleaseWaitTextView);

        closeOrderLayout.setVisibility(View.GONE);
        closeOrderGasLayout.setVisibility(View.GONE);
        pleaseWaitTextView.setVisibility(View.GONE);
        submitCloseButton.setOnClickListener(this);//The submit button

        //ceckbox listeners to know if to open or close the added gas layout
        closeOrderNoAddedGas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    closeOrderGasLayout.setVisibility(View.GONE);
                }
            }
        });
        closeOrderAddedGas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    closeOrderGasLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userID = sharedPreferences.getString("ID", null); //Gets the user Id.
        getOrder();

        return view;
    }

    /**
     * The current order according to the client's Id.
     */

    public void getOrder() {
        try {
            // android.os.Debug.waitForDebugger();
            new AsyncTask<Void, Void, List<Order>>() {
                @Override

                protected void onPostExecute(List<Order> orders) {
                    if (orders != null) {

                        for (Order order_1 : orders) {
                            if (order_1.getClientId().equals(userID) && order_1.isOpen() == true) {
                                orderNumber = order_1.getOrderNumber();
                                order = order_1;
                            }

                        }
                    }
                    else {
                        closeOrder.setVisibility(View.GONE);;
                        viewCarDetailsButton.setVisibility(View.GONE);
                        carNumberText.setVisibility(View.GONE);
                        openOrderLayout.setVisibility(View.GONE);
                    }
                    if(order != null) {
                        pleaseWaitTextView.setVisibility(View.VISIBLE);
                        getCar(order.getCarNumber());
                    }
                    else {
                        closeOrder.setVisibility(View.GONE);;
                        viewCarDetailsButton.setVisibility(View.GONE);
                        carNumberText.setVisibility(View.GONE);
                        openOrderLayout.setVisibility(View.GONE);
                    }

                }

                @Override
                protected List<Order> doInBackground(Void... params) {
                    return DBManagerFactory.getManager().getOrders();
                }
            }.execute();
        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * set the known info about the client order to the view
     * if there is an opened order updates the view according to order details
     * else tells the user there is no open order
     */
    public void setViewByOrder() {

        if (orderNumber == -1) //there is no open order for client
        {
            closeOrder.setVisibility(View.GONE);;
            viewCarDetailsButton.setVisibility(View.GONE);
            carNumberText.setVisibility(View.GONE);
            openOrderLayout.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "no car", Toast.LENGTH_LONG).show();
        } else {
            noOrderTextView.setVisibility(View.GONE);
            openOrderLayout.setVisibility(View.VISIBLE);
            carNumberText.setText("" + car.getNumber());
            pleaseWaitTextView.setVisibility(View.GONE);
        }
    }

    /**
     * Gets the client's car by its order number.
     * @param carNumber
     */

    public void getCar(final long carNumber) {
        try {
            // android.os.Debug.waitForDebugger();
            new AsyncTask<Void, Long, Car>() {
                @Override
                protected Car doInBackground(Void... params) {
                    return DBManagerFactory.getManager().getCar(carNumber);
                }

                @Override

                protected void onPostExecute(Car car_1) {

                    car = car_1;

                    setViewByOrder();
                }

            }.execute();

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == closeOrder)
            closeOrderStep1();
        else if (v == submitCloseButton)
            closeOrderStep2();
        else if (v == viewCarDetailsButton)
            showCarDetails();
    }

    /**
     * the first step for closing a order is to open the close order layout
     */
    private void closeOrderStep1() {
        closeOrderLayout.setVisibility(View.VISIBLE);
    }

    /**
     * collectד the data the client filled and validates it, if all good, closes the order
     * and lets the client know what is the final payment
     */
    private void closeOrderStep2() {
        try {
            validateInput();
            final double pay; // how much the client payed for the gas
            if (closeOrderNoAddedGas.isChecked()) //client didn't add gas
                pay = 0;
            else
                pay = Double.valueOf(closeOrderGasPay.getText().toString());
            final double kilometers = Double.valueOf(closeOrderKilometers.getText().toString());
            new AsyncTask<Void, Double, Double>() {

                @Override
                protected void onPreExecute(){
                    pleaseWaitTextView.setVisibility(View.VISIBLE);
                }
                @Override
                protected void onPostExecute(Double aDouble) {

                    if (aDouble >= 0) { // if closing successful let the client know the final payment
                        payDialog(aDouble);
                        getActivity().onBackPressed();

                    }
                }

                @Override
                protected Double doInBackground(Void... params) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pleaseWaitTextView.setVisibility(View.VISIBLE);
                            }
                        });

                        //returns the final payment
                        return (DBManagerFactory.getManager().closeOrder(order.getOrderNumber(), kilometers, pay));
                    } catch (final Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        return -1.0; //closing failed
                    }
                }
            }.execute();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * validates the info the client provided for closing order
     * @return true if all the info is validated and filled correctly, else false
     * @throws Exception if there is a problem with the info provided
     */
    private boolean validateInput() throws Exception {
        String message="";
        if (!(closeOrderAddedGas.isChecked()) && !(closeOrderNoAddedGas.isChecked()))
            message = "please check one option";
        else if (closeOrderAddedGas.isChecked() && closeOrderNoAddedGas.isChecked())
            message = "please check only one option";
        else if(closeOrderKilometers.getText().toString().isEmpty())
            message = "please fill the kilometers you drove";
        else if (Integer.valueOf(closeOrderKilometers.getText().toString()) <= 0)
            message = "please enter validate kilometers number";
        else if(closeOrderAddedGas.isChecked() && closeOrderGasPay.getText().toString().isEmpty())
            message = "please fill the pay for the gas";
        else if (closeOrderAddedGas.isChecked() && Double.parseDouble(closeOrderGasPay.getText().toString()) <= 0)
            message = "please enter validate pay";
        if(message.isEmpty())
            return true;
        else
            throw new Exception(message);
    }
       /*
    activates a dialog to show payment.
     */

    private void payDialog(double payment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Order No." + order.getOrderNumber()+" is closed");
        String pay;
        if (payment <= 0)
            pay = "0.0";
        else
            pay = String.format("%.2f",payment);
        builder.setMessage("your final payment is "+pay+" dollars");
        builder.setNeutralButton("got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.create().show();
    }

    /*
    activates a dialog to show full specific car details, including branch and model
     */
    private void showCarDetails() {
        View view = MyCarDetailsView.getView(getContext(),car);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle("full car details");
        builder.setNeutralButton("got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }
    }




