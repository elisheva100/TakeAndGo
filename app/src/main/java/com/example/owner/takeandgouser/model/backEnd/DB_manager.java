package com.example.owner.takeandgouser.model.backEnd;

import android.content.ContentValues;

import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.Client;
import com.example.owner.takeandgouser.model.entities.Order;

import java.util.List;

/**
 *
 */

public interface DB_manager {
    //region client
    String addClient(ContentValues client) throws Exception;
    boolean isExistClient(String string)throws Exception;
    List<Client> getClients();
    //end region


    //region Car
    long updateCar(ContentValues car) throws Exception;
    /*boolean removeCar(long num) throws Exception;
    boolean updateCar(long num, ContentValues values) throws Exception;*/
    //boolean isExistCar(long n);
    List<Car> getAvailableCars();
    List<Car> getAvailableCarsForBranch(Branch b);
    Car getCar(long num);
    //endregion
    //region branches
    List<Branch> getBranches();
    //end region
    //region orders
    int addOrder(ContentValues order)throws Exception;
    List<Order> getOrders();
    Double closeOrder(int num, double kilometers, double gasFilled)throws Exception;
    Order getOrder (int num);
   boolean checkOrder();

}
