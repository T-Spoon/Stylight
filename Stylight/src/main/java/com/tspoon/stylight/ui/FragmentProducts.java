package com.tspoon.stylight.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequestListener;
import com.tspoon.stylight.R;
import com.tspoon.stylight.model.LoginResponse;
import com.tspoon.stylight.model.ProductsPage;
import com.tspoon.stylight.ui.adapters.ProductListAdapter;
import com.tspoon.stylight.utils.Api;

public class FragmentProducts extends Fragment {

    private static final String TAG = "FragmentProducts";

    private boolean isRefreshing;

    private LinearLayout mProgressView;
    private GridView mGridView;

    public static FragmentProducts newInstance() {
        FragmentProducts fragment = new FragmentProducts();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (bundle != null) {

        }

        login();

        setHasOptionsMenu(true);
        //setListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (GridView) view.findViewById(R.id.gridview);
        mProgressView = (LinearLayout) view.findViewById(R.id.progress_bar_holder);
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

    private void setListShown(boolean isShown) {
        if (mGridView != null && mProgressView != null) {
            if (isShown) {
                mGridView.setVisibility(View.VISIBLE);
                mProgressView.setVisibility(View.GONE);
            } else {
                mGridView.setVisibility(View.GONE);
                mProgressView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void login() {
        Api.getInstance(getActivity()).login(new JacksonRequestListener() {
            @Override
            public void onResponse(Object response, int statusCode, VolleyError error) {
                if (response != null) {
                    refresh();
                } else {
                    Log.e(TAG, "An error occurred while parsing the data. Status: " + statusCode);
                    if (error != null) error.printStackTrace();
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
            Api.getInstance(getActivity()).fetchProducts(mProductsResponseListener);
            isRefreshing = true;
        }
    }

    private JacksonRequestListener<ProductsPage> mProductsResponseListener = new JacksonRequestListener<ProductsPage>() {
        @Override
        public void onResponse(ProductsPage response, int statusCode, VolleyError error) {
            if (response != null) {
                if (mGridView != null) {
                    mGridView.setAdapter(new ProductListAdapter(getActivity(), 0, response.getProductList()));
                }
                isRefreshing = false;
                setListShown(true);
            } else {
                Log.e(TAG, "An error occurred while parsing the data. Status: " + statusCode);
                if (error != null) error.printStackTrace();
            }
        }

        @Override
        public JavaType getReturnType() {
            return SimpleType.construct(ProductsPage.class);
        }
    };
}