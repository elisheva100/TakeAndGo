package com.example.owner.takeandgouser.model.datasource;

import android.content.ContentValues;

import com.example.owner.takeandgouser.model.backEnd.DB_manager;
import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.CarModel;
import com.example.owner.takeandgouser.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

import static com.example.owner.takeandgouser.model.backEnd.AgencyConsts.ContentValuesToBranch;
import static com.example.owner.takeandgouser.model.backEnd.AgencyConsts.ContentValuesToCar;
import static com.example.owner.takeandgouser.model.backEnd.AgencyConsts.ContentValuesToCarModel;
import static com.example.owner.takeandgouser.model.backEnd.AgencyConsts.ContentValuesToClient;


public class List_DBManager implements DB_manager {

    //static lists
    public static List<Car> cars;
    public static List<Branch> branches;
    public static List<CarModel> carModels;
    public static List<Client> clients;

    //static inner class
    static {
        cars = new ArrayList<>();
        branches = new ArrayList<>();
        carModels = new ArrayList<>();
        clients = new ArrayList<>();
    }

    //region car
    @Override
    //add car to cars' list
    public long addCar(ContentValues car) throws Exception {
        Car item = ContentValuesToCar(car);
        if (isExistCar(item.getNumber()))
            throw new Exception ("This car is already exists!!");
        try{ cars.add(item);}
        catch (Exception e){ throw new Exception(e.getMessage());}
        return item.getNumber();
    }

    /*@Override
    //removes car from cars' list
    public boolean removeCar(long num) throws Exception {
        if(!isExistCar(num)) //checks if that car exists in the data
            throw new Exception("This car number isn't exist");
        Car carToRemove = null;
        for (Car item : cars) //search for item with the same number
            if (item.getNumber() == num) {
                carToRemove = item;
                break;
            }
        return cars.remove(carToRemove);
    }

    @Override
    //updates item in cars' list
    public boolean updateCar(long num, ContentValues values) throws Exception {
        if(!isExistCar(num)) //checks if that car exists in the data
            throw new Exception("This car number isn't exist");
        Car car = ContentValuesToCar(values); //sets car details
        car.setNumber(num);
        for (int i = 0; i < cars.size(); i++)
            if (cars.get(i).getNumber() == num) {
                cars.set(i,car);
                return true; //Return true if the update succeeded
            }
        return false; //Returns false if the update failed
    }*/

    @Override
    //checks if there is a car with the same number
    public boolean isExistCar(long n) {
        for (Car item : cars)
            if(item.getNumber()==n)
                return true;
        return false;
    }

    @Override
    //returns cars' list
    public List<Car> getCars() {return cars;}
    //endregion

    //region client
    @Override
    //adds client to clients' list
    public String addClient(ContentValues client) throws Exception {
        Client item = ContentValuesToClient(client);
        if (isExistClient(item.getId()))
            throw new Exception("This client is already exists!!");
        try{clients.add(item);}
        catch (Exception e){ throw new Exception(e.getMessage());}
        return item.getId();
    }

   /* @Override
    //removes client from the list
    public boolean removeClient(String id) throws Exception {
        if (!isExistClient(id)) //checks if there is a client with that id
            throw new Exception("This client id isn't exist");
        Client clientToRemove = null;
        for (Client item : clients) //search for item with the same number
            if (item.getId() == id) {
                clientToRemove = item;
                break;
            }
        return clients.remove(clientToRemove);
    }

    @Override
    //updates item in clients' list
    public boolean updateClient(String id, ContentValues values) throws Exception {
        if (!isExistClient(id)) //checks if there is a client with that id
            throw new Exception("This client id isn't exist");
        Client client = ContentValuesToClient(values); //sets client details
        client.setId(id);
        for (int i = 0; i < clients.size(); i++)
            if (clients.get(i).getId() == id) {
                clients.set(i,client);
                return true; //Return true if the update succeeded
            }
        return false; //Returns false if the update failed
    }*/

    @Override
    //checks if there is a client with that id
    public boolean isExistClient(String i)
    {
        for (Client item : clients)
            if(item.getId()==i)
                return true;
        return false;
    }

    @Override
    //returns clients' list
    public List<Client> getClients() {
        return clients;
    }
    //endregion

    //region branch
    @Override
    //adds branch to branches' list
    public int addBranch(ContentValues branch) throws Exception {
        Branch item = ContentValuesToBranch(branch);
        if (isExistBranch(item.getBranchNumber()))
            throw new Exception("This branch is already exists!!");
        try {branches.add(item);}
        catch (Exception e){ throw new Exception(e.getMessage());}
        return item.getBranchNumber();
    }

    /*@Override
    //removes branch from branches' list
    public boolean removeBranch(int num) throws Exception {
        if (! isExistBranch(num)) //checks if there is a branch with that number
            throw new Exception("This branch number isn't exist");
        Branch branchToRemove = null;
        for (Branch item : branches) //search for item with the same number
            if (item.getBranchNumber() == num) {
                branchToRemove = item;
                break;
            }
        return branches.remove(branchToRemove);
    }

    @Override
    //updates item in branches' list
    public boolean updateBranch(int num, ContentValues values) throws Exception {
        if (! isExistBranch(num)) //checks if there is a branch with that number
            throw new Exception("This branch number isn't exist");
        Branch branch = ContentValuesToBranch(values); //sets branch details
        branch.setBranchNumber(num);
        for (int i = 0; i < branches.size(); i++)
            if (branches.get(i).getBranchNumber() == num) {
                branches.set(i,branch);
                return true; //Return true if the update succeeded
            }
        return false; //Returns false if the update failed
    }*/

    @Override
    //checks if there is a client with that id
    public boolean isExistBranch(int n)
    {
        for (Branch item : branches)
            if(item.getBranchNumber()==n)
                return true;
        return false;
    }

    @Override
    //returns branches' list
    public List<Branch> getBranches() { return branches; }
    //endregion

    //region model
    @Override
    //adds model to car models' list
    public int addCarModel(ContentValues model) throws Exception {
        CarModel item = ContentValuesToCarModel(model);
        if (isExistModel(item.getCode()))
            throw new Exception("This model is already exists!!");
        try {carModels.add(item);}
        catch (Exception e){ throw new Exception(e.getMessage());}
        return item.getCode();
    }

    /*@Override
    //removes model from car models' list
    public boolean removeCarModel(int num) {
        CarModel carModelToRemove = null;
        for (CarModel item : carModels) //search for item with the same number
            if (item.getCode() == num) {
                carModelToRemove = item;
                break;
            }
        return cars.remove(carModelToRemove);
    }

    @Override
    //updates item in car models' list
    public boolean updateCarModel(int num, ContentValues values) {
        CarModel carModel = ContentValuesToCarModel(values); //sets car model details
        carModel.setCode(num);
        for (int i = 0; i < carModels.size(); i++)
            if (carModels.get(i).getCode() == num) {
                carModels.set(i,carModel);
                return true; //Return true if the update succeeded
            }
        return false; //Returns false if the update failed
    }*/

    @Override
    //checks if there is a model with that code
    public boolean isExistModel(int n)
    {
        for (CarModel item : carModels)
            if(item.getCode()==n)
                return true;
        return false;
    }

    @Override
    //returns car models' list
    public List<CarModel> getCarModels() {
        return carModels;
    }

    //endregion

}



