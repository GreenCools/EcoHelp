package com.example.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.example.Classes.Coupons;
import com.example.ecohelp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;



public class LibraryActivity extends BaseActivity {
    private static final String TAG = "LibraryActivity";
    private RecyclerView recyclerView;


    List<Coupons> couponss = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Купоны");

        }
        setInitialData();

        recyclerView = findViewById(R.id.list);

        RecyclerAdapter adapter = new RecyclerAdapter(this,couponss);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();  //или this.finish или что то свое
        return true;
    }


    public void  setInitialData(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(getUid()).child("coupons");



        ValueEventListener valueEventListener = new ValueEventListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                long petiarochkaAmount100 = dataSnapshot.child("petiarochka").child("petiarochka100").getValue(Long.class);
                long petiarochkaAmount300 = dataSnapshot.child("petiarochka").child("petiarochka300").getValue(Long.class);
                long petiarochkaAmount500 = dataSnapshot.child("petiarochka").child("petiarochka500").getValue(Long.class);
                long lentaAmount100 = dataSnapshot.child("lenta").child("lenta100").getValue(Long.class);
                long lentaAmount300 = dataSnapshot.child("lenta").child("lenta300").getValue(Long.class);
                long lentaAmount500 = dataSnapshot.child("lenta").child("lenta500").getValue(Long.class);


                Log.v("JAJJAJAJAJA",""+petiarochkaAmount100);
                couponss.clear();
                for (int i = 0; i < petiarochkaAmount100; i++) {

                    couponss.add(new Coupons(R.drawable.p100,"petiarochkaAmount100"));
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
                for (int i = 0; i < petiarochkaAmount300; i++) {

                    couponss.add(new Coupons(R.drawable.p300,"petiarochkaAmount300"));
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
                for (int i = 0; i < petiarochkaAmount500; i++) {

                    couponss.add(new Coupons(R.drawable.p500,"petiarochkaAmount500"));
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
                for (int i = 0; i < lentaAmount100; i++) {

                    couponss.add(new Coupons(R.drawable.l100,"lentaAmount100"));
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
                for (int i = 0; i < lentaAmount300; i++) {

                    couponss.add(new Coupons(R.drawable.l300,"lentaAmount300"));
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
                for (int i = 0; i < lentaAmount500; i++) {

                    couponss.add(new Coupons(R.drawable.l500,"lentaAmount500"));
                    recyclerView.getAdapter().notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        uidRef.addValueEventListener(valueEventListener);

    }

















}

