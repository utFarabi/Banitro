package com.sm.banitro.ui.main.list;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.sm.banitro.data.model.product.Product;

import java.util.ArrayList;

public class ProductDiffUtil extends DiffUtil.Callback {

    // ********************************************************************************
    // Field

    // Instance
    private ArrayList<Product> oldProducts;
    private ArrayList<Product> newProducts;

    // ********************************************************************************
    // Constructor

    public ProductDiffUtil(ArrayList<Product> oldProducts, ArrayList<Product> newProducts) {
        this.oldProducts = oldProducts;
        this.newProducts = newProducts;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public int getOldListSize() {
        return oldProducts.size();
    }

    @Override
    public int getNewListSize() {
        return newProducts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldProducts.get(oldItemPosition).getId().equals(newProducts.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldProducts.get(oldItemPosition).equals(newProducts.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}