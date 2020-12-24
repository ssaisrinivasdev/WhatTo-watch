package com.appplication.firebase_imagdrawer.layouts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.appplication.firebase_imagdrawer.NoInternetActivity;
import com.appplication.firebase_imagdrawer.R;
import com.appplication.firebase_imagdrawer.adapters.ImageSliderAdapter;
import com.appplication.firebase_imagdrawer.adapters.RecyclerAdapter;
import com.appplication.firebase_imagdrawer.helpers.Messages;
import com.appplication.firebase_imagdrawer.sharedprefarences.PrefManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    //widgets
    RecyclerView recyclerView,ActionRecyclerView,ThrillerRecyclerView,HorrorRecylerView,KidsRecylerView,Kdramarecyclerview;
    private RecyclerAdapter recyclerAdapter,ActionrecyclerAdapter,ThrillerRecyclerAdapter,HorrorRecylerAdapter,KidsRecylerAdapter,KdramaRecycleradapter;
    //variables
    private ArrayList<Messages> messagesList,ActionList,Thrillerlist,HorrorList,KidsList,Kdramalist;   //Trending
    private Context mContext;
    private static  int i=0;
    private static int total;

    //Toggle
    ToggleButton discover;
    ToggleButton scratch_to_find;

    private static TextView choose;

    private Button spinner;

    private int flag=0;
    //Firebase;
    private DatabaseReference myRef;

    ImageButton dialogue;
    private CardView cardView;

    private static int count=0;

    ProgressDialog progressDialog;

    private ConstraintLayout discoverlayout;
    private ConstraintLayout movieCardslayout;
    //Buttons
    TextView viewalltrending,viewallaction,viewallthriller,viewallhorror,viewallkids,viewallkdrama;

    public static int x;
    public static String imagelink,name;

    Button viewMovie,fav;

    ImageView hiddenfilm;

    Button reload;
    ScratchView scratchView;


    //Title
    TextView textTitle, movietitle;

    String genr= "All";
    String platform ="Any";


    private AdView mAdView;



    //Slider
    SliderView sliderview;
    int TotalCount,width,height;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // "BANNER ADS "
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
        i=0;
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad (LoadAdError adError){
                if(i<4) {
                    final AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    i++;
                }
            }
        });

        final Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);


        //String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        hiddenfilm=findViewById(R.id.hiddenfilm);
        reload=findViewById(R.id.reload);
        choose = findViewById(R.id.choose);

        // Favourites page
        fav=findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav.startAnimation(animFadein);
                Intent intent= new Intent(MainActivity.this, myFavourites.class);
                Context context = view.getContext();
                context.startActivity(intent);
            }
        });

        //New User
        if(!new PrefManager(this).firstcheck()){
            int count=0;
            new PrefManager(this).savecount(count);
            new PrefManager(this).createtitle(count," ");
            new PrefManager(this).create(count);
        }



        //No Internet Page
        checkInternet();




        movietitle = findViewById(R.id.moviename);
        textTitle=findViewById(R.id.textTitle);
        discoverlayout=findViewById(R.id.discoverlayout);
        discover= findViewById(R.id.discover);
        movieCardslayout=findViewById(R.id.movieCards);
        scratch_to_find=findViewById(R.id.scratch_to_find);
        discover.setChecked(true);
        scratch_to_find.setChecked(false);
        spinner = findViewById(R.id.spinner);
        dialogue = findViewById(R.id.choosegenre);
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcustomDialogue();
            }
        });
        dialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcustomDialogue();
            }
        });



