package com.dat.yecxanh.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat.yecxanh.R;
import com.dat.yecxanh.adapter.ProductFilterRecyclerViewAdapter;
import com.dat.yecxanh.base.BaseActivity;
import com.dat.yecxanh.model.FilterModel;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity implements ProductFilterRecyclerViewAdapter.ListTransAdapterListener {

    private RecyclerView recyclerView;

    private TextView filter_txt;

    private List<FilterModel> modelList = new ArrayList<FilterModel>();

    @Override
    protected String getTitleScreen() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.brands_lst);
        filter_txt = (TextView) findViewById(R.id.filter_txt);
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        ProductFilterRecyclerViewAdapter adapter = new ProductFilterRecyclerViewAdapter(this, getBrands());
        adapter.setHiddenCheckBox(true);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void afteriInitView() {
        //RecyclerView layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void initListener() {

    }

    private List<FilterModel> getBrands() {
        modelList.add(new FilterModel("Adidas", 1323, true));
        modelList.add(new FilterModel("Nike", 2321, false));
        modelList.add(new FilterModel("Reebok", 3221, true));
        modelList.add(new FilterModel("Boss", 1323, false));
        modelList.add(new FilterModel("Wrangler", 5651, true));
        modelList.add(new FilterModel("Lee", 1898, false));
        modelList.add(new FilterModel("Levis", 1655, false));
        modelList.add(new FilterModel("Polo", 8881, false));
        modelList.add(new FilterModel("Tommy Hil", 167, false));
        modelList.add(new FilterModel("Nautica", 177, false));
        modelList.add(new FilterModel("Gas", 14, false));
        modelList.add(new FilterModel("Diesel", 1555, false));
        modelList.add(new FilterModel("Gap", 551, false));
        modelList.add(new FilterModel("Flying Machine", 199, false));
        modelList.add(new FilterModel("Pepe Jeans", 981, true));
        modelList.add(new FilterModel("Jack Jones", 561, false));
        modelList.add(new FilterModel("Puma", 1832, false));

        return modelList;
    }

    @Override
    public void onChangedCheckbox(FilterModel filterModel) {
        for (FilterModel filterModel1 : modelList) {
            if (filterModel1.getName().equals(filterModel.getName())) {
                filterModel1.setSelected(!filterModel1.isSelected());
            }
        }
        showTotalAmoutText();
    }

    private void showTotalAmoutText() {
        int total = 0;
        for (FilterModel filterModel : modelList) {
            if (filterModel.isSelected()) {
                total += filterModel.getProductCount();
            }
        }
        filter_txt.setText(String.valueOf(total));
        Log.d("TestActivity", "total = " + total);
        Toast.makeText(getApplicationContext(), String.valueOf(total), Toast.LENGTH_LONG).show();
    }
}
