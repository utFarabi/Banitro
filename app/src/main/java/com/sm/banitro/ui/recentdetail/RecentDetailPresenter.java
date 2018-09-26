package com.sm.banitro.ui.recentdetail;

import android.content.Context;

public class RecentDetailPresenter implements RecentDetailContract.Presenter {
    private RecentDetailContract.View iaView;
//    private Repository repository;

    public RecentDetailPresenter(RecentDetailContract.View iaView, Context context) {
        this.iaView = iaView;
//        repository = new Repository(context);
    }
}