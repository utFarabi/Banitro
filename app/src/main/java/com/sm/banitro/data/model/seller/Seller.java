package com.sm.banitro.data.model.seller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seller {

    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("rich_editing")
    @Expose
    private String richEditing;
    @SerializedName("syntax_highlighting")
    @Expose
    private String syntaxHighlighting;
    @SerializedName("comment_shortcuts")
    @Expose
    private String commentShortcuts;
    @SerializedName("admin_color")
    @Expose
    private String adminColor;
    @SerializedName("show_admin_bar_front")
    @Expose
    private String showAdminBarFront;
    @SerializedName("wp_capabilities")
    @Expose
    private String wpCapabilities;
    @SerializedName("_yoast_wpseo_profile_updated")
    @Expose
    private String yoastWpseoProfileUpdated;
    @SerializedName("category")
    @Expose
    private ArrayList<String> category;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("id")
    @Expose
    private String id;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRichEditing() {
        return richEditing;
    }

    public void setRichEditing(String richEditing) {
        this.richEditing = richEditing;
    }

    public String getSyntaxHighlighting() {
        return syntaxHighlighting;
    }

    public void setSyntaxHighlighting(String syntaxHighlighting) {
        this.syntaxHighlighting = syntaxHighlighting;
    }

    public String getCommentShortcuts() {
        return commentShortcuts;
    }

    public void setCommentShortcuts(String commentShortcuts) {
        this.commentShortcuts = commentShortcuts;
    }

    public String getAdminColor() {
        return adminColor;
    }

    public void setAdminColor(String adminColor) {
        this.adminColor = adminColor;
    }

    public String getShowAdminBarFront() {
        return showAdminBarFront;
    }

    public void setShowAdminBarFront(String showAdminBarFront) {
        this.showAdminBarFront = showAdminBarFront;
    }

    public String getWpCapabilities() {
        return wpCapabilities;
    }

    public void setWpCapabilities(String wpCapabilities) {
        this.wpCapabilities = wpCapabilities;
    }

    public String getYoastWpseoProfileUpdated() {
        return yoastWpseoProfileUpdated;
    }

    public void setYoastWpseoProfileUpdated(String yoastWpseoProfileUpdated) {
        this.yoastWpseoProfileUpdated = yoastWpseoProfileUpdated;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}