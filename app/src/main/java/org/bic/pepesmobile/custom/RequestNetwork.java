package org.bic.pepesmobile.custom;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.bic.pepesmobile.application.PepesApplication;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adityalegowo on 10/30/15.
 */
public class RequestNetwork {


    public RequestManager rManager;
    private static final String TAG = "RequestNetwork";
    public String URL_REQ;
    int method = Request.Method.POST;
    Map mapRequestLogin;
    Map mapRequest;
    JSONParser jsonParse;
    public PepesSingleton singleton = PepesSingleton.getInstance();
    public int index=0;


    public RequestNetwork(String URL,RequestManager valueRequestManager){
        this.URL_REQ = URL;
        method = Request.Method.POST;
        rManager = valueRequestManager;
    }

    public void setLoginModelRequest(String user, String pass){

        mapRequestLogin = new HashMap<String,String>();
        mapRequestLogin.put("usr",user);
        mapRequestLogin.put("pwd",pass);
    }



    public void setPelayananModelRequest(String nik, String num, String tingkat, String lama, String pungutan, String namaLokasi, String dok){
        mapRequest = new HashMap<String,String>();
        mapRequest.put("nik",nik);
        mapRequest.put("num",num);
        mapRequest.put("tingkat",tingkat);
        mapRequest.put("lama",lama);
        mapRequest.put("pungutan", pungutan);
        mapRequest.put("namaLokasi", namaLokasi);
        mapRequest.put("dok", dok);
    }

    public void setRequestJSONMethod(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(method,URL_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);

                switch (rManager){

                    case LOGIN:
                        jsonParse = new JSONParser();
//                        try{
//                            jsonParse.parseJadwalJSON(response);
//                        }catch (JSONException ex){
//                            ex.printStackTrace();
//                        }finally {
//
//                        }
//                        singleton.jadwalKontingen.nm_cabor_fk = response;
                        break;
                    case SUBMIT_RT:
                        jsonParse = new JSONParser();
                        try{
                            Log.i(TAG, "response " + response);
                            jsonParse.parsePelayananJSON(response);
                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }finally {

                        }
                        break;
                    case SUBMIT_RW:
                        jsonParse = new JSONParser();
                        try{
                            Log.i(TAG,"ini response jadwal past " + response);
                            jsonParse.parsePelayananJSON(response);
                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }finally {

                        }
                        break;
                    case SUBMIT_KEL:
                        jsonParse = new JSONParser();
                        try{
                            jsonParse.parsePelayananJSON(response);
                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }finally {

                        }
                        break;
                    case SUBMIT_KEC:
                        jsonParse = new JSONParser();
                        try{
                            jsonParse.parsePelayananJSON(response);
                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }finally {

                        }
                        break;
                    case SUBMIT_KOTA:
                        jsonParse = new JSONParser();
                        try{
                            jsonParse.parsePelayananJSON(response);
                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }finally {

                        }
                        break;

                    default:
                        break;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 400:
                            json = new String(response.data);
                            if(json != null) displayMessage(json);
                            break;
                        case 401:
                            json = new String(response.data);
                            if(json != null) displayMessage(json);
                            break;
                        case 404:
                            json = new String(response.data);
                            if(json != null) displayMessage(json);
                            break;
                        case 503:
                            json = new String(response.data);
                            if(json != null) displayMessage(json);
                            break;
                    }
                    //Additional cases
                }
            }

            //Somewhere that has access to a context
            public void displayMessage(String messageLog){
                Log.e(TAG, messageLog);
                Toast.makeText(PepesApplication.getAppContext(), messageLog,
                        Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                switch (rManager){
                    case LOGIN:
                        params = mapRequestLogin;
                        break;
                    case SUBMIT_RT:
                        params = mapRequest;
                        break;
                    case SUBMIT_RW:
                        params = mapRequest;
                        break;
                    case SUBMIT_KEL:
                        params = mapRequest;
                        break;
                    case SUBMIT_KEC:
                        params = mapRequest;
                        break;
                    case SUBMIT_KOTA:
                        params = mapRequest;
                        break;

                    default:
                        break;

                }

                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return createBasicAuthHeader("admin", "1234");
//            }
//
//            Map<String, String> createBasicAuthHeader(String username, String password) {
//                Map<String, String> headerMap = new HashMap<String, String>();
//
//                String credentials = username + ":" + password;
//                String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                headerMap.put("Authorization", "Basic " + encodedCredentials);
//
//                return headerMap;
//            }
        };
        queue.add(sr);

    }
}

