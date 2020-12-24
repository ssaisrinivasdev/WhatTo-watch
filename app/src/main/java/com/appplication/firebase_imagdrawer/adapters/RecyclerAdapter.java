package com.appplication.firebase_imagdrawer.adapters;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.appplication.firebase_imagdrawer.layouts.ItemDescription;
import com.appplication.firebase_imagdrawer.helpers.Messages;
import com.appplication.firebase_imagdrawer.R;
import com.appplication.firebase_imagdrawer.layouts.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String Tag = "RecyclerView";

    private Context mContext;
    private ArrayList<Messages> messagesList;

    public RecyclerAdapter(Context mContext, ArrayList<Messages> messagesList) {

        this.mContext = mContext;
        this.messagesList = messagesList;
    }


    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item,parent,false);


        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.ViewHolder holder, final int position) {


        //textview
        holder.textView.setText(messagesList.get(position).getName());
        holder.ratings.setText(messagesList.get(position).getFav());
        holder.genre.setText(messagesList.get(position).getGenre());
        switch (messagesList.get(position).getAvailableon()) {
            case "Netflix":
                Glide.with(mContext).load(R.drawable.netflix).into(holder.app);
                break;
            case "Prime Video":
                Glide.with(mContext).load(R.drawable.primevideo).into(holder.app);
                break;
            case "Apple TV+":
                Glide.with(mContext).load(R.drawable.appletvplus).into(holder.app);
                break;
            case "Hulu":
                Glide.with(mContext).load(R.drawable.hulu).into(holder.app);
                break;
            case "Disney + HotStar":
                Glide.with(mContext).load(R.drawable.disneyhostarr).into(holder.app);
                break;
            case "Alt Balaji":
                Glide.with(mContext).load(R.drawable.altbalajii).into(holder.app);
                break;

        }




        holder.textView.post(new Runnable() {
            @Override
            public void run() {
                if(holder.textView.getLineCount()>1){
                    holder.text.setVisibility(View.GONE);
                }
            }
        });


        //imageview
        Glide.with(mContext)
                .load(messagesList.get(position).getImageUrl())
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDescription.class);
                intent.putExtra("image",messagesList.get(position).getImageUrl());
                intent.putExtra("imagebackground",messagesList.get(position).getImagebackground());
                intent.putExtra("ratings",messagesList.get(position).getRatings());
                intent.putExtra("genre",messagesList.get(position).getGenre());
                intent.putExtra("cast",messagesList.get(position).getCast());
                intent.putExtra("name",messagesList.get(position).getName());
                intent.putExtra("description",messagesList.get(position).getDescription());
                intent.putExtra("fav",messagesList.get(position).getFav());
                intent.putExtra("time",messagesList.get(position).getTime());
                intent.putExtra("trailer",messagesList.get(position).getTrailer());
                intent.putExtra("availableon",messagesList.get(position).getAvailableon());

                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return messagesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        //Widgets
        ImageView imageView;
        TextView textView;
        TextView ratings, genre,text;
        ImageView app;
        //ProgressDialog progressDialog;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            genre = itemView.findViewById(R.id.genre);
            ratings = itemView.findViewById(R.id.rating);
            text = itemView.findViewById(R.id.texxt);
            app = itemView.findViewById(R.id.app);

        }
    }


}
