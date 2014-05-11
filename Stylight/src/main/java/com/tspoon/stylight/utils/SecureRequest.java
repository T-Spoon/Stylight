package com.tspoon.stylight.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;
import com.tspoon.stylight.Stylight;

import java.util.HashMap;
import java.util.Map;

public class SecureRequest extends JacksonRequest {

    public SecureRequest(int method, String url, JacksonRequestListener listener) {
        super(method, url, listener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.isEmpty()) {
            headers = new HashMap<String, String>();
        }

        headers.put("X-apiKey", "D13A5A5A0A3602477A513E02691A8458");
        headers.put("Accept-Language", "de-DE");

        String cookie = Stylight.getSettings().getSecureCookie();
        if (cookie != null && !cookie.isEmpty()) {
            headers.put("Cookie", "st_secure=" + cookie);
        }

        return headers;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String cookie = response.headers.get("Set-Cookie");
        if (cookie != null && !cookie.isEmpty()) {
            Stylight.getSettings().setSecureCookie(response.headers.get("Set-Cookie"));
        }
        return super.parseNetworkResponse(response);
    }
}