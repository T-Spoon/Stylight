package com.tspoon.stylight.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequestListener;
import com.tspoon.stylight.R;
import com.tspoon.stylight.model.LoginResponse;
import com.tspoon.stylight.model.ProductsPage;
import com.tspoon.stylight.ui.adapters.ProductListAdapter;
import com.tspoon.stylight.utils.Api;

public class FragmentProducts extends ListFragment {

    private static final String TAG = "FragmentProducts";

    private static final String KEY_LOGGED_IN = "KEY_LOGGED_IN";

    private boolean isRefreshing;
    private boolean isLoggedIn;

    private Request loginRequest;
    private Request dataRequest;

    public static FragmentProducts newInstance() {
        FragmentProducts fragment = new FragmentProducts();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (bundle != null) {
            isLoggedIn = bundle.getBoolean(KEY_LOGGED_IN);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDivider(null);
        getListView().setSelector(android.R.color.transparent);
        if (isLoggedIn) {
            login();
        } else {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loginRequest != null) loginRequest.cancel();
        if (dataRequest != null) dataRequest.cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_LOGGED_IN, isLoggedIn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void login() {
        loginRequest = Api.getInstance(getActivity()).login(new JacksonRequestListener() {
            @Override
            public void onResponse(Object response, int statusCode, VolleyError error) {
                if (response != null) {
                    refresh();
                    isLoggedIn = true;
                } else {
                    Log.e(TAG, "An error occurred while parsing the data. Status: " + statusCode);
                    if (error != null) error.printStackTrace();
                    Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public JavaType getReturnType() {
                return SimpleType.construct(LoginResponse.class);
            }
        });
    }

    private void refresh() {
        if (!isRefreshing) {
            setListShown(false);
            dataRequest = Api.getInstance(getActivity()).fetchProducts(mProductsResponseListener);
            isRefreshing = true;
        }
    }

    private JacksonRequestListener<ProductsPage> mProductsResponseListener = new JacksonRequestListener<ProductsPage>() {
        @Override
        public void onResponse(ProductsPage response, int statusCode, VolleyError error) {
            if (response != null) {
                getListView().setAdapter(new ProductListAdapter(getActivity(), 0, response.getPairedProductList()));
            } else {
                Log.e(TAG, "An error occurred while parsing the data. Status: " + statusCode);
                if (error != null) error.printStackTrace();
                Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
            }
            isRefreshing = false;
            setListShown(true);
        }

        @Override
        public JavaType getReturnType() {
            return SimpleType.construct(ProductsPage.class);
        }
    };
}
