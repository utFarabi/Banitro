package com.sm.banitro.data.source.remote;

import com.sm.banitro.data.model.Product;
import com.sm.banitro.data.model.Reply;
import com.sm.banitro.data.model.ReplyResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface ApiInterface {

    @GET("{seller_id}/{position}")
    Call<ArrayList<Product>> getProducts(@Path("seller_id") int sellerId,
                                         @Path("position") int position);

    @POST("{seller_id}/{product_id}")
    @FormUrlEncoded
    Call<ReplyResponse> postReply(@Path("seller_id") int sellerId,
                                  @Path("product_id") int productId,
                                  @Field("is_replied") boolean isReplied,
                                  @Field("reply") Reply reply);
}