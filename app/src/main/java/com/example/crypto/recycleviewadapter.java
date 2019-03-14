package com.example.crypto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recycleviewadapter extends RecyclerView.Adapter<recycleviewadapter.ViewHolder>{

    private static final String TAG = "recycleviewadapter";

    private ArrayList<String> mImageNames =new ArrayList<>();
    private ArrayList<String> mImages =new ArrayList<>();
    private ArrayList<String> mPrices =new ArrayList<>();

    private Context mContext;

    public recycleviewadapter( Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages, ArrayList<String> mPrices) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.mPrices=mPrices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlistitem, parent, false);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);

        holder.coinName.setText(mImageNames.get(position));

        holder.coinPrice.setText("$"+mPrices.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on"+ mImageNames.get(position));

                Toast.makeText(mContext, mPrices.get(position),Toast.LENGTH_SHORT).show();

                
                
            }
        });
    }

    @Override
    public int getItemCount() {

        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView coinName;
        RelativeLayout parentLayout;
        TextView coinPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
            coinName=itemView.findViewById(R.id.coinCard);
            parentLayout= itemView.findViewById(R.id.parent_layout);
            coinPrice= itemView.findViewById(R.id.coinCardPrice);

        }
    }

}
