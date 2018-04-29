package com.utsman.kucingapes.learnhistoryidn;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.vitorlaerte.bypassglideimagegetter.BypassGlideImageGetter;

import in.uncod.android.bypass.Bypass;


public class Detail extends AppCompatActivity {

    ScrollView scroll;
    TextView body;

    FirebaseDatabase database;
    DatabaseReference mRef;

    String urlDat, url, kategori;
    int id, patokan;

    FirebaseUser user;
    FirebaseAuth mAuth;

    MenuItem fav, pin, favFill, pinFill;

    String equalid, statusPin, statusFav;

    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        scroll = findViewById(R.id.scroll);
        body = findViewById(R.id.body);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        urlDat = bundle.getString("url");

        if (urlDat != null) {
            url = urlDat.replaceAll("\\s{2,}", "\n");
        }
        id = bundle.getInt("id");

        Bypass bypass = new Bypass(this);

        body.setText(bypass.markdownToSpannable(url,
                new BypassGlideImageGetter(body, Glide.with(this))));

        equalid = String.valueOf(id);

    }

    @Override
    public void onBackPressed() {
        int saveScroll = scroll.getScrollY();
        int scrollHeight = scroll.getChildAt(0).getHeight();

        RelativeLayout relativeLayout = findViewById(R.id.parent);
        int parentHeight = relativeLayout.getHeight();

        final int total = scrollHeight-parentHeight;
        final int persen = (int) (saveScroll *100f/total);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("user").child(user.getUid());

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                patokan = dataSnapshot.child(equalid).child("prog").getValue(Integer.class);
                kategori = dataSnapshot.child(equalid).child("cat").getValue(String.class);

                if (persen >= patokan) {
                    final DatabaseReference cDat = FirebaseDatabase.getInstance().getReference()
                            .child("user").child(user.getUid());

                    cDat.child(String.valueOf(id)).child("prog").setValue(persen);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
        fav = menu.findItem(R.id.action_favorite);
        pin = menu.findItem(R.id.action_pin);

        favFill = menu.findItem(R.id.action_favorite_fill);
        pinFill = menu.findItem(R.id.action_pin_fill);

        fav.setVisible(false);
        favFill.setVisible(false);
        pin.setVisible(false);
        pinFill.setVisible(false);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("user").child(user.getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                statusPin = dataSnapshot.child(equalid).child("pin").getValue(String.class);
                statusFav = dataSnapshot.child(equalid).child("fav").getValue(String.class);
                if (statusPin != null) {
                    if (statusPin.equals("n")) {
                       pinFill.setVisible(false);
                       pin.setVisible(true);
                    } if (statusPin.equals("y")) {
                        pinFill.setVisible(true);
                        pin.setVisible(false);
                    }
                }

                if (statusFav != null) {
                    if (statusFav.equals("n")) {
                        fav.setVisible(true);
                        favFill.setVisible(false);
                    } if (statusFav.equals("y")) {
                        fav.setVisible(false);
                        favFill.setVisible(true);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String y = "y";
        String n = "n";

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("user").child(user.getUid());
        /*mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nah = dataSnapshot.child("pin").getValue(String.class);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        /*if (pin.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_pin))) {
            mRef.child("pin").setValue("y");
            mRef.child("date").setValue(ServerValue.TIMESTAMP);
            pin.setIcon(R.drawable.ic_pin_red);
        }*/

        /*if (pin.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_pin_red))) {
            mRef.child(equalid).child("pin").setValue("n");
            mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
            pin.setIcon(R.drawable.ic_pin);
        }*/

        /*if (pin.getIcon().equals(getResources().getDrawable(R.drawable.ic_pin_red))) {
            mRef.child(equalid).child("pin").setValue("n");
            mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
            pin.setIcon(R.drawable.ic_pin);
        } if (pin.getIcon().equals(getResources().getDrawable(R.drawable.ic_pin))) {
            mRef.child(equalid).child("pin").setValue("y");
            mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
            pin.setIcon(R.drawable.ic_pin_red);
        }*/



        /*int id = item.getItemId();
        if (id == R.id.action_favorite) {
            Toast.makeText(this, "Action clicked", Toast.LENGTH_SHORT).show();
            //fav.setVisible(false);
            //favFill.setVisible(true);
            mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
            mRef.child(equalid).child("fav").setValue(y);
        } if (id == R.id.action_pin) {
            Toast.makeText(this, "tes", Toast.LENGTH_SHORT).show();
            //pin.setVisible(false);
            //pinFIll.setVisible(true);
            mRef.child(equalid).child("pin").setValue(y);
            mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
            pin.setChecked(true);
            pin.setIcon(R.drawable.ic_pin_red);
        }*/

        switch (item.getItemId()) {
            case R.id.action_favorite:
                Toast.makeText(getApplicationContext(),"Settings Option Selected",Toast.LENGTH_SHORT).show();
                mRef.child(equalid).child("fav").setValue("y");
                mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
                return true;
            case R.id.action_favorite_fill:
                Toast.makeText(getApplicationContext(),"Delete Option Selected",Toast.LENGTH_SHORT).show();
                mRef.child(equalid).child("fav").setValue("n");
                mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
                return true;
            case R.id.action_pin:
                Toast.makeText(getApplicationContext(),"About Option Selected",Toast.LENGTH_SHORT).show();
                mRef.child(equalid).child("pin").setValue("y");
                mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
                return true;
            case R.id.action_pin_fill:
                Toast.makeText(getApplicationContext(),"Note Option Selected",Toast.LENGTH_SHORT).show();
                mRef.child(equalid).child("pin").setValue("n");
                mRef.child(equalid).child("date").setValue(ServerValue.TIMESTAMP);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

       // return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*MenuItem pin = menu.findItem(R.id.action_pin);

        invalidateOptionsMenu();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("user").child(user.getUid());


        if (pin.isChecked()) {
            pin.setIcon(R.drawable.ic_pin);
        }*/

        MenuItem checkable = menu.findItem(R.id.action_pin);
        checkable.setChecked(isChecked);
        checkable.setIcon(R.drawable.ic_pin);
        return true;


       // return super.onPrepareOptionsMenu(menu);
    }
}
