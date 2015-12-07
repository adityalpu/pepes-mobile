package org.bic.pepesmobile.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by adityalegowo on 12/6/15.
 */
public class PepesApplication extends Application {
    private static PepesApplication mInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        initializeDatabase();
        this.setAppContext(getApplicationContext());
    }

//    private void initializeDatabase() {
//        List<Class<? extends Model>> models = new ArrayList<>(0);
//        models.add(LogQuickSearch.class);
//        String dbName = this.getResources().getString(R.string.database_name);
//        DatabaseAdapter.setDatabaseName(dbName);
//        DatabaseAdapter adapter = new DatabaseAdapter(mInstance);
//        adapter.setModels(models);
//    }

    public static PepesApplication getInstance(){
        return mInstance;
    }
    public static Context getAppContext() {
        return mAppContext;
    }
    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
}