package com.dat.yecxanh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dat.yecxanh.R;
import com.dat.yecxanh.model.FilterModel;

import java.util.List;

public class ProductFilterRecyclerViewAdapter extends RecyclerView.Adapter<ProductFilterRecyclerViewAdapter.ViewHolder> {

    private Context context;

    private List<FilterModel> filterModelList;

    private boolean isHiddenCheckBox = false;

    private ListTransAdapterListener listener;

    public void setListener(ListTransAdapterListener listener) {
        this.listener = listener;
    }

    public void setHiddenCheckBox(boolean hiddenCheckBox) {
        isHiddenCheckBox = hiddenCheckBox;
    }

    public interface ListTransAdapterListener {
        void onChangedCheckbox(FilterModel filterModel);
    }

    public ProductFilterRecyclerViewAdapter(Context context, List<FilterModel> filterModelList) {
        this.context = context;
        this.filterModelList = filterModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FilterModel filterModel = filterModelList.get(position);
        holder.brandName.setText(filterModel.getName());
        holder.productCount.setText(" " + filterModel.getProductCount());
        holder.selectionState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onChangedCheckbox(filterModel);
                }
            }
        });
        holder.selectionState.setChecked(filterModel.isSelected());
    }

    @Override
    public int getItemCount() {
        return filterModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Context context;

        public TextView brandName;
        public TextView productCount;
        public CheckBox selectionState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = (TextView) itemView.findViewById(R.id.brand_name);
            productCount = (TextView) itemView.findViewById(R.id.product_count);
            selectionState = (CheckBox) itemView.findViewById(R.id.brand_select);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
