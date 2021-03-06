package com.example.android.camera2basic;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ServerAsker{
    private String address = "http://10.10.4.212:54269";

    public String post(byte[] request) throws Exception {
        URL url = new URL(address);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty( "Content-Type", "application/octet-stream");
        conn.setRequestProperty( "Content-Length", Integer.toString(request.length));

        try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(request);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            Log.d("RIoTApp", "OK");
            return conn.getHeaderField("Object-id");
        } else {
            throw new IOException("BAD RESPONSE");
        }
    }

    public boolean request(int id, int value) throws Exception {
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty( "Content-Type", "text/html");
        conn.setRequestProperty( "Object-id", Integer.toString(id));
        conn.setRequestProperty( "Request-id", Integer.toString(value));

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            return true;
        } else {
            return false;
        }
    }
}
