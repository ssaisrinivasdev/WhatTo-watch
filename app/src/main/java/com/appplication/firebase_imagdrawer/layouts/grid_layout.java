package com.appplication.firebase_imagdrawer.layouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appplication.firebase_imagdrawer.NoInternetActivity;
import com.appplication.firebase_imagdrawer.R;
import com.appplication.firebase_imagdrawer.adapters.GridRecyclerAdapter;
import com.appplication.firebase_imagdrawer.helpers.Grid;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class grid_layout extends AppCompatActivity {

    private String value;

    private DatabaseReference myRef;
    private ArrayList<Grid> gridl;



    private GridRecyclerAdapter gridrecyclerAdapter;
    TextView gridtitle;
    ProgressDialog progressDialog;

    RecyclerView recyclerView;

    ImageButton sortby;
    private static String platform = "Any";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);
        platform="Any";

        checkInternet();
        value = getIntent().getStringExtra("value");
        gridtitle = findViewById(R.id.gridtitle);
        String text = value.substring(0, 1).toUpperCase() + value.substring(1);
        gridtitle.setText(text);

        progressDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.gridrecycler);

        sortby=findViewById(R.id.sortby);
        sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcustomDialogue();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 3;
                int spacing = 10;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference();

        gridl = new ArrayList<>();

        ClearAll();

        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        GetDataFromFirebase();


    }


    private void GetDataFromFirebase(){

        Query query = myRef.child(value);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String plat = snapshot.child("availableon").getValue().toString();
                        if(plat.contains(platform) || platform.equals("Any")) {
                            Grid grid = new Grid();
                            grid.setImageUrl(snapshot.child("image").getValue().toString());
                            grid.setName(snapshot.child("name").getValue().toString());
                            grid.setDescription(snapshot.child("description").getValue().toString());
                            grid.setCast(snapshot.child("cast").getValue().toString());
                            grid.setFav(snapshot.child("fav").getValue().toString());
                            grid.setGenre(snapshot.child("genre").getValue().toString());
                            grid.setImagebackground(snapshot.child("imagebackground").getValue().toString());
                            grid.setRatings(snapshot.child("ratings").getValue().toString());
                            grid.setAvailableon(snapshot.child("availableon").getValue().toString());
                            grid.setTime(snapshot.child("time").getValue().toString());
                            grid.setTrailer(snapshot.child("trailer").getValue().toString());
                            gridl.add(grid);
                        }
                    }
                    gridrecyclerAdapter = new GridRecyclerAdapter(getApplicationContext(), gridl);
                    recyclerView.setAdapter(gridrecyclerAdapter);
                    gridrecyclerAdapter.notifyDataSetChanged();
                    progressDialog.setCancelable(true);
                    progressDialog.dismiss();
               // }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });




    }

    void showcustomDialogue() {
        final Dialog dialog = new Dialog(grid_layout.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filteronlyplatform);
        Spinner streamspinner = dialog.findViewById(R.id.streamspinner);
        Button okay = dialog.findViewById(R.id.ok);



        List<String> platforms = new ArrayList<>();
        platforms.add(0,"Any");
        platforms.add("Netflix");
        platforms.add("Prime Video");//
        platforms.add("Disney + HotStar");
        platforms.add("Apple TV+");//
        platforms.add("Hulu");
        platforms.add("ALT Balaji");

        ArrayAdapter<String> Dataadapter;
        Dataadapter = new ArrayAdapter(grid_layout.this,R.layout.spinner_item,platforms);
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
                GetDataFromFirebase();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void ClearAll() {
        if(gridl != null) {
            gridl.clear();

            if(gridrecyclerAdapter != null){
                gridrecyclerAdapter.notifyDataSetChanged();
            }
        }
        gridl = new ArrayList<>();
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
            Intent intent = new Intent(grid_layout.this, NoInternetActivity.class);
            grid_layout.this.startActivity(intent);
        }

    }
}