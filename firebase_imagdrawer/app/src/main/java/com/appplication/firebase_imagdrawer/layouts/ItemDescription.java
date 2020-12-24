package com.appplication.firebase_imagdrawer.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appplication.firebase_imagdrawer.NoInternetActivity;
import com.appplication.firebase_imagdrawer.R;
import com.appplication.firebase_imagdrawer.sharedprefarences.PrefManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;

public class ItemDescription extends AppCompatActivity {

    public static String imagebackgroundurl;

    CardView cardView;

    String id;

    ImageView movieimage,background,down,shadow,shadow2,gradientShadow;
    ImageButton btn;
    String favStatus,image;
    ImageView availableonimage;
    int count;
    LikeButton favbtn;

    WebView webView;
    ScrollView scrollView;

    private AdView mAdView;

    TextView movieTitle,genre,time_or_seasons,imdb_rating,like_percent,description,cast,available;
    String title,gen,time,imdb,likepercent,descrip,fullcast,trailerlink,availableon;
    Button watch_trailer;
        ImageView backbtn;


    private ArrayList<String> al = new ArrayList<>();
    int len;

    ProgressDialog progressDialog;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description2);

        checkInternet();

        image = getIntent().getStringExtra("image");
        imagebackgroundurl = getIntent().getStringExtra("imagebackground");
        trailerlink = getIntent().getStringExtra("trailer");
        title = getIntent().getStringExtra("name");
        gen = getIntent().getStringExtra("genre");
        time = getIntent().getStringExtra("time");
        descrip = getIntent().getStringExtra("description");
        fullcast = getIntent().getStringExtra("cast");
        imdb = getIntent().getStringExtra("ratings");
        likepercent = getIntent().getStringExtra("fav");
        availableon = getIntent().getStringExtra("availableon");


        gradientShadow = findViewById(R.id.gradient_shadow);
        backbtn = findViewById(R.id.backbtn);
        cardView = findViewById(R.id.cardView);
        available = findViewById(R.id.available);
        movieimage = findViewById(R.id.MovieImage);
        movieTitle = findViewById(R.id.movietitle);
        genre = findViewById(R.id.genre);
        time_or_seasons = findViewById(R.id.time_or_seasons);
        imdb_rating = findViewById(R.id.imdb_rating);
        like_percent = findViewById(R.id.likepercent);
        description = findViewById(R.id.description);
        cast=findViewById(R.id.cast);
        watch_trailer =findViewById(R.id.watch_trailer_Btn);
        btn = findViewById(R.id.expand_collapse);
        shadow2= findViewById(R.id.shadow2);
        favbtn = findViewById(R.id.favbtn);
        scrollView = findViewById(R.id.scrollable);

        movieTitle.setText(title);
        genre.setText(gen);
        time_or_seasons.setText(time);
        like_percent.setText(likepercent);
        imdb_rating.setText(imdb);
        cast.setText(fullcast);
        //available.setText(availableon);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        mAdView = findViewById(R.id.adView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
                        .build());
        mAdView = findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad (LoadAdError adError){
                final AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        });


        //update for next version

        //webView = findViewById(R.id.webView);
        //webView.loadDataWithBaseURL(null, trailerlink, "text/html", "UTF-8", null);

        availableonimage = findViewById(R.id.availableonimage);

        if(availableon.equals("Netflix"))
            availableonimage.setImageResource(R.drawable.netflix);
        else if(availableon.equals("Prime Video"))
            availableonimage.setImageResource(R.drawable.primevideo);
        else if(availableon.equals("Apple TV+"))
            availableonimage.setImageResource(R.drawable.appletvplus);
        else if(availableon.equals("Hulu"))
            availableonimage.setImageResource(R.drawable.hulu);
        else if(availableon.equals("Disney + HotStar"))
            availableonimage.setImageResource(R.drawable.disneyhostarr);
        else if(availableon.equals("Alt Balaji"))
            availableonimage.setImageResource(R.drawable.altbalajii);
        else
            available.setText(availableon);




        final ArrayList<String> al = new ArrayList<>();



        final ExpandableTextView textView  = findViewById(R.id.expand_text_view);
        textView.setText(descrip);
        textView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
                if(isExpanded){
                    shadow2.setVisibility(View.GONE);
                }
                else{
                    shadow2.setVisibility(View.VISIBLE);
                }
            }
        });


        gradientShadow.getLayoutParams().height=getScreenWidth();

        Picasso.get().load(imagebackgroundurl).resize(getScreenWidth(), getScreenWidth()).centerInside() .into(movieimage, new Callback() {
            @Override
            public void onSuccess() {
                gradientShadow.setMinimumHeight(getScreenWidth());
                progressDialog.setCancelable(true);
                progressDialog.dismiss();
            }

            @Override
            public void onError(Exception e) {
                //Toast.makeText(ItemDescription.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });





        if(!new PrefManager(this).firstcheck()){
            count=1;
            new PrefManager(this).savecount(count);
            new PrefManager(this).createtitle(count,title);
            new PrefManager(this).create(count);
        }
        else if(!new PrefManager(this).check(title)){
            count = new PrefManager(this).getcount();
            count=count+1;
            new PrefManager(this).savecount(count);
            new PrefManager(this).createtitle(count,title);
            new PrefManager(this).create(count);
        }
        else{
            count=new PrefManager(this).getcountexists(title);
        }



        if(new PrefManager(this).returnstate(count).equals("1"))
            favbtn.setLiked(true);
        else if(new PrefManager(this).returnstate(count).equals("0"))
            favbtn.setLiked(false);


        favbtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                if(new PrefManager(ItemDescription.this).returnstate(count).equals("0")){
                    favStatus="1";
                    uploaddata();
                    //Toast.makeText(ItemDescription.this, ""+favStatus+" "+count, Toast.LENGTH_SHORT).show();
                }
                else if(new PrefManager(ItemDescription.this).returnstate(count).equals("1")){
                    favStatus="0";
                    uploaddata();
                    //Toast.makeText(ItemDescription.this, ""+favStatus+" "+count, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                if(new PrefManager(ItemDescription.this).returnstate(count).equals("0")){
                    favStatus="1";
                    uploaddata();
                    //Toast.makeText(ItemDescription.this, ""+favStatus+" "+count, Toast.LENGTH_SHORT).show();
                }
                else if(new PrefManager(ItemDescription.this).returnstate(count).equals("1")){
                    favStatus="0";
                    uploaddata();
                    //Toast.makeText(ItemDescription.this, ""+favStatus+" "+count, Toast.LENGTH_SHORT).show();
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        watch_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(trailerlink);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });


    }

    protected void onResume()
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    private void uploaddata() {
        SharedPreferences sharedPreferences = getSharedPreferences("title"+String.valueOf(count),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(favStatus.equals("1")){
            editor.putString("title",title);
            editor.putString("imagebackground",imagebackgroundurl);
            editor.putString("image",image);
            editor.putString("ratings",imdb);
            editor.putString("time",time);
            editor.putString("available",availableon);
            editor.putString("like",likepercent);
            editor.putString("genre",gen);
            editor.putString("trailer",trailerlink);
            editor.putString("description",descrip);
            editor.putString("cast",fullcast);
        }
        editor.putString("favstatus", favStatus);
        editor.commit();

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
            Intent intent = new Intent(ItemDescription.this, NoInternetActivity.class);
            ItemDescription.this.startActivity(intent);
        }

    }


}