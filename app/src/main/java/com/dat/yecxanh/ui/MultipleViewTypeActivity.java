package com.dat.yecxanh.ui;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat.yecxanh.R;
import com.dat.yecxanh.adapter.MultipleTypeAdapter;
import com.dat.yecxanh.base.BaseActivity;
import com.dat.yecxanh.model.MultiEmployee;
import com.dat.yecxanh.presenter.MultiplePresenter;

import java.util.ArrayList;

public class MultipleViewTypeActivity extends BaseActivity implements MultiplePresenter.MultipleListener {

    private RecyclerView recyclerView;


    @Override
    protected String getTitleScreen() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_multiple_view_type;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        onGet();
    }


    @Override
    protected void afteriInitView() {

    }

    @Override
    protected void initListener() {

    }

    private void onGet() {
        MultiplePresenter presenter = new MultiplePresenter(this);
        presenter.setListener(this);
        presenter.createList();
    }

    @Override
    public void onGetEmployee(ArrayList<MultiEmployee> multiEmployees) {
        //        //set adapter
        MultipleTypeAdapter adapter = new MultipleTypeAdapter(this, multiEmployees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
