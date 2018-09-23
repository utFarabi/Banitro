package com.sm.banitro.ui.home.recent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private ArrayList<Product> products;

    // ********************************************************************************
    // Constructor

    public RecentAdapter(Interaction interaction) {
        this.interaction = interaction;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recent, parent, false);
        return new RecentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.onBind(product);
    }

    @Override
    public int getItemCount() {
        if (products == null) {
            return 0;
        }
        return products.size();
    }

    // ********************************************************************************
    // Method

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    // ********************************************************************************
    // Inner Class

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_recent_tv_product_name)
        TextView tvProductName;
        @BindView(R.id.list_item_recent_tv_product_category)
        TextView tvProductCategory;
        @BindView(R.id.list_item_recent_iv_is_replied)
        ImageView ivIsReplied;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interaction.setProductFromAdapterToRecentFragment(products.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Product product=products.get(getAdapterPosition());
                    if (!product.isReplied()) {
                        interaction.openDeleteDialogFragment(product);
                    }
                    return false;
                }
            });
        }

        public void onBind(Product product) {
            tvProductName.setText(product.getName());
            tvProductCategory.setText(product.getCategory().getName());
            if (product.isReplied()) {
                ivIsReplied.setImageResource(R.drawable.outline_chat_white_36);
            } else {
                ivIsReplied.setImageResource(R.drawable.outline_chat_bubble_outline_white_36);
            }
        }
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void setProductFromAdapterToRecentFragment(Product product);

        void openDeleteDialogFragment(Product product);
    }
}