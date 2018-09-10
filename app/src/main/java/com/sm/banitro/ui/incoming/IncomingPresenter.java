package com.sm.banitro.ui.incoming;

public class IncomingPresenter implements IncomingContract.Presenter {
    private IncomingContract.View iaView;

    public IncomingPresenter(IncomingContract.View iaView) {
        this.iaView = iaView;
    }
}