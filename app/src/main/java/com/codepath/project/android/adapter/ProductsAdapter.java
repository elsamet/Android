package com.codepath.project.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.codepath.project.android.R;
import com.codepath.project.android.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by anmallya on 11/11/2016.
 */

public class ProductsAdapter extends
        RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> mProducts;
    private Context mContext;

    public ProductsAdapter(Context context, List<Product> products) {
        mProducts = products;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductNAme;
        public ImageView ivProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductNAme = (TextView) itemView.findViewById(R.id.tv_product_name);
            ivProductImage = (ImageView) itemView.findViewById(R.id.iv_product);
        }
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder viewHolder, int position) {
        Product product = mProducts.get(position);
        TextView tvProductName = viewHolder.tvProductNAme;
        tvProductName.setText(product.getName());
        ImageView ivProductImage = viewHolder.ivProductImage;
        Picasso.with(getContext()).load(product.getImageUrl()).into(ivProductImage);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}