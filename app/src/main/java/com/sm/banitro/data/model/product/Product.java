
package com.sm.banitro.data.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("car_co")
    @Expose
    private String carCo;
    @SerializedName("car_mo")
    @Expose
    private String carMo;
    @SerializedName("car_spid")
    @Expose
    private String carSpid;
    @SerializedName("car_year")
    @Expose
    private String carYear;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("pro_name")
    @Expose
    private String proName;
    @SerializedName("product_dc")
    @Expose
    private String productDc;
    @SerializedName("pro_cat")
    @Expose
    private String proCat;
    @SerializedName("pro_number")
    @Expose
    private String proNumber;
    @SerializedName("pro_picture")
    @Expose
    private String proPicture;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("is_replied")
    @Expose
    private boolean isReplied;
    @SerializedName("reply_price")
    @Expose
    private String replyPrice;
    @SerializedName("reply_dc")
    @Expose
    private String replyDc;
    @SerializedName("reply_approved")
    @Expose
    private boolean replyApproved;
    @SerializedName("notifcheck")
    @Expose
    private boolean notifcheck;
    @SerializedName("position")
    @Expose
    private String position;

    protected Product(Parcel in) {
        id = in.readString();
        userName = in.readString();
        carCo = in.readString();
        carMo = in.readString();
        carSpid = in.readString();
        carYear = in.readString();
        phoneNumber = in.readString();
        proName = in.readString();
        productDc = in.readString();
        proCat = in.readString();
        proNumber = in.readString();
        proPicture = in.readString();
        clientId = in.readString();
        sellerId = in.readString();
        isReplied = in.readByte() != 0;
        replyPrice = in.readString();
        replyDc = in.readString();
        replyApproved = in.readByte() != 0;
        notifcheck = in.readByte() != 0;
        position = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(carCo);
        dest.writeString(carMo);
        dest.writeString(carSpid);
        dest.writeString(carYear);
        dest.writeString(phoneNumber);
        dest.writeString(proName);
        dest.writeString(productDc);
        dest.writeString(proCat);
        dest.writeString(proNumber);
        dest.writeString(proPicture);
        dest.writeString(clientId);
        dest.writeString(sellerId);
        dest.writeByte((byte) (isReplied ? 1 : 0));
        dest.writeString(replyPrice);
        dest.writeString(replyDc);
        dest.writeByte((byte) (replyApproved ? 1 : 0));
        dest.writeByte((byte) (notifcheck ? 1 : 0));
        dest.writeString(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCarCo() {
        return carCo;
    }

    public void setCarCo(String carCo) {
        this.carCo = carCo;
    }

    public String getCarMo() {
        return carMo;
    }

    public void setCarMo(String carMo) {
        this.carMo = carMo;
    }

    public String getCarSpid() {
        return carSpid;
    }

    public void setCarSpid(String carSpid) {
        this.carSpid = carSpid;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProductDc() {
        return productDc;
    }

    public void setProductDc(String productDc) {
        this.productDc = productDc;
    }

    public String getProCat() {
        return proCat;
    }

    public void setProCat(String proCat) {
        this.proCat = proCat;
    }

    public String getProNumber() {
        return proNumber;
    }

    public void setProNumber(String proNumber) {
        this.proNumber = proNumber;
    }

    public String getProPicture() {
        return proPicture;
    }

    public void setProPicture(String proPicture) {
        this.proPicture = proPicture;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isReplied() {
        return isReplied;
    }

    public void setIsReplied(boolean isReplied) {
        this.isReplied = isReplied;
    }

    public String getReplyPrice() {
        return replyPrice;
    }

    public void setReplyPrice(String replyPrice) {
        this.replyPrice = replyPrice;
    }

    public String getReplyDc() {
        return replyDc;
    }

    public void setReplyDc(String replyDc) {
        this.replyDc = replyDc;
    }

    public boolean isReplyApproved() {
        return replyApproved;
    }

    public void setReplyApproved(boolean replyApproved) {
        this.replyApproved = replyApproved;
    }

    public boolean isNotifcheck() {
        return notifcheck;
    }

    public void setNotifcheck(boolean notifcheck) {
        this.notifcheck = notifcheck;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}