package com.dat.yecxanh.model;

public class FilterModel {
    private String name;

    private int productCount;

    private boolean isSelected;

    public FilterModel(String name, int productCount, boolean isSelected) {
        this.name = name;
        this.productCount = productCount;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
