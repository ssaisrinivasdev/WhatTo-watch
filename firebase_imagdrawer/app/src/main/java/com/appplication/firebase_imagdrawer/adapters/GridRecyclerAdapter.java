package com.appplication.firebase_imagdrawer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appplication.firebase_imagdrawer.R;
import com.appplication.firebase_imagdrawer.helpers.Grid;
import com.appplication.firebase_imagdrawer.helpers.Messages;
import com.appplication.firebase_imagdrawer.layouts.ItemDescription;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridRecyclerAdapter extends  RecyclerView.Adapter<GridRecyclerAdapter.ViewHolder>{
    private static final String Tag = "RecyclerView";

    private Context mContext;
    private ArrayList<Grid> gridl;

    public GridRecyclerAdapter(Context mContext, ArrayList<Grid> gridl) {

        this.mContext = mContext;
        this.gridl = gridl;
    }


    @NonNull
    @Override
    public GridRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridlayout,parent,false);
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull final GridRecyclerAdapter.ViewHolder holder, final int position) {
        //textview
        holder.textView.setText(gridl.get(position).getName());
        holder.ratings.setText(gridl.get(position).getFav());
        holder.genre.setText(gridl.get(position).getGenre());
        switch (gridl.get(position).getAvailableon()) {
            case "Netflix":
                holder.app.setImageResource(R.drawable.netflix);
                break;
            case "Prime Video":
                holder.app.setImageResource(R.drawable.primevideo);
                break;
            case "Apple TV+":
                holder.app.setImageResource(R.drawable.appletvplus);
                break;
            case "Hulu":
                holder.app.setImageResource(R.drawable.hulu);
                break;
            case "Disney + HotStar":
                holder.app.setImageResource(R.drawable.disneyhostarr);
                break;
            case "Alt Balaji":
                holder.app.setImageResource(R.drawable.altbalajii);
                break;
        }

        //imageview
        Glide.with(mContext)
                .load(gridl.get(position).getImageUrl())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDescription.class);
                intent.putExtra("image",gridl.get(position).getImageUrl());
                intent.putExtra("imagebackground",gridl.get(position).getImagebackground());
                intent.putExtra("ratings",gridl.get(position).getRatings());
                intent.putExtra("genre",gridl.get(position).getGenre());
                intent.putExtra("cast",gridl.get(position).getCast());
                intent.putExtra("name",gridl.get(position).getName());
                intent.putExtra("description",gridl.get(position).getDescription());
                intent.putExtra("fav",gridl.get(position).getFav());
                intent.putExtra("time",gridl.get(position).getTime());
                intent.putExtra("trailer",gridl.get(position).getTrailer());
                intent.putExtra("availableon",gridl.get(position).getAvailableon());

                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return gridl.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        //Widgets
        ImageView imageView;
        TextView textView;
        TextView ratings, genre;
        ImageView app;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            genre = itemView.findViewById(R.id.genre);
            ratings = itemView.findViewById(R.id.rating);
            app = itemView.findViewById(R.id.app);
        }
    }


}
