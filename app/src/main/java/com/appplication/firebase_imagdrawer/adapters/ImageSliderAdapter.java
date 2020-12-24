package com.appplication.firebase_imagdrawer.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.appplication.firebase_imagdrawer.layouts.ItemDescription;
import com.appplication.firebase_imagdrawer.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder> {

    Context context;
    int setTotalCount;
    String Imagelink;
    int width,height;


    public ImageSliderAdapter(Context context,int setTotalCount,int width,int height) {
        this.setTotalCount=setTotalCount;
        this.context = context;
        this.width = width;
        this.height = height;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout,parent,false);
        return new SliderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SliderViewHolder viewHolder, final int position) {
        FirebaseDatabase.getInstance().getReference("imageslider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                switch(position){
                    case 0:
                        Imagelink = snapshot.child("0").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 1:
                        Imagelink = snapshot.child("1").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 2:
                        Imagelink = snapshot.child("2").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 3:
                        Imagelink = snapshot.child("3").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 4:
                        Imagelink = snapshot.child("4").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 5:
                        Imagelink = snapshot.child("5").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 6:
                        Imagelink = snapshot.child("6").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 7:
                        Imagelink = snapshot.child("7").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 8:
                        Imagelink = snapshot.child("8").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                    case 9:
                        Imagelink = snapshot.child("9").child("imageslider").getValue().toString();
                        Glide.with(viewHolder.itemView).load(Imagelink).override(width,height).centerCrop().into(viewHolder.sliderImageView);
                        break;
                }
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, ItemDescription.class);
                        intent.putExtra("name",snapshot.child(String.valueOf(position)).child("name").getValue().toString());
                        intent.putExtra("image",snapshot.child(String.valueOf(position)).child("image").getValue().toString());
                        intent.putExtra("imagebackground",snapshot.child(String.valueOf(position)).child("imagebackground").getValue().toString());
                        intent.putExtra("ratings",snapshot.child(String.valueOf(position)).child("ratings").getValue().toString());
                        intent.putExtra("genre",snapshot.child(String.valueOf(position)).child("genre").getValue().toString());
                        intent.putExtra("cast",snapshot.child(String.valueOf(position)).child("cast").getValue().toString());
                        intent.putExtra("description",snapshot.child(String.valueOf(position)).child("description").getValue().toString());
                        intent.putExtra("fav",snapshot.child(String.valueOf(position)).child("fav").getValue().toString());
                        intent.putExtra("time",snapshot.child(String.valueOf(position)).child("time").getValue().toString());
                        intent.putExtra("trailer", snapshot.child(String.valueOf(position)).child("trailer").getValue().toString());
                        intent.putExtra("availableon", snapshot.child(String.valueOf(position)).child("availableon").getValue().toString());
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getCount() {
        return setTotalCount;
    }
}

class SliderViewHolder extends SliderViewAdapter.ViewHolder{
    ImageView sliderImageView;
    View itemView;
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public SliderViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        sliderImageView = itemView.findViewById(R.id.imageView);
        sliderImageView.getLayoutParams().height= (getScreenWidth()/16)*9;
        sliderImageView.getLayoutParams().width= getScreenWidth();
    }
}
