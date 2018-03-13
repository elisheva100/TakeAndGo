package com.example.owner.takeandgouser.model.datasource;

import android.content.ContentValues;
import android.util.Log;
import com.example.owner.takeandgouser.model.backEnd.AgencyConsts;

import com.example.owner.takeandgouser.model.backEnd.AgencyConsts;
import com.example.owner.takeandgouser.model.backEnd.DB_manager;
import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.CarModel;
import com.example.owner.takeandgouser.model.entities.Client;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO continue that process : creare sql for table
public class MySQL_DBManager implements DB_manager {

    private final String UserName="taicohen";
    private final String WEB_URL = "http://"+UserName+".vlab.jct.ac.il/php_files/";


    private boolean updateFlag = false;
    private void SetUpdate() { updateFlag = true; }

    public void printLog(String message) { Log.d(this.getClass().getName(),"\n"+message); }

    public void printLog(Exception message) { Log.d(this.getClass().getName(),"Exception-->\n"+message); }

    //region client
    @Override
    public String addClient(ContentValues client) throws Exception {
        Client c =AgencyConsts.ContentValuesToClient(client);
        if (isExistClient(c.getId()))
            throw new Exception("This client is already exists!!");
        try {
            String result = PHPtools.POST(WEB_URL + "/add_client.php", client);
            result = result.trim();
            if (result != null)
                SetUpdate();
            printLog("addClient:\n" + result);
            return result;
        } catch (IOException e) {
            printLog("addClient Exception:\n" + e);
            return null;
        }
    }



    @Override
    //checks if there is a client with that id
    public boolean isExistClient(String i)
    {
        for (Client item : this.getClients()) {
            if (item.getId().equals(i) == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Client> getClients() {
        List<Client> result = new ArrayList<Client>();
        try {
            String str = PHPtools.GET(WEB_URL + "/get_client.php");
            JSONArray array = new JSONObject(str).getJSONArray("clients");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Client client = AgencyConsts.ContentValuesToClient(contentValues);
                result.add(client);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion

    //region cars
    @Override
    public long updateCar(ContentValues car) throws Exception
    {
        try {
            String result = PHPtools.POST(WEB_URL + "/update_car.php", car);
            result = result.trim();
            long numResult = Long.parseLong(result);
            if (result != null)
                SetUpdate();
            printLog("updateCar:\n" + result);
            return numResult;
        } catch (IOException e) {
            printLog("updateCar Exception:\n" + e);
            return -1;
        }
    }
    @Override

    public List<Car> getAvailableCars() {
        List<Car> result = new ArrayList<Car>();

        try {
            String str = PHPtools.GET(WEB_URL + "/get_available_cars.php").trim();
            JSONArray array = new JSONObject(str).getJSONArray("Available cars");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Car car = AgencyConsts.ContentValuesToCar(contentValues);
                result.add(car);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//TODO: check the php file' right now it's not working.
    public List<Car> getAvailableCarsForBranch(Branch b){
        List<Car> result = new ArrayList<Car>();
        List<Car> itemsToRemove = new ArrayList<Car>();
        

        try {
            String str = PHPtools.GET(WEB_URL + "/get_available_cars.php").trim();
            JSONArray array = new JSONObject(str).getJSONArray("Available cars");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Car car = AgencyConsts.ContentValuesToCar(contentValues);
                result.add(car);

            }
            for (Car car : result){
                if(car.getBranchNumber()!= b.getBranchNumber() )
                    itemsToRemove.add(car);
            }
            result.removeAll(itemsToRemove);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    //endregion

    //region branch


    @Override
    public List<Branch> getBranches() {
        List<Branch> result = new ArrayList<Branch>();
        try {
            String str = PHPtools.GET(WEB_URL + "/get_Branches.php");
            JSONArray array = new JSONObject(str).getJSONArray("branches");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Branch branch = AgencyConsts.ContentValuesToBranch(contentValues);
                result.add(branch);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion

    //region order
    @Override
    public int addOrder(ContentValues order)throws Exception
    {
        try {
            String result = PHPtools.POST(WEB_URL + "/add_order.php", order);
            result = result.trim();
            int numResult = Integer.parseInt(result);
            if (result != null)
                SetUpdate();
            printLog("addOrder:\n" + result);
            return numResult;
        } catch (IOException e) {
            printLog("addOrder Exception:\n" + e);
            return -1;
        }
    }

    @Override
public int closeOrder(ContentValues order)throws Exception{
    try {
        String result = PHPtools.POST(WEB_URL + "/close_order.php", order);
        result = result.trim();
        int numResult = Integer.parseInt(result);
        if (result != null)
            SetUpdate();
        printLog("closeOrder:\n" + result);
        return numResult;
    } catch (IOException e) {
        printLog("closeOrder Exception:\n" + e);
        return -1;
    }

}
@Override
    public List<Order> getOpenOrders()
    {
        List<Order> result = new ArrayList<Order>();
        try {
            String str = PHPtools.GET(WEB_URL + "/get_open_orders.php");
            JSONArray array = new JSONObject(str).getJSONArray("Open orders");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Order order = AgencyConsts.ContentValuesToOrder(contentValues);
                result.add(order);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    //end region
}

