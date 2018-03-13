package com.example.owner.takeandgouser.model.datasource;

import android.content.ContentValues;
import android.util.Log;

import com.example.owner.takeandgouser.model.backEnd.AgencyConsts;
import com.example.owner.takeandgouser.model.backEnd.DB_manager;
import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.Client;
import com.example.owner.takeandgouser.model.entities.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public Car getCar(long num) {
        List<Car> result = new ArrayList<Car>();

        try {
            String str = PHPtools.GET(WEB_URL + "/get_cars.php");
            JSONArray array = new JSONObject(str).getJSONArray("cars");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Car car = AgencyConsts.ContentValuesToCar(contentValues);
                result.add(car);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Car car : result){
            if (car.getNumber() == num)
                return car;
        }
        return null;
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
/*public int closeOrder(ContentValues order)throws Exception{
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

}*/
public Order getOrder(int num)
{
    List<Order> orders = getOrders();
    for(Order order : orders){
        if(order.getOrderNumber() == num)
            return order;
    }
    return null;

}
@Override
    public double closeOrder (int number , double kilometers , double gasFilled) throws Exception {

        Order order = getOrder(number);
        order.setRentEnd(Calendar.getInstance().getTime());
    
        //order.setRentEnd(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        order.setMileageEnd(order.getMileageStart() + kilometers);
        if(gasFilled == 0)
            order.setGasFilled(false);
        else
            order.setGasFilled(true);
        order.setGasLiters(gasFilled);
        double finalPayment = calculatePayment(order);
        if(finalPayment < 0)
            finalPayment = 0; //for case the client filled to much gas.
        order.setFinalBilling(finalPayment);
        ContentValues orderContentValue = AgencyConsts.OrderToContentValues(order);
        orderContentValue.put(AgencyConsts.OrderConst.RENT_END, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

        //update car kilometers
        Car car = getCar(order.getCarNumber());
        car.setMileage(order.getMileageEnd());
        ContentValues carContentValue = AgencyConsts.CarToContentValues(car);

        try {
            String result = PHPtools.POST(WEB_URL + "/close_order.php", orderContentValue);
            result = result.trim();
            int numResult = Integer.parseInt(result);
            if (result != null)
                SetUpdate();
            printLog("closeOrder:\n" + result);
            //return numResult;
        } catch (IOException e) {
            printLog("closeOrder Exception:\n" + e);
            return -1;
        }
        updateCar(carContentValue);

        return finalPayment;
    }

    private double calculatePayment(Order order) {
        double finalPayment = 0 ;
        long daysOfRent = daysBetween(order.getRentStart(),order.getRentEnd());
        double hoursOfRent = hoursOfRent(order,daysOfRent);
        finalPayment = (hoursOfRent + daysOfRent * 24.0) * 15 ; //15 dollars for hour rent
        return finalPayment;
    }

    @Override
    public List<Order> getOrders()
    {
        List<Order> result = new ArrayList<Order>();
        try {
            String str = PHPtools.GET(WEB_URL + "/get_open_orders.php").trim();
            if(str.equals("0 results"))
            {
                return null;
            }
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
    private long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1); // add another day
            daysBetween++;                       // singles another day difference
        }
        return daysBetween;
    }
    private Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second
        return cal;
    }
    private double hoursOfRent(Order order,long days) {
        Calendar cal = Calendar.getInstance();
        double hours;
        if(days > 0) {
            //calculate hours of day of end rent
            cal.setTime(order.getRentEnd());
            hours = cal.get(Calendar.HOUR_OF_DAY);
            hours += cal.get(Calendar.MINUTE) / 60.0;
            //calculate hours of day of start rent
            cal.setTime(order.getRentStart());
            hours += (24 - cal.get(Calendar.HOUR_OF_DAY));
            hours += ((60 - cal.get(Calendar.MINUTE)) / 60.0);
        }
        else{ //its the same day
            Calendar cal1 = Calendar.getInstance();
            cal.setTime(order.getRentEnd());
            cal1.setTime(order.getRentStart());
            hours = cal.get(Calendar.HOUR_OF_DAY) - cal1.get(Calendar.HOUR_OF_DAY);
            hours += cal.get(Calendar.MINUTE) / 60.0;
            hours -= cal1.get(Calendar.MINUTE) / 60.0;
            if(hours < 0)
                hours = 0;
        }
        return hours;
    }
}

