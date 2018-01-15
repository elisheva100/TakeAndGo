package com.example.owner.takeandgouser.model.backEnd;

import android.content.ContentValues;

import com.example.owner.takeandgouser.model.entities.Adress;
import com.example.owner.takeandgouser.model.entities.Branch;
import com.example.owner.takeandgouser.model.entities.Car;
import com.example.owner.takeandgouser.model.entities.CarModel;
import com.example.owner.takeandgouser.model.entities.Client;
import com.example.owner.takeandgouser.model.entities.GEARBOX;
import com.example.owner.takeandgouser.model.entities.Order;
import com.example.owner.takeandgouser.model.entities.Car;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by Owner on 11/11/2017.
 */

//TODO: Take care of casting all the formats like: dates, enums and classes
public class AgencyConsts {

    //region Static consts
    public static class BranchConst {
        public static final String ADRESS = "address";
        public static final String STREET_NUMBER = "street_number";
        public static final String STREET = "street";
        public static final String CITY = "city";
        public static final String PARKING = "parking";
        public static final String BRANCH_NUMBER = "_id";

    }
    public static class CarConst {
        public static final String BRANCH_NUMBER = "branchNumber";
        public static final String MODEL_TYPE = "model_id";
        public static final String MILEAGE= "mileage";
        public static final String NUMBER = "_id";
        public static final String AVAILABLE = "available";
    }
    public static class CarModelConst {
        public static final String CODE = "_id";
        public static final String COMPANY_NAME = "companyName";
        public static final String MODEL_NAME = "modelName";
        public static final String ENGINE_CAPACITY = "engineCapacity";
        public static final String GEARBOX = "gearbox";
        public static final String SEATS = "seats";
        public static final String COLOR = "color";
    }
    public static class ClientConst {
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String ID = "_id";
        public static final String EMAIL = "email";
        public static final String CREDIT_CARD = "creditCard";
        public static final String BIRTHDAY = "birthday";
        public static final String CELLPHONE = "cellphone";
    }
    public static class OrderConst {
        public static final String CLIENT_NUMBER = "clientNumber";
        public static final String OPEN = "open";
        public static final String CAR_NUMBER = "carNumber";
        public static final String RENT_START = "rentStart";
        public static final String RENT_END = "rentEnd";
        public static final String MILEAGE_START = "mileageStart";
        public static final String MILEAGE_END = "mileageEnd";
        public static final String GAS_FILLED = "gasFilled";
        public static final String GAS_LITERS = "gasLiters";
        public static final String FINAL_BILLING= "finalBilling";
        public static final String ORDER_NUMBER= "_id";
    }
    //endregion

    //region Types to Content values casting
    public static ContentValues BranchToContentValues(Branch branch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BranchConst.BRANCH_NUMBER, branch.getBranchNumber());
        contentValues.put(BranchConst.PARKING, branch.getParking());
        contentValues.put(BranchConst.ADRESS,(branch.getAdress().toString()));

