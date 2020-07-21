package com.dat.yecxanh.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat.yecxanh.R;
import com.dat.yecxanh.adapter.MultiAdapter;
import com.dat.yecxanh.base.BaseActivity;
import com.dat.yecxanh.model.Employee;
import com.dat.yecxanh.presenter.MultipleSelectionPresenter;

import java.util.ArrayList;

public class MultipleSelectionActivity extends BaseActivity implements MultipleSelectionPresenter.MultipleListener {

    private RecyclerView recyclerView;

    private MultiAdapter adapter;

    private AppCompatButton btnGetSelected;

    @Override
    protected String getTitleScreen() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_multiple_selection;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnGetSelected = (AppCompatButton) findViewById(R.id.btnGetSelected);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        onGetEmployees();
    }

    @Override
    protected void afteriInitView() {

    }

    @Override
    protected void initListener() {
        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getName());
                        stringBuilder.append("\n");
                    }
                    showToast(stringBuilder.toString().trim());
                } else {
                    showToast("No Selection");
                }
            }
        });
    }

    private void onGetEmployees() {
        MultipleSelectionPresenter multipleSelectionPresenter = new MultipleSelectionPresenter(this);
        multipleSelectionPresenter.setListener(this);
        multipleSelectionPresenter.createList();
    }

    @Override
    public void onGetEmployee(ArrayList<Employee> Employees) {
        Log.d("MultipleSelection", "Employees" + Employees.get(0).getName());
        adapter = new MultiAdapter(this, Employees);
        recyclerView.setAdapter(adapter);
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
