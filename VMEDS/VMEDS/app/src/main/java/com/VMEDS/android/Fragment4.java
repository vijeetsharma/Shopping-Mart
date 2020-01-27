package com.VMEDS.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.VMEDS.android.adapter.OffersAdapter;
import com.VMEDS.android.model.OfferDetail;
import com.VMEDS.android.utils.Global_Typeface;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class Fragment4 extends Fragment {

    private ListView listOffers;
    private Global_Typeface global_typeface;
    private AVLoadingIndicatorView loadingAvi;
    private StaticData static_data;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private Vector<OfferDetail> vOffersList;
    private TextView txtBlankData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_offers, container, false);

        global_typeface = new Global_Typeface(getActivity());

        loadingAvi = (AVLoadingIndicatorView) v.findViewById(R.id.loadingAvi);
        static_data = new StaticData();

        listOffers = (ListView) v.findViewById(R.id.listOffers);
        txtBlankData = (TextView) v.findViewById(R.id.txtBlankData);
        txtBlankData.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        getOfferList();
        return v;

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity(). getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void getOfferList() {

        if (isNetworkConnected()) {
            loadingAvi.setVisibility(View.VISIBLE);
            String url = static_data.BASE_URL + static_data.OFFER_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vOffersList = new Vector<OfferDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("offer_data"));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                //  JSONArray jsonArraySubCat = new JSONArray(jsonObject2.getString("sub_category"));
                                OfferDetail obj = new OfferDetail(jsonObject2.getString("id"), jsonObject2.getString("name"), jsonObject2.getString("description"), jsonObject2.getString("amount"));
                                vOffersList.add((OfferDetail) obj);
                            }
                            listOffers.setAdapter(new OffersAdapter(getActivity(), vOffersList));

                            loadingAvi.setVisibility(View.GONE);
                        } else {
                            loadingAvi.setVisibility(View.GONE);
                            txtBlankData.setVisibility(View.VISIBLE);
                            listOffers.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loadingAvi.setVisibility(View.GONE);
                        txtBlankData.setVisibility(View.VISIBLE);
                        listOffers.setVisibility(View.GONE);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loadingAvi.setVisibility(View.GONE);
                    txtBlankData.setVisibility(View.VISIBLE);
                    listOffers.setVisibility(View.GONE);
                    Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Problem" + volleyError.toString(), Toast.LENGTH_LONG).show();
                }
            });
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getOfferList();
                }
            }, 5000);
        }
    }


}