        return contentValues;
    }
    public static ContentValues CarToContentValues(Car car) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CarConst.BRANCH_NUMBER,car.getBranchNumber());
        contentValues.put(CarConst.MODEL_TYPE, car.getModelType());
        contentValues.put(CarConst.MILEAGE, car.getMileage());
        contentValues.put(CarConst.NUMBER, car.getNumber());
        contentValues.put(CarConst.AVAILABLE, car.isAvailable());

        return contentValues;
    }
    public static ContentValues CarModelToContentValues(CarModel carModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CarModelConst.CODE,carModel.getCode());
        contentValues.put(CarModelConst.COMPANY_NAME,carModel.getCompanyName());
        contentValues.put(CarModelConst.MODEL_NAME,carModel.getModelName());
        contentValues.put(CarModelConst.ENGINE_CAPACITY,carModel.getEngineCapacity());
        contentValues.put(CarModelConst.GEARBOX, String.valueOf(carModel.getGearbox()));
        contentValues.put(CarModelConst.SEATS,carModel.getSeats());
        //contentValues.put(CarModelConst.COLOR, String.valueOf(carModel.getColor()));
        contentValues.put(CarModelConst.COLOR, carModel.getColor());
        return contentValues;
    }
    public static ContentValues ClientToContentValues(Client client) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ClientConst.FIRST_NAME, client.getFirstName());
        contentValues.put(ClientConst.LAST_NAME, client.getLastName());
        contentValues.put(ClientConst.ID, client.getId());
        contentValues.put(ClientConst.EMAIL, client.getEmail());
        contentValues.put(ClientConst.CREDIT_CARD, client.getCreditCard());
        contentValues.put(ClientConst.CELLPHONE, client.getCellphoneNumber());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // like MySQL Format
        String dateString = dateFormat.format(client.getBirthday());
        contentValues.put(ClientConst.BIRTHDAY, dateString);

        return contentValues;
    }
    public static ContentValues OrderToContentValues(Order order) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderConst.CLIENT_NUMBER, order.getClientId());
        contentValues.put(OrderConst.OPEN, order.isOpen());
        contentValues.put(OrderConst.CAR_NUMBER, order.getCarNumber());
        DateFormat dateStartFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String dateStartString = dateStartFormat.format(order.getRentStart());
        contentValues.put(OrderConst.RENT_START, dateStartString);
        DateFormat dateEndFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String dateEndString = dateEndFormat.format(order.getRentEnd());
        contentValues.put(OrderConst.RENT_END, dateEndString);
        contentValues.put(OrderConst.MILEAGE_START, order.getMileageStart());
        contentValues.put(OrderConst.MILEAGE_END, order.getMileageEnd());
        contentValues.put(OrderConst.GAS_FILLED, order.isGasFilled());
        contentValues.put(OrderConst.GAS_LITERS, order.getGasLiters());
        contentValues.put(OrderConst.FINAL_BILLING, order.getFinalBilling());
        contentValues.put(OrderConst.ORDER_NUMBER, order.getOrderNumber());

        return contentValues;
    }
    //endregion

    //region Content values to types casting
    public static Branch ContentValuesToBranch(ContentValues contentValues) {

        Branch branch = new Branch();
        Adress adress = new Adress();
        adress.setNumber(contentValues.getAsInteger(BranchConst.STREET_NUMBER));
        adress.setStreet(contentValues.getAsString(BranchConst.STREET));
        adress.setCity(contentValues.getAsString(BranchConst.CITY));
        branch.setAdress(adress);
        branch.setParking(contentValues.getAsInteger(BranchConst.PARKING));
        branch.setBranchNumber(contentValues.getAsInteger(BranchConst.BRANCH_NUMBER));

        return branch;
    }
    public static Car ContentValuesToCar(ContentValues contentValues) {

        Car car = new Car();
        car.setBranchNumber(contentValues.getAsInteger(CarConst.BRANCH_NUMBER));
        car.setModelType(contentValues.getAsInteger(CarConst.MODEL_TYPE));
        car.setMileage(contentValues.getAsDouble(CarConst.MILEAGE));
        car.setNumber(contentValues.getAsLong(CarConst.NUMBER));
        car.setAvailable(contentValues.getAsBoolean(CarConst.AVAILABLE));

        return car;
    }
    public static CarModel ContentValuesToCarModel(ContentValues contentValues) {

        CarModel carModel = new CarModel();
        carModel.setCode(contentValues.getAsInteger(CarModelConst.CODE));
        carModel.setCompanyName(contentValues.getAsString(CarModelConst.COMPANY_NAME));
        carModel.setModelName(contentValues.getAsString(CarModelConst.MODEL_NAME));
        carModel.setEngineCapacity(contentValues.getAsDouble(CarModelConst.ENGINE_CAPACITY));
        carModel.setGearbox(GEARBOX.valueOf(contentValues.getAsString(AgencyConsts.CarModelConst.GEARBOX)));
        carModel.setSeats(contentValues.getAsInteger(CarModelConst.SEATS));
        //carModel.setColor(COLOR.valueOf(contentValues.getAsString(CarModelConst.COLOR)));
        carModel.setColor(contentValues.getAsString(CarModelConst.COLOR));

        return carModel;
    }
    public static Client ContentValuesToClient(ContentValues contentValues) {

        Client client = new Client();
        client.setFirstName(contentValues.getAsString(ClientConst.FIRST_NAME));
        client.setLastName(contentValues.getAsString(ClientConst.LAST_NAME));
        client.setId(contentValues.getAsString(ClientConst.ID));
        client.setEmail(contentValues.getAsString(ClientConst.EMAIL));
        client.setCreditCard(contentValues.getAsLong(ClientConst.CREDIT_CARD));
        client.setCellphoneNumber(contentValues.getAsString(ClientConst.CELLPHONE));
        DateFormat birthDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // like MySQL Format
        String birthDateString = contentValues.getAsString(ClientConst.BIRTHDAY);
        try {
            client.setBirthday(birthDateFormat.parse(birthDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return client;
    }
    public static Order ContentValuesToOrder(ContentValues contentValues) {

        Order order = new Order();
        order.setClientId(contentValues.getAsString(OrderConst.CLIENT_NUMBER));
        order.setOpen(contentValues.getAsBoolean(OrderConst.OPEN));
        order.setCarNumber(contentValues.getAsInteger(OrderConst.CAR_NUMBER));
        DateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String startDateString = contentValues.getAsString(OrderConst.RENT_START);
        try {
            order.setRentStart(startDateFormat.parse(startDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String endDateString = contentValues.getAsString(OrderConst.RENT_END);
        try {
            order.setRentStart(endDateFormat.parse(endDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order.setMileageStart(contentValues.getAsDouble(OrderConst.MILEAGE_START));
        order.setMileageEnd(contentValues.getAsDouble(OrderConst.MILEAGE_END));
        order.setGasFilled(contentValues.getAsBoolean(OrderConst.GAS_FILLED));
        order.setGasLiters(contentValues.getAsDouble(OrderConst.GAS_LITERS));
        order.setFinalBilling(contentValues.getAsDouble(OrderConst.FINAL_BILLING));
        order.setOrderNumber(contentValues.getAsInteger(OrderConst.ORDER_NUMBER));

        return order;
    }
    //endregion

}