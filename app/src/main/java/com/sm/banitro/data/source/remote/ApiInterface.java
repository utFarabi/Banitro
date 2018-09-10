package com.sm.banitro.data.source.remote;

import com.sm.banitro.data.model.Demand;
import com.sm.banitro.data.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ApiInterface {

    @GET("")
    Call<ArrayList<Demand>> getDemands();

    @GET("product/{product_id}")
    Call<Product> getProduct(@Path("product_id") int productId);
}