package com.appplication.firebase_imagdrawer.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appplication.firebase_imagdrawer.NoInternetActivity;
import com.appplication.firebase_imagdrawer.R;
import com.appplication.firebase_imagdrawer.adapters.MyFavouritesRecycler;
import com.appplication.firebase_imagdrawer.helpers.Favmessages;
import com.appplication.firebase_imagdrawer.sharedprefarences.PrefManager;

import java.util.ArrayList;


public class myFavourites extends AppCompatActivity {

    private ArrayList<Favmessages> al;

    RecyclerView recyclerView;
    private MyFavouritesRecycler recyclerAdapter;

    ImageView nothingtoshow;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourites);


        checkInternet();

        nothingtoshow = findViewById(R.id.nothingtoshow);
        textView = findViewById(R.id.nothing);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        //new GridLayoutManager(this, numberOfColumns)

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        int count = new PrefManager(this).getcount();
        //Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
        ClearAll();

        al = new ArrayList<>();
        int flag=0;

        ClearAll();

            for (int i =0; i <= count + 1; i++) {
                SharedPreferences sharedPreferences = getSharedPreferences("title" + i, MODE_PRIVATE);
                String str = sharedPreferences.getString("favstatus", "");
                if (str.equals("1")) {
                    //Toast.makeText(this, "" + count, Toast.LENGTH_SHORT).show();
                    Favmessages favmessages = new Favmessages();
                    favmessages.setName(sharedPreferences.getString("title", ""));
                    favmessages.setAvailableon(sharedPreferences.getString("available", ""));
                    favmessages.setCast(sharedPreferences.getString("cast", ""));
                    favmessages.setDescription(sharedPreferences.getString("description", ""));
                    favmessages.setFav(sharedPreferences.getString("like", ""));
                    favmessages.setImageUrl(sharedPreferences.getString("image", ""));
                    favmessages.setImagebackground(sharedPreferences.getString("imagebackground", ""));
                    favmessages.setRatings(sharedPreferences.getString("ratings", ""));
                    favmessages.setTrailer(sharedPreferences.getString("trailer", ""));
                    favmessages.setTime(sharedPreferences.getString("time", ""));
                    favmessages.setGenre(sharedPreferences.getString("genre", ""));
                    al.add(favmessages);
                    recyclerAdapter = new MyFavouritesRecycler(getApplicationContext(), al);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();

                    flag=1;

                }

                if(flag==0){
                    nothingtoshow.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
                else{
                    nothingtoshow.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
        }
    }

    private void ClearAll() {
        if(al != null) {
            al.clear();

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        al = new ArrayList<>();
    }
    public void checkInternet()
    {
        final ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected) {
            //Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myFavourites.this, NoInternetActivity.class);
            myFavourites.this.startActivity(intent);
        }

    }
}