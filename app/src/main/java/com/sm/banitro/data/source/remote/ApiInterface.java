package com.sm.banitro.data.source.remote;

import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.model.register.RegisterResponse;
import com.sm.banitro.data.model.seller.Seller;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // ********************************************************************************
    // Product

    @GET("seller/{seller_id}/{position}")
    Call<ArrayList<Product>> getProducts(@Path("seller_id") String sellerId,
                                         @Path("position") String condition,
                                         @Query("offset") int offset,
                                         @Query("limit") int limit,
                                         @Header("Authorization") String token);

    @GET("delete/{reply_id}/{seller_id}")
    Call<BaseResponse> getProductForDelete(@Path("reply_id") String productId,
                                           @Path("seller_id") String sellerId,
                                           @Header("Authorization") String token);

    @GET("notif/{seller_id}")
    Call<ArrayList<Product>> getProductsForNotif(@Path("seller_id") String sellerId,
                                                 @Header("Authorization") String token);

    // ********************************************************************************
    // Reply

    @POST("reply/suggest")
    @FormUrlEncoded
    Call<BaseResponse> postReplySuggest(@Field("reply_price") String replyPrice,
                                        @Field("reply_dc") String replyDc,
                                        @Field("reply_id") String productId,
                                        @Header("Authorization") String token);

    @POST("reply/edit")
    @FormUrlEncoded
    Call<BaseResponse> postReplyEdit(@Field("reply_price") String replyPrice,
                                     @Field("reply_dc") String replyDc,
                                     @Field("reply_id") String productId,
                                     @Header("Authorization") String token);

    // ********************************************************************************
    // Seller

    @GET("seller/id/{seller_id}")
    Call<Seller> getSeller(@Path("seller_id") String sellerId,
                           @Header("Authorization") String token);

    @Multipart
    @POST("user/edit")
    Call<BaseResponse> postProfileImage(@Part("id") RequestBody sellerId,
                                        @Part MultipartBody.Part image,
                                        @Header("Authorization") String token);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfileName(@Field("id") String sellerId,
                                       @Field("full_name") String name,
                                       @Header("Authorization") String token);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfilePhoneNumber(@Field("id") String sellerId,
                                              @Field("user_phone") String phoneNumber,
                                              @Header("Authorization") String token);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfileAddress(@Field("id") String sellerId,
                                          @Field("user_address") String address,
                                          @Header("Authorization") String token);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfileCategory(@Field("id") String sellerId,
                                           @Field("user_category") String category,
                                           @Header("Authorization") String token);

    @POST("user/register")
    @FormUrlEncoded
    Call<RegisterResponse> postRegisterInfo(@Field("full_name") String name,
                                            @Field("user_phone") String phoneNamber,
                                            @Field("user_address") String address,
                                            @Field("user_category") String categories,
                                            @Field("user_pass") String password,
                                            @Header("Authorization") String token);

    @POST("user/login")
    @FormUrlEncoded
    Call<RegisterResponse> postLoginInfo(@Field("username") String username,
                                         @Field("password") String password,
                                         @Header("Authorization") String token);
}