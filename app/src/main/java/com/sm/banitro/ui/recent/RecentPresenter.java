package com.sm.banitro.ui.recent;

import com.sm.banitro.data.model.Demand;
import com.sm.banitro.data.source.remote.Repository;

import java.util.ArrayList;

public class RecentPresenter implements RecentContract.Presenter {
    private RecentContract.View iaView;
    private Repository repository;

    public RecentPresenter(RecentContract.View iaView) {
        this.iaView = iaView;
//        repository = new Repository();
    }

    @Override
    public void loadData() {
//        iaView.showProgress();
//        repository.loadDemands(new ApiResult<ArrayList<Demand>>() {
//
//            @Override
//            public void onSuccess(ArrayList<Demand> result) {
//                iaView.hideProgress();
//                iaView.showDemands(result);
//            }
//
//            @Override
//            public void onFail(String errorMessage) {
//                iaView.hideProgress();
//                iaView.showErrorMessage(errorMessage);
//            }
//        });
        test();
    }

    private void test() {
        ArrayList<Demand> demands = new ArrayList<>();
        Demand demand = new Demand();
        demand.setProductName("یبسسلشبشیلاتسلت");
        demand.setProductCategory("سشبیلشیبالبا");
        demands.add(demand);
        demand = new Demand();
        demand.setProductName("ئبسیبیسشلبیل");
        demand.setProductCategory("يئؤلیبلباتلباتلت");
        demands.add(demand);
        demand = new Demand();
        demand.setProductName("سشیشیسشیشسیشسیسشیسشیسشیسشیسشیشبیسلبالت");
        demand.setProductCategory("سشبسیبیسلبی");
        demands.add(demand);
        demand = new Demand();
        demand.setProductName("ئؤیبسیشلشیبالتانی");
        demand.setProductCategory("لانتمتنلنابییابینامتتابلیباناالتلناهمتایسبیسلیباللبالبساتلل");
        demands.add(demand);
        demand = new Demand();
        demand.setProductName("یسبیابتنامتنلما");
        demand.setProductCategory("ؤلیبالناتمنتکمنلل");
        demands.add(demand);
        demand = new Demand();
        demand.setProductName("سیبیلبتالاناتمتن");
        demand.setProductCategory("يئلبیابتانتن");
        demands.add(demand);
        demand = new Demand();
        demand.setProductName("ئبسیلیتابلت");
        demand.setProductCategory("إسیبلیبتلانا");
        demands.add(demand);
        demand = new Demand();
        demand.setProductName("يسلبیتالمنتنلقبثس");
        demand.setProductCategory("قثابلنهاعحکهغخغفا");
        demands.add(demand);
        iaView.showDemands(demands);
    }
}