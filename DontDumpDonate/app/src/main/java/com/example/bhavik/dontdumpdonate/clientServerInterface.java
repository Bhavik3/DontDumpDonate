package com.example.bhavik.dontdumpdonate;

/**
 * Created by Bhavik on 03-04-2015.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class clientServerInterface {

    //input stream deals with bytes
    static InputStream is = null;
    static JSONObject jobj = null;
    static String json = "";

    //constructor
    public clientServerInterface() {

    }

    //this method returns json object.
    //JSONObject
    public static JSONObject makeHttpRequest(String url, List<NameValuePair> params) {
        //http client helps to send and receive data
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //our request method is post
        // HttpPost httppost = new HttpPost(url);
        try {
            //get the response
            String paramString = URLEncodedUtils.format(params, "utf-8");
            url += "?" + paramString;
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity = httpresponse.getEntity();
            // get the content and store it into inputstream object.
            is = httpentity.getContent();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonResult = answer.toString();

        System.out.println("json result is " + jsonResult);
        try {
            jobj = new JSONObject(jsonResult);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jobj;
    }
}