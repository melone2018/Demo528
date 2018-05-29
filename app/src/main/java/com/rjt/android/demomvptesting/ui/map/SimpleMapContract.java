package com.rjt.android.demomvptesting.ui.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.rjt.android.demomvptesting.ui.base.BasePresenter;
import com.rjt.android.demomvptesting.ui.base.BaseView;

interface SimpleMapContract {
    interface IView extends BaseView<IPresenter> {

    }
    interface IPresenter extends BasePresenter{

        void setOnMapReady(String query, GoogleMap googleMap);
    }
}
