package com.sm.banitro.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Demand implements Parcelable {

    private int productId;
    private String productName;
    private String productCategory;

    protected Demand(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        productCategory = in.readString();
    }

    public static final Creator<Demand> CREATOR = new Creator<Demand>() {
        @Override
        public Demand createFromParcel(Parcel in) {
            return new Demand(in);
        }

        @Override
        public Demand[] newArray(int size) {
            return new Demand[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(productId);
        parcel.writeString(productName);
        parcel.writeString(productCategory);
    }
}