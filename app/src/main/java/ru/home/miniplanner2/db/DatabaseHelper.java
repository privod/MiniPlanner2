package ru.home.miniplanner2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.home.miniplanner2.model.Bay;
import ru.home.miniplanner2.model.Contribution;
import ru.home.miniplanner2.model.Domain;
import ru.home.miniplanner2.model.Party;
import ru.home.miniplanner2.model.Plan;
//import ru.home.miniplanner2.service.BayDao;
//import ru.home.miniplanner2.service.ContributionDao;
//import ru.home.miniplanner2.service.PartyDao;
//import ru.home.miniplanner2.service.Dao;

/**
 * Created by privod on 23.10.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME ="planner.db";
    private static final int DATABASE_VERSION = 1;

//    private Map<Class, Dao<Domain>> daoMap;
    private Dao<Plan> planDao;
    private Dao<Party> partyDao;
    private Dao<Bay> bayDao;
    private Dao<Contribution> contributionDao;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Plan.class);
            TableUtils.createTable(connectionSource, Party.class);
            TableUtils.createTable(connectionSource, Bay.class);
            TableUtils.createTable(connectionSource, Contribution.class);
        }
        catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }
//        daoMap = new HashMap<>();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try
        {
            TableUtils.dropTable(connectionSource, Plan.class, true);
            TableUtils.createTable(connectionSource, Plan.class);
            TableUtils.dropTable(connectionSource, Party.class, true);
            TableUtils.createTable(connectionSource, Party.class);
            TableUtils.dropTable(connectionSource, Bay.class, true);
            TableUtils.createTable(connectionSource, Bay.class);
            TableUtils.dropTable(connectionSource, Contribution.class, true);
            TableUtils.createTable(connectionSource, Contribution.class);

            //TODO update tables with save data.
//            upgradeTable(database, getPlanDao(), Plan.class);
//            upgradeTable(database, getPartyDao(), Party.class);
//            upgradeTable(database, getBayDao(), Bay.class);
//            upgradeTable(database, getContributionDao(), Contribution.class);

        }
        catch (SQLException e){
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public <T extends Domain> void upgradeTable(SQLiteDatabase database, BaseDaoImpl dao, Class<T> type) throws SQLException {
        List<T> oldData = dao.queryForAll();
        TableUtils.dropTable(connectionSource, type, true);
        TableUtils.createTable(connectionSource, type);
        for (T entity :
                oldData) {
            dao.create(entity);
        }
    }

//    public synchronized <T extends Domain> Dao<T> getAnyDao(Class<T> tClass) {
//        Dao<T> dao = daoMap.get(tClass);
//        if (null == dao) {
//            try {
//                dao = new Dao<T>(getConnectionSource(), tClass);
//            } catch (SQLException e) {
//                Log.e(TAG, e.getMessage());
//            }
//            daoMap.put(tClass, dao);
//        }
//        return dao;
//    }

    private <T extends Domain> Dao<T> daoCreate(Class<T> tClass) {
        Dao<T> dao = null;
        try {
            dao = new Dao<T>(getConnectionSource(), tClass);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return dao;
    }

    public synchronized Dao<Plan> getPlanDao() {
        if (null == planDao) {
            planDao = daoCreate(Plan.class);
        }
        return planDao;
    }

//    public synchronized Dao getPlanDao() {
//        if (null == planDao) {
//            try {
//                planDao = new Dao(getConnectionSource(), Plan.class);
//            } catch (SQLException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }
//        return planDao;
//    }
//
//    public synchronized PartyDao getPartyDao() {
//        if (null == partyDao) {
//            try {
//                partyDao = new PartyDao(getConnectionSource(), Party.class);
//            } catch (SQLException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }
//        return partyDao;
//    }
//
//    public synchronized BayDao getBayDao() {
//        if (null == bayDao) {
//            try {
//                bayDao = new BayDao(getConnectionSource(), Bay.class);
//            } catch (SQLException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }
//        return bayDao;
//    }
//
//    public synchronized ContributionDao getContributionDao() {
//        if (null == contributionDao) {
//            try {
//                contributionDao = new ContributionDao(getConnectionSource(), Contribution.class);
//            } catch (SQLException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }
//        return contributionDao;
//    }

    @Override
    public void close() {
        planDao = null;
        partyDao = null;
        bayDao = null;
        contributionDao = null;
        super.close();
    }
}
