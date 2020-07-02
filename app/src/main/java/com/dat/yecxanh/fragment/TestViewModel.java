package com.dat.yecxanh.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TestViewModel() {
        mText = new MutableLiveData<String>();
        mText.setValue("Hello Tien Dat");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
