package com.sm.banitro.ui.editdialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sm.banitro.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCategoryAdapter extends RecyclerView.Adapter<EditCategoryAdapter.ViewHolder> {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private ArrayList<String> categories;

    // ********************************************************************************
    // Constructor

    public EditCategoryAdapter(Interaction interaction) {
        this.interaction = interaction;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public EditCategoryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_category, parent, false);
        return new EditCategoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EditCategoryAdapter.ViewHolder holder, int position) {
        String category = categories.get(position);
        holder.onBind(category);
    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    // ********************************************************************************
    // Method

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    // ********************************************************************************
    // Inner Class

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_category_tv_name)
        TextView tvName;
        @BindView(R.id.list_item_category_cb_check)
        CheckBox cbCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(String category) {
            tvName.setText(category);
            cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    interaction.onCheckedChanged(getAdapterPosition(), isChecked);
                }
            });
        }
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void onCheckedChanged(int condition, boolean isChecked);
    }
}