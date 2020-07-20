package com.dat.yecxanh.presenter;

import android.content.Context;

import com.dat.yecxanh.model.MultiEmployee;

import java.util.ArrayList;

public class MultiplePresenter {

    private Context context;
    private MultipleListener listener;

    public MultiplePresenter(Context context) {
        this.context = context;
    }

    public void createList() {
        ArrayList<MultiEmployee> employees = new ArrayList<>();

        MultiEmployee employee = new MultiEmployee();
        employee.setName("Robert");
        employee.setAddress("New York");
        employee.setPhone("+8878447");
        employees.add(employee);

        employee = new MultiEmployee();
        employee.setName("Tom");
        employee.setAddress("California");
        employee.setEmail("tom_frank@gmail.com");
        employees.add(employee);

        employee = new MultiEmployee();
        employee.setName("Smith");
        employee.setAddress("Philadelphia");
        employee.setEmail("smith_carrol@gmail.com");
        employees.add(employee);

        employee = new MultiEmployee();
        employee.setName("Ryan");
        employee.setAddress("Canada");
        employee.setPhone("+9303839");
        employees.add(employee);

        employee = new MultiEmployee();
        employee.setName("Mark");
        employee.setAddress("Boston");
        employee.setEmail("mark_walmerd@gmail.com");
        employees.add(employee);

        employee = new MultiEmployee();
        employee.setName("Adam");
        employee.setAddress("Brooklyn");
        employee.setPhone("+4848484");
        employees.add(employee);

        employee = new MultiEmployee();
        employee.setName("Kevin");
        employee.setAddress("New Jersey");
        employee.setPhone("+9399393");
        employees.add(employee);

        if (listener != null) {
            listener.onGetEmployee(employees);
        }

    }

    public void setListener(MultipleListener listener) {
        this.listener = listener;
    }

    public interface MultipleListener {
        void onGetEmployee(ArrayList<MultiEmployee> multiEmployees);
    }
}
