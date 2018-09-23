package com.sm.banitro.ui.home.incoming;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.util.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomingAdapter extends RecyclerView.Adapter<IncomingAdapter.ViewHolder> {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private ArrayList<Product> products;

    // Data Type
    private int productPosition;

    // ********************************************************************************
    // Constructor

    public IncomingAdapter(Interaction interaction) {
        this.interaction = interaction;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_incoming, parent, false);
        return new IncomingAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IncomingAdapter.ViewHolder holder, int position) {
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
        @BindView(R.id.list_item_incoming_tv_product_name)
        TextView tvProductName;
        @BindView(R.id.list_item_incoming_tv_product_category)
        TextView tvProductCategory;
        @BindView(R.id.list_item_incoming_iv_approved)
        ImageView ivApproved;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interaction.goToDetail(products.get(getAdapterPosition()));
                }
            });
        }

        public void onBind(Product product) {
            productPosition = product.getPosition();
            tvProductName.setText(product.getName());
            tvProductCategory.setText(product.getCategory().getName());
            if (productPosition == Constant.POSITION_APPROVED) {
                ivApproved.setImageResource(R.drawable.baseline_check_white_48);
                ivApproved.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.colorOk),
                        android.graphics.PorterDuff.Mode.MULTIPLY);
            } else if (productPosition == Constant.POSITION_APPROVED_NOT) {
                ivApproved.setImageResource(R.drawable.baseline_clear_white_48);
                ivApproved.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.colorError),
                        android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void goToDetail(Product product);
    }
}