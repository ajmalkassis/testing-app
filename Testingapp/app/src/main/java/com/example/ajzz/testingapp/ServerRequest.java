
package com.example.ajzz.testingapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class ServerRequest {

    static InputStream is = null;
    static JSONObject jObj = null;
    JSONObject jobj;
    static String json = "";
    String msg="logging_test";



    public ServerRequest() {

    Log.d(msg,"constructor"+this.toString());
    }

    public JSONObject getJSONFromUrl(String server_url, JSONObject postDataParams)
    {
        URL url = null;
        jobj=null;
        json=null;
        jObj=null;
        is=null;
        try {
            url = new URL(server_url);
            Log.d(msg,"url to connect"+url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try
        {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d(msg,"connection object"+conn.toString());
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataParams.toString());

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();
            Log.d(msg,"response code"+responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {

                BufferedReader in=new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null)
                {

                    sb.append(line);
                    break;
                }

                in.close();
                Log.d(msg,"return value from server"+sb.toString());
                json =sb.toString();

            }
            else
            {
                json= "{"+"false :"+responseCode+"}";
            }

            jObj = new JSONObject(json);
            Log.d(msg,"json in else after sb.toString()"+json.toString());
            Log.d(msg,"jObj"+jObj.toString());
            Log.d(msg,"ret"+jObj.toString());


        }
        catch (ConnectException e)
        {

        }
        catch (Exception e)
        {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return jObj;
    }
    // try {

    //     DefaultHttpClient httpClient = new DefaultHttpClient();
    //     HttpPost httpPost = new HttpPost(url);
    //     httpPost.setEntity(new UrlEncodedFormEntity(params));

    //     HttpResponse httpResponse = httpClient.execute(httpPost);
    //     HttpEntity httpEntity = httpResponse.getEntity();
    //     is = httpEntity.getContent();

    // } catch (UnsupportedEncodingException e) {
    //     e.printStackTrace();
    // } catch (ClientProtocolException e) {
    //     e.printStackTrace();
    // } catch (IOException e) {
    //     e.printStackTrace();
    // }

    //     try {
    //         BufferedReader reader = new BufferedReader(new InputStreamReader(
    //                 is, "iso-8859-1"), 8);
    //         StringBuilder sb = new StringBuilder();
    //         String line = null;
    //         while ((line = reader.readLine()) != null) {
    //             sb.append(line + "n");
    //         }
    //         is.close();
    //         json = sb.toString();
    //         Log.e("JSON", json);
    //     } catch (Exception e) {
    //         Log.e("Buffer Error", "Error converting result " + e.toString());
    //     }


    //     try {
    //         jObj = new JSONObject(json);
    //     } catch (JSONException e) {
    //         Log.e("JSON Parser", "Error parsing data " + e.toString());
    //     }


    //     return jObj;

    // }

    public JSONObject getJSON(String url, JSONObject postDataParams) {

        Log.d(msg,"url and postdataparams"+url +" and "+postDataParams.toString());
//        JSONObject postdataparams = new JSONObject({
//                                            "url": url,
//                                            "params":postDataParams
//                                          });
        Params param=new Params(url,postDataParams);
        Log.d(msg,"params "+param.toString());
        Request myTask = new Request();
        Log.d(msg,"Request async object"+myTask.toString());
        try{
            jobj= myTask.execute(param).get();
            Log.d(msg,"json result after execute"+jobj);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        if(jobj==null)
        {

            return null;
        }
        return jobj;
    }

    private String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        Log.d(msg,"result of getpostdatastring"+result.toString());
        return result.toString();
    }


     private static class Params {
         String url;
         JSONObject params;


         Params(String url, JSONObject params) {
             this.url = url;
             this.params = params;

         }
     }

    private class Request extends AsyncTask<Params, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(Params... args) {

            ServerRequest request = new ServerRequest();
            JSONObject json=new JSONObject();
            Log.d(msg,"request "+request.toString());
            try
            {
                json = (JSONObject) request.getJSONFromUrl(args[0].url,args[0].params);
                Log.d(msg,"json value result after db"+json.toString());

            }
            catch (Exception e)
            {

            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

        }

    }
}
/*
*/
/**
 * Created by Ajzz on 16-Feb-17.
 *//*


//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.HttpClient ;
import java.io.Reader;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import java.net.URL;
import android.os.AsyncTask;
import android.util.Log;



import android.widget.Toast;



public class ServerRequest {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";


    public ServerRequest() throws IOException, JSONException {

    }

    public JSONObject getJSONFromUrl(String myurl, JSONObject params) {


        try {

//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setEntity(new UrlEncodedFormEntity(params));
//
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            is = httpEntity.getContent();

            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 */
/* milliseconds *//*
);
            conn.setConnectTimeout(15000 */
/* milliseconds *//*
);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("logging_test", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is, length);
            return contentAsString;
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }


        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


        return jObj;

    }
    JSONObject jobj;
    public JSONObject getJSON(String url, List<NameValuePair> params) {

        Params param = new Params(url,params);
        Request myTask = new Request();
        try{
            jobj= myTask.execute(param).get();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return jobj;
    }


    private static class Params {
        String url;
        List<NameValuePair> params;


        Params(String url, List<NameValuePair> params) {
            this.url = url;
            this.params = params;

        }
    }

    private class Request extends AsyncTask<Params, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(Params... args) {

            ServerRequest request = new ServerRequest();
            JSONObject json = request.getJSONFromUrl(args[0].url,args[0].params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

        }
        public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[length];
            reader.read(buffer);
            return new String(buffer);
        }


    }
}
*/
