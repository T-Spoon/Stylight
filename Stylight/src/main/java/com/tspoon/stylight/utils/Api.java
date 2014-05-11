package com.tspoon.stylight.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.spothero.volley.JacksonRequestListener;

public class Api {

    private static final String URL_LOGIN = "http://api.stylight.com/api/login?username=%s&passwd=%s";
    private static final String URL_PRODUCTS = "http://api.stylight.de/api/products";
    private static Api sInstance;

    private RequestQueue mRequestQueue;
    private Context mContext;

    private Api(Context c) {
        mContext = c;
        mRequestQueue = Volley.newRequestQueue(c);
    }

    public static Api getInstance(Context c) {
        if (sInstance == null) {
            sInstance = new Api(c);
        }
        return sInstance;
    }

    public void login(JacksonRequestListener listener) {
        String username = "Tspoon";
        String passwd = "iamapassword";

        String url = String.format(URL_LOGIN, username, passwd);
        SecureRequest request = new SecureRequest(Request.Method.POST, url, listener);
        mRequestQueue.add(request);
    }

    public void fetchProducts(JacksonRequestListener listener) {
        SecureRequest request = new SecureRequest(Request.Method.GET, URL_PRODUCTS, listener);
        mRequestQueue.add(request);
    }
}
