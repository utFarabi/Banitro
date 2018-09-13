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

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.viewHolder> {
    private ArrayList<Product> products;
    private Interaction interaction;

    public RecentAdapter(Interaction interaction) {
        this.interaction = interaction;
    }

    @Override
    public RecentAdapter.viewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recent, parent, false);
        return new RecentAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentAdapter.viewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductCategory.setText(product.getCategory().getName());
        if (product.isReplied()) {
            holder.ivIsReplied.setImageResource(R.drawable.outline_chat_white_36);
        } else {
            holder.ivIsReplied.setImageResource(R.drawable.outline_chat_bubble_outline_white_36);
        }
    }

    @Override
    public int getItemCount() {
        if (products == null) {
            return 0;
        }
        return products.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductCategory;
        ImageView ivIsReplied;

        viewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.list_item_recent_tv_product_name);
            tvProductCategory = itemView.findViewById(R.id.list_item_recent_tv_product_category);
            ivIsReplied = itemView.findViewById(R.id.list_item_recent_iv_is_replied);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interaction.setProductToRecentFragment(products.get(getAdapterPosition()));
                }
            });
        }
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public interface Interaction {

        void setProductToRecentFragment(Product product);
    }
}