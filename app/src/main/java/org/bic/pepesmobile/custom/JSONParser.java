package org.bic.pepesmobile.custom;

import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adityalegowo on 11/3/15.
 */
public class JSONParser {

    private PepesSingleton singleton;
    private static final String TAG = "JSONParser";
    private Pelayanan p;


    public JSONParser(){
        singleton = PepesSingleton.getInstance();

    }

    public void parsePelayananJSON(String jsonString) throws JSONException {
//        JSONObject value = json.getJSONObject("value");

        JSONObject items = new JSONObject(jsonString);
//        JSONArray jsonArray = new JSONArray(jsonString);
        Log.i(TAG,"lengthnya adalah "+items.length());
        for (int i = 0; i < items.length(); i++) {
            HashMap<String,String> map = new HashMap<String, String>();
            JSONObject obj = items.getJSONObject(String.valueOf(i));
            p = new Pelayanan();

//            p._id = obj.getString("_id");
            p.nik = obj.getString("nik");
            p.num = obj.getString("num");
            p.tingkat = obj.getString("tingkat");
            p.namalokasi = obj.getString("namalokasi");
            p.dok = obj.getString("dok");
            p.lama = obj.getString("lama");
            p.pungutan = obj.getString("pungutan");

            JSONObject objId = obj.getJSONObject("_id");

            for(int m = 0; m < objId.length(); m++){

                JSONObject objP = objId.getJSONObject(String.valueOf(m));
                Log.i(TAG,"objP " + objP);
                p.id = objP.getString("id");

            }
            singleton.pelayanan = p;
            Log.i(TAG, "jsonisasi " + singleton.pelayanan);
        }
    }


}
