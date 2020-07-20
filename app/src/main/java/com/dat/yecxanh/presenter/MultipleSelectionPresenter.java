package com.dat.yecxanh.presenter;

import android.content.Context;

import com.dat.yecxanh.model.Employee;

import java.util.ArrayList;

public class MultipleSelectionPresenter {

    private Context context;
    private MultipleListener listener;

    public MultipleSelectionPresenter(Context context) {
        this.context = context;
    }

    public void createList() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Employee employee = new Employee();
            employee.setName("Employee " + (i + 1));

            // for example to show at least one selection
            if (i == 0) {
                employee.setChecked(true);
            }
            //

            employees.add(employee);

            if (listener != null) {
                listener.onGetEmployee(employees);
            }
        }
    }


    public void setListener(MultipleListener listener) {
        this.listener = listener;
    }

    public interface MultipleListener {
        void onGetEmployee(ArrayList<Employee> Employees);
    }
}