/**
        //Choose Category option in Scratch-Card Layer
        List<String> genes = new ArrayList<>();
        genes.add(0,"All");
        genes.add("Action");
        genes.add("Kids");//
        genes.add("Drama");
        genes.add("Horror");//
        genes.add("Fantasy");
        genes.add("Sci-Fi");
        genes.add("Thriller");//
        genes.add("Romance");//
        genes.add("K-Drama");



        ArrayAdapter<String> dataadapter;
        dataadapter = new ArrayAdapter(this,R.layout.spinner_item,genes);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataadapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("All")){
                    genr =",";
                    scratch_to_find.callOnClick();
                }
                else{
                    genr= adapterView.getItemAtPosition(i).toString();
                    scratch_to_find.callOnClick();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

         **/




        if(discover.isChecked()){
            scratch_to_find.setChecked(false);
        }


        String Source =  "Streaming Platform: "+"<b>" +platform+"</b>"  +"<br>"+"Genre: "+"<b>"+genr+"</b>"  ;
        choose.setText(Html.fromHtml(Source));


        //Scratch Card Layer OnClick  ||    Loading Scratch-Card for new
        scratch_to_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(scratchView.isRevealed()||flag==1){
                        scratchView.mask();
                    }
                    reload.setVisibility(View.VISIBLE);
                    flag=0;
                    Random r = new Random();
                    x = r.nextInt(total - 1 + 1);
                    movietitle.setVisibility(View.INVISIBLE);
                    viewMovie.setVisibility(View.INVISIBLE);
                    discover.setChecked(false);
                    scratch_to_find.setChecked(true);
                    textTitle.setText("Movie Cards");
                    discoverlayout.setVisibility(View.GONE);
                    movieCardslayout.setVisibility(View.VISIBLE);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setContentView(R.layout.progress_layout);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );
                    //Getting Data from Firebase for ScratchCard

                if(count<50) {
                    FirebaseDatabase.getInstance().getReference("scratchcard").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot snapshot) {
                            imagelink = snapshot.child(String.valueOf(x)).child("imagebackground").getValue().toString();
                            movietitle.setText(snapshot.child(String.valueOf(x)).child("name").getValue().toString());
                            String gen = snapshot.child(String.valueOf(x)).child("genre").getValue().toString();
                            String plat = snapshot.child(String.valueOf(x)).child("availableon").getValue().toString();


                            if ((gen.contains(genr)||genr.equals("All")) && (plat.contains(platform) || platform.equals("Any"))) {
                                Picasso.get().load(imagelink).into(hiddenfilm, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressDialog.setCancelable(true);
                                        progressDialog.dismiss();
                                        viewMovie.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(MainActivity.this, ItemDescription.class);
                                                intent.putExtra("image", snapshot.child(String.valueOf(x)).child("image").getValue().toString());
                                                intent.putExtra("imagebackground", snapshot.child(String.valueOf(x)).child("imagebackground").getValue().toString());
                                                intent.putExtra("ratings", snapshot.child(String.valueOf(x)).child("ratings").getValue().toString());
                                                intent.putExtra("genre", snapshot.child(String.valueOf(x)).child("genre").getValue().toString());
                                                intent.putExtra("cast", snapshot.child(String.valueOf(x)).child("cast").getValue().toString());
                                                intent.putExtra("name", snapshot.child(String.valueOf(x)).child("name").getValue().toString());
                                                intent.putExtra("description", snapshot.child(String.valueOf(x)).child("description").getValue().toString());
                                                intent.putExtra("fav", snapshot.child(String.valueOf(x)).child("fav").getValue().toString());
                                                intent.putExtra("time", snapshot.child(String.valueOf(x)).child("time").getValue().toString());
                                                intent.putExtra("trailer", snapshot.child(String.valueOf(x)).child("trailer").getValue().toString());
                                                intent.putExtra("availableon", snapshot.child(String.valueOf(x)).child("availableon").getValue().toString());
                                                Context context = view.getContext();
                                                context.startActivity(intent);
                                            }
                                        });
                                        count=0;
                                    }

                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });
                            } else {
                                count++;
                                scratch_to_find.callOnClick();


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //Toast.makeText(mContext, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            count=0;
                            progressDialog.setCancelable(true);
                            progressDialog.dismiss();
                        }
                    });
                }
                else{
                    //Toast.makeText(mContext, "Sorry, Can't Find Your Item\nTry To Find Another...", Toast.LENGTH_SHORT).show();
                    reload.setVisibility(View.GONE);
                    hiddenfilm.setImageResource(R.drawable.ic_baseline_error_24);

                    if(genr.equals("All")){
                        movietitle.setText("Sorry!"+"\nFound Nothing in "+platform+"!\nTry Another Platform");
                    }else{
                        movietitle.setText("Sorry!"+"\nFound Nothing in "+genr+"!\nTry Another Genre");
                    }

                    genr = "all";
                    platform="Any";
                    count=0;
                    scratchView.reveal();
                    progressDialog.setCancelable(true);
                    progressDialog.dismiss();
                    viewMovie.setVisibility(View.GONE);
                }

            }

        });


        reload.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scratch_to_find.callOnClick();

            }
        });


        //Discover Layer OnClick
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    discover.setChecked(true);
                    scratch_to_find.setChecked(false);
                    movieCardslayout.setVisibility(View.GONE);
                    textTitle.setText("Discover");
                    discoverlayout.setVisibility(View.VISIBLE);
            }
        });


        //Scratch To Find   ||  Revealing Scratch
        viewMovie=findViewById(R.id.viewMovie);
        scratchView=findViewById(R.id.scratch);
        scratchView.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {
                scratchView.reveal();
                if(reload.getVisibility()==View.GONE){
                    viewMovie.setVisibility(View.GONE);
                }else {
                    viewMovie.setVisibility(View.VISIBLE);
                }
                movietitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {
                if (percent>=0.2) {
                    flag = 1;
                }
            }
        });


        //View All for Trending Category
        viewalltrending=findViewById(R.id.viewalltrending);
        viewalltrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, grid_layout.class);
                Context context = view.getContext();
                intent.putExtra("value","trending");
                context.startActivity(intent);
            }
        });
        //View All for Action Category
        viewallaction=findViewById(R.id.viewallaction);
        viewallaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, grid_layout.class);
                Context context = view.getContext();
                intent.putExtra("value","action");
                context.startActivity(intent);
            }
        });
        //View All for Thriller Category
        viewallthriller=findViewById(R.id.viewallthriller);
        viewallthriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, grid_layout.class);
                Context context = view.getContext();
                intent.putExtra("value","thriller");
                context.startActivity(intent);
            }
        });
        //View All for Horror Category
        viewallhorror=findViewById(R.id.viewallhorror);
        viewallhorror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, grid_layout.class);
                Context context = view.getContext();
                intent.putExtra("value","horror");
                context.startActivity(intent);
            }
        });
        //View All for K-drama Category
        viewallkdrama=findViewById(R.id.viewallkdrama);
        viewallkdrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, grid_layout.class);
                Context context = view.getContext();
                intent.putExtra("value","kdrama");
                context.startActivity(intent);
            }
        });
        //View All for Kids Category
        viewallkids=findViewById(R.id.viewallkids);
        viewallkids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, grid_layout.class);
                Context context = view.getContext();
                intent.putExtra("value","kids");
                context.startActivity(intent);
            }
        });



        progressDialog = new ProgressDialog(this);

        //Recyclers
        recyclerView = findViewById(R.id.recyclerView);
        ActionRecyclerView = findViewById(R.id.actionrecyclerView);
        ThrillerRecyclerView = findViewById(R.id.thrillerrecyclerView);
        HorrorRecylerView = findViewById(R.id.horrorrecyclerView);
        Kdramarecyclerview=findViewById(R.id.kdramarecyclerview);
        KidsRecylerView = findViewById(R.id.kidsrecyclerView);


        //Slider

        width=getScreenWidth()-10;
        height = (getScreenWidth()/16)*9-10;

        cardView= findViewById(R.id.cardView);
        cardView.getLayoutParams().width=width-20;
        cardView.getLayoutParams().height=height-10;

        sliderview = findViewById(R.id.imageSlider);
        sliderview.getLayoutParams().width=width;
        sliderview.getLayoutParams().height=height;
        sliderview.startAutoCycle();
        sliderview.setIndicatorAnimation(IndicatorAnimationType.WORM);
        //Retrieve Data from Firebase for Slider//
        FirebaseDatabase.getInstance().getReference("imageslider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long count= snapshot.getChildrenCount();
                TotalCount= count.intValue();
                sliderview.setSliderAdapter(new ImageSliderAdapter(MainActivity.this,TotalCount,width,height));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        //Trending
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        //Action
        ActionRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        ActionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ActionRecyclerView.setHasFixedSize(true);
        //Thriller
        ThrillerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        ThrillerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ThrillerRecyclerView.setHasFixedSize(true);
        //Horror
        HorrorRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        HorrorRecylerView.setItemAnimator(new DefaultItemAnimator());
        HorrorRecylerView.setHasFixedSize(true);
        //K-Drama
        Kdramarecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        Kdramarecyclerview.setItemAnimator(new DefaultItemAnimator());
        Kdramarecyclerview.setHasFixedSize(true);
        //Kids
        KidsRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        KidsRecylerView.setItemAnimator(new DefaultItemAnimator());
        KidsRecylerView.setHasFixedSize(true);


        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();
        //ArrayList
        messagesList = new ArrayList<>();   //Trending
        ActionList = new ArrayList<>();     //Action
        Thrillerlist = new ArrayList<>();
        HorrorList = new ArrayList<>();
        Kdramalist =new ArrayList<>();
        KidsList = new ArrayList<>();


        //Clear ArrayList
        ClearAllTrending();
        ClearAllAction();
        ClearAllThriller();
        ClearAllHorror();
        ClearAllKDrama();
        ClearAllKids();


        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        //setTitle("Loading...");
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        progressDialog.setCancelable(false);


        //get Data method
        GetDataFromFirebase();

    }

    void showcustomDialogue() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter);
        Spinner genrespinner = dialog.findViewById(R.id.genrespinner);
        Spinner streamspinner = dialog.findViewById(R.id.streamspinner);
        Button okay = dialog.findViewById(R.id.ok);



        List<String> genes = new ArrayList<>();
        genes.add(0,"All");
        genes.add("Action");
        genes.add("Kids");//
        genes.add("Drama");
        genes.add("Horror");//
        genes.add("Fantasy");
        genes.add("Sci-Fi");
        genes.add("Thriller");//
        genes.add("Romance");//
        genes.add("K-Drama");

        ArrayAdapter<String> dataadapter;
        dataadapter = new ArrayAdapter(MainActivity.this,R.layout.spinner_item,genes);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genrespinner.setAdapter(dataadapter);
        genrespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("All")){
                    genr ="All";
                }
                else{
                    genr= adapterView.getItemAtPosition(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        genrespinner.setSelection(dataadapter.getPosition(genr));

        List<String> platforms = new ArrayList<>();
        platforms.add(0,"Any");
        platforms.add("Netflix");
        platforms.add("Prime Video");//
        platforms.add("Disney + HotStar");
        platforms.add("Apple TV+");//
        platforms.add("Hulu");
        platforms.add("ALT Balaji");

        ArrayAdapter<String> Dataadapter;
        Dataadapter = new ArrayAdapter(MainActivity.this,R.layout.spinner_item,platforms);
        Dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streamspinner.setAdapter(Dataadapter);
        streamspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Any")){
                    platform="Any";
                }
                else{
                    platform = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        streamspinner.setSelection(Dataadapter.getPosition(platform));

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Source =  "Streaming Platform: "+"<b>" +platform+"</b>"  +"<br>"+"Genre: "+"<b>"+genr+"</b>"  ;
                choose.setText(Html.fromHtml(Source));
                scratch_to_find.callOnClick();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void GetDataFromFirebase(){

        //Trending
        Query query = myRef.child("trending").orderByKey().limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAllTrending();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();

                    messages.setImageUrl(snapshot.child("image").getValue().toString());
                    messages.setName(snapshot.child("name").getValue().toString());
                    messages.setDescription(snapshot.child("description").getValue().toString());
                    messages.setCast(snapshot.child("cast").getValue().toString());
                    messages.setFav(snapshot.child("fav").getValue().toString());
                    messages.setGenre(snapshot.child("genre").getValue().toString());
                    messages.setImagebackground(snapshot.child("imagebackground").getValue().toString());
                    messages.setRatings(snapshot.child("ratings").getValue().toString());
                    messages.setAvailableon(snapshot.child("availableon").getValue().toString());
                    messages.setTime(snapshot.child("time").getValue().toString());
                    messages.setTrailer(snapshot.child("trailer").getValue().toString());
                    messagesList.add(messages);
                }
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(),messagesList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
                progressDialog.setCancelable(true);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Action
        query = myRef.child("action").orderByKey().limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAllAction();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();

                    messages.setImageUrl(snapshot.child("image").getValue().toString());
                    messages.setName(snapshot.child("name").getValue().toString());
                    messages.setDescription(snapshot.child("description").getValue().toString());
                    messages.setCast(snapshot.child("cast").getValue().toString());
                    messages.setFav(snapshot.child("fav").getValue().toString());
                    messages.setGenre(snapshot.child("genre").getValue().toString());
                    messages.setImagebackground(snapshot.child("imagebackground").getValue().toString());
                    messages.setRatings(snapshot.child("ratings").getValue().toString());
                    messages.setAvailableon(snapshot.child("availableon").getValue().toString());
                    messages.setTime(snapshot.child("time").getValue().toString());
                    messages.setTrailer(snapshot.child("trailer").getValue().toString());
                    ActionList.add(messages);
                }
                ActionrecyclerAdapter = new RecyclerAdapter(getApplicationContext(),ActionList);
                ActionRecyclerView.setAdapter(ActionrecyclerAdapter);
                ActionrecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Thriller
        query = myRef.child("thriller").orderByKey().limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAllThriller();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();

                    messages.setImageUrl(snapshot.child("image").getValue().toString());
                    messages.setName(snapshot.child("name").getValue().toString());
                    messages.setDescription(snapshot.child("description").getValue().toString());
                    messages.setCast(snapshot.child("cast").getValue().toString());
                    messages.setFav(snapshot.child("fav").getValue().toString());
                    messages.setGenre(snapshot.child("genre").getValue().toString());
                    messages.setImagebackground(snapshot.child("imagebackground").getValue().toString());
                    messages.setRatings(snapshot.child("ratings").getValue().toString());
                    messages.setAvailableon(snapshot.child("availableon").getValue().toString());
                    messages.setTime(snapshot.child("time").getValue().toString());
                    messages.setTrailer(snapshot.child("trailer").getValue().toString());
                    Thrillerlist.add(messages);
                }
                ThrillerRecyclerAdapter = new RecyclerAdapter(getApplicationContext(),Thrillerlist);
                ThrillerRecyclerView.setAdapter(ThrillerRecyclerAdapter);
                ThrillerRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Horror
        query = myRef.child("horror").orderByKey().limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAllHorror();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();

                    messages.setImageUrl(snapshot.child("image").getValue().toString());
                    messages.setName(snapshot.child("name").getValue().toString());
                    messages.setDescription(snapshot.child("description").getValue().toString());
                    messages.setCast(snapshot.child("cast").getValue().toString());
                    messages.setFav(snapshot.child("fav").getValue().toString());
                    messages.setGenre(snapshot.child("genre").getValue().toString());
                    messages.setImagebackground(snapshot.child("imagebackground").getValue().toString());
                    messages.setRatings(snapshot.child("ratings").getValue().toString());
                    messages.setAvailableon(snapshot.child("availableon").getValue().toString());
                    messages.setTime(snapshot.child("time").getValue().toString());
                    messages.setTrailer(snapshot.child("trailer").getValue().toString());
                    HorrorList.add(messages);
                }
                HorrorRecylerAdapter = new RecyclerAdapter(getApplicationContext(),HorrorList);
                HorrorRecylerView.setAdapter(HorrorRecylerAdapter);
                HorrorRecylerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Kdrama
        query = myRef.child("kdrama").orderByKey().limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAllKDrama();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();

                    messages.setImageUrl(snapshot.child("image").getValue().toString());
                    messages.setName(snapshot.child("name").getValue().toString());
                    messages.setDescription(snapshot.child("description").getValue().toString());
                    messages.setCast(snapshot.child("cast").getValue().toString());
                    messages.setFav(snapshot.child("fav").getValue().toString());
                    messages.setGenre(snapshot.child("genre").getValue().toString());
                    messages.setImagebackground(snapshot.child("imagebackground").getValue().toString());
                    messages.setRatings(snapshot.child("ratings").getValue().toString());
                    messages.setAvailableon(snapshot.child("availableon").getValue().toString());
                    messages.setTime(snapshot.child("time").getValue().toString());
                    messages.setTrailer(snapshot.child("trailer").getValue().toString());
                    Kdramalist.add(messages);
                }
                KdramaRecycleradapter = new RecyclerAdapter(getApplicationContext(),Kdramalist);
                Kdramarecyclerview.setAdapter(KdramaRecycleradapter);
                KdramaRecycleradapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Kids
        query = myRef.child("kids").orderByKey().limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAllKids();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();

                    messages.setImageUrl(snapshot.child("image").getValue().toString());
                    messages.setName(snapshot.child("name").getValue().toString());
                    messages.setDescription(snapshot.child("description").getValue().toString());
                    messages.setCast(snapshot.child("cast").getValue().toString());
                    messages.setFav(snapshot.child("fav").getValue().toString());
                    messages.setGenre(snapshot.child("genre").getValue().toString());
                    messages.setImagebackground(snapshot.child("imagebackground").getValue().toString());
                    messages.setRatings(snapshot.child("ratings").getValue().toString());
                    messages.setAvailableon(snapshot.child("availableon").getValue().toString());
                    messages.setTime(snapshot.child("time").getValue().toString());
                    messages.setTrailer(snapshot.child("trailer").getValue().toString());
                    KidsList.add(messages);
                }
                KidsRecylerAdapter = new RecyclerAdapter(getApplicationContext(),KidsList);
                KidsRecylerView.setAdapter(KidsRecylerAdapter);
                KidsRecylerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
    private void ClearAllTrending() {
        //Trending
        if (messagesList != null) {
            messagesList.clear();
            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        messagesList = new ArrayList<>();
    }
    private void ClearAllAction(){
        //Action
        if(ActionList != null) {
            ActionList.clear();
            if(ActionrecyclerAdapter != null){
                ActionrecyclerAdapter.notifyDataSetChanged();
            }
        }
        ActionList = new ArrayList<>();
    }
    private void ClearAllThriller(){
        //Thriller
        if(Thrillerlist != null) {
            Thrillerlist.clear();
            if(ThrillerRecyclerAdapter != null){
                ThrillerRecyclerAdapter.notifyDataSetChanged();
            }
        }
        Thrillerlist = new ArrayList<>();
    }
    private void ClearAllHorror(){
        //Horror
        if(HorrorList != null) {
            HorrorList.clear();
            if(HorrorRecylerAdapter != null){
                HorrorRecylerAdapter.notifyDataSetChanged();
            }
        }
        HorrorList = new ArrayList<>();
    }
    private void ClearAllKDrama(){
        //K-Drama
        if(Kdramalist != null) {
            Kdramalist.clear();
            if(KdramaRecycleradapter != null){
                KdramaRecycleradapter.notifyDataSetChanged();
            }
        }
        Kdramalist = new ArrayList<>();
    }
    private void ClearAllKids(){
        //Kids
        if(KidsList != null) {
            KidsList.clear();
            if(KidsRecylerAdapter != null){
                KidsRecylerAdapter.notifyDataSetChanged();
            }
        }
        KidsList = new ArrayList<>();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    public void checkInternet() {
        final ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);


                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(!isConnected) {
                    //Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, NoInternetActivity.class);
                    MainActivity.this.startActivity(intent);
                }

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


}