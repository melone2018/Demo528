package com.rjt.android.demomvptesting.ui.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.rjt.android.demomvptesting.ui.base.BasePresenter;
import com.rjt.android.demomvptesting.ui.base.BaseView;

public interface MapActivityContract  {
    interface IView extends BaseView<IPresenter>{

    }
    interface IPresenter extends BasePresenter{
        void getMapReady(Context mContext, String query, GoogleMap googleMap);
    }
}
