package com.sm.banitro.ui.recent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Demand;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.viewHolder> {
    private ArrayList<Demand> demands;
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
        Demand demand = demands.get(position);
        holder.tvProductName.setText(demand.getProductName());
        holder.tvProductCategory.setText(demand.getProductCategory());
    }

    @Override
    public int getItemCount() {
        if (demands == null) {
            return 0;
        }
        return demands.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductCategory;

        viewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.list_item_recent_tv_product_name);
            tvProductCategory = itemView.findViewById(R.id.list_item_recent_tv_product_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interaction.setDemandToRecentProduct(getDemands().get(getAdapterPosition()));
                }
            });
        }
    }

    public ArrayList<Demand> getDemands() {
        return demands;
    }

    public void setDemands(ArrayList<Demand> demands) {
        this.demands = demands;
        notifyDataSetChanged();
    }

    public interface Interaction {

        void setDemandToRecentProduct(Demand demand);
    }
}