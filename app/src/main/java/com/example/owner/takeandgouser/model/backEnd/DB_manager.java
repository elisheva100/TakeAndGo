package com.example.owner.takeandgouser.model.backEnd;

import android.content.ContentValues;

import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.CarModel;
import com.example.owner.takeandgouser.model.entities.Client;

import java.util.List;

/**
 *
 */

public interface DB_manager {
    //region Car
    long addCar(ContentValues car) throws Exception;
    /*boolean removeCar(long num) throws Exception;
    boolean updateCar(long num, ContentValues values) throws Exception;*/
    boolean isExistCar(long n);
    List<Car> getCars();
    //endregion

    //region client
    String addClient(ContentValues client) throws Exception;
    //boolean removeClient(String id) throws Exception;
    //boolean updateClient(String id, ContentValues values) throws Exception;
    boolean isExistClient(String i);
    List<Client> getClients();
    //endregion

    //region branch
    int addBranch(ContentValues branch) throws Exception;
    /*boolean removeBranch(int num) throws Exception;
    boolean updateBranch(int num, ContentValues values) throws Exception;*/
    boolean isExistBranch(int n);
    List<Branch> getBranches();
    //endregion

    //region model
    int addCarModel(ContentValues model) throws Exception;
    /*boolean removeCarModel(int num);
    boolean updateCarModel(int num, ContentValues values);*/
    boolean isExistModel(int n);
    List<CarModel> getCarModels();
    //endregion

}
