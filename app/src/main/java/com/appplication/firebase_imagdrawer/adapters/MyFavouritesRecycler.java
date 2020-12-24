package com.appplication.firebase_imagdrawer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appplication.firebase_imagdrawer.helpers.Favmessages;
import com.appplication.firebase_imagdrawer.layouts.ItemDescription;
import com.appplication.firebase_imagdrawer.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyFavouritesRecycler extends RecyclerView.Adapter<MyFavouritesRecycler.ViewHolder> {

    private static final String Tag = "RecyclerView";

    private Context mContext;
    private ArrayList<Favmessages> al;
    private String Description;

    public MyFavouritesRecycler(Context context, ArrayList<Favmessages> al) {
        this.mContext = context;
        this.al = al;

    }

    @NonNull
    @Override
    public MyFavouritesRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.landscapelist,parent,false);


        return new MyFavouritesRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFavouritesRecycler.ViewHolder holder, final int position) {

        holder.textView.setText(al.get(position).getName());
        holder.genre.setText(al.get(position).getGenre());
        holder.description.setText(al.get(position).getDescription());
        holder.fav.setText(al.get(position).getFav());

        //imageview
        Glide.with(mContext)
                .load(al.get(position).getImageUrl())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDescription.class);
                intent.putExtra("image",al.get(position).getImageUrl());
                intent.putExtra("imagebackground",al.get(position).getImagebackground());
                intent.putExtra("ratings",al.get(position).getRatings());
                intent.putExtra("genre",al.get(position).getGenre());
                intent.putExtra("cast",al.get(position).getCast());
                intent.putExtra("name",al.get(position).getName());
                intent.putExtra("description",al.get(position).getDescription());
                intent.putExtra("fav",al.get(position).getFav());
                intent.putExtra("time",al.get(position).getTime());
                intent.putExtra("trailer",al.get(position).getTrailer());
                intent.putExtra("availableon",al.get(position).getAvailableon());


                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        //Widgets
        ImageView imageView;
        TextView textView,genre,description,fav;

        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            genre = itemView.findViewById(R.id.genre);
            description = itemView.findViewById(R.id.description);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            fav = itemView.findViewById(R.id.rating);

            Animation translate_anim = AnimationUtils.loadAnimation(mContext, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
