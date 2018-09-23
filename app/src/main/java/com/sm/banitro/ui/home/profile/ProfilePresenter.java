package com.sm.banitro.ui.home.profile;

import com.sm.banitro.data.model.Category;
import com.sm.banitro.data.model.Seller;

import java.io.File;
import java.util.ArrayList;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View iaView;

    public ProfilePresenter(ProfileContract.View iaView) {
        this.iaView = iaView;
    }

    @Override
    public void loadData() {
        test();
    }

    @Override
    public void uploadSellerImage(File file) {
        iaView.showSellerImage(file);
    }

    private void test() {
        Seller seller = new Seller();
        seller.setName("سینا الماسی");
        seller.setPhoneNumber("09397696665");
        seller.setAddress("تهران-مشیریه-خیابان بیست متری اعظم-کوچه فرخی امندی-پلاک 4-واحد 6");

        ArrayList<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName("جلوبندی");
        categories.add(category);

        category = new Category();
        category.setName("عقب");
        categories.add(category);

        category = new Category();
        category.setName("اتاق");
        categories.add(category);

        seller.setCategories(categories);

        iaView.showSeller(seller);
    }
}