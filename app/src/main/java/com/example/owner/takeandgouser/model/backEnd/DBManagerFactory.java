package com.example.owner.takeandgouser.model.backEnd;

import com.example.owner.takeandgouser.model.datasource.MySQL_DBManager;

/**
 * Created by Owner on 14/11/2017.
 */

public class DBManagerFactory {
    static DB_manager manager = null;

    public static DB_manager getManager() {

        if (manager == null) {
            //manager = new List_DBManager();
            manager = new MySQL_DBManager();
        }

        return manager;
    }

}
