package com.iteso.sesion9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iteso.sesion9.beans.ItemProduct;
import com.iteso.sesion9.tools.Constant;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private ArrayList<ItemProduct> mDataSet;
    private Context context;
    private int fragment;

    AdapterProduct(int fragment, Context context, ArrayList<ItemProduct> products){
        this.fragment = fragment;
        this.mDataSet = products;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mProductTitle.setText( mDataSet.get(position).getTitle());

        switch(mDataSet.get(holder.getAdapterPosition()).getImage()){
            case Constant.TYPE_MAC:
                holder.mProductImage.setImageResource(R.drawable.mac); break;
            case Constant.TYPE_ALIENWARE:
                holder.mProductImage.setImageResource(R.drawable.alienware); break;
            case Constant.TYPE_SHEETS:
                holder.mProductImage.setImageResource(R.drawable.sheets); break;
            case Constant.TYPE_PILLOW:
                holder.mProductImage.setImageResource(R.drawable.pillows); break;
            case Constant.TYPE_REFRIGERATOR:
                holder.mProductImage.setImageResource(R.drawable.refrigerator); break;
            case Constant.TYPE_MICRO:
                holder.mProductImage.setImageResource(R.drawable.micro); break;
        }

        holder.mEventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, mDataSet.get(position).toString(),
                //        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, ActivityProduct.class);
                intent.putExtra(Constant.EXTRA_PRODUCT, mDataSet.get(holder.getAdapterPosition()));
                intent.putExtra(Constant.EXTRA_FRAGMENT, fragment);
                ((MainActivity) context).startActivityForResult(intent, Constant.ACTIVITY_DETAIL);
            }
        });

        holder.mProductStore.setText(mDataSet.get(position).getStore().getName());
        holder.mProductLocation.setText(
                mDataSet.get(position).getStore().getCity().getName() + ", Jalisco");
        holder.mProductPhone.setText(mDataSet.get(position).getStore().getPhone());

        holder.mProductPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + mDataSet.get(position).getStore().getPhone()));
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        Button mDetail;
        TextView mProductTitle;
        TextView mProductStore;
        TextView mProductLocation;
        TextView mProductPhone;
        ImageView mProductImage;
        ImageView mProductThumbnail;
        RelativeLayout mEventLayout;

        ViewHolder(View v) {
            super(v);
            mEventLayout = v.findViewById(R.id.item_product_layout);
            mDetail = v.findViewById(R.id.item_product_detail);
            mProductTitle = v.findViewById(R.id.item_product_title);
            mProductStore = v.findViewById(R.id.item_product_store);
            mProductLocation = v.findViewById(R.id.item_product_location);
            mProductPhone = v.findViewById(R.id.item_product_phone);
            mProductImage = v.findViewById(R.id.item_product_image);
            mProductThumbnail = v.findViewById(R.id.item_product_thumbnail);
        }
    }



}