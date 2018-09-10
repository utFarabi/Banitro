package com.sm.banitro.ui.recent;

import com.sm.banitro.data.model.Demand;

import java.util.ArrayList;

public interface RecentContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showDemands(ArrayList<Demand> demands);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void loadData();
    }
}