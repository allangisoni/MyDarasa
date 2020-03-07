package com.mydarasa.app;

import androidx.annotation.Nullable;

public class MyServiceHolder {

    GetDataService myService = null;

    @Nullable
    public GetDataService get() {
        return myService;
    }

    public void set(GetDataService myService) {
        this.myService = myService;
    }
}
