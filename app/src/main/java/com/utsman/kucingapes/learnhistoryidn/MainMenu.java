package com.utsman.kucingapes.learnhistoryidn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.utsman.kucingapes.learnhistoryidn.Adapter.EmptyRecyclerView;
import com.utsman.kucingapes.learnhistoryidn.Adapter.ItemCategoryHolder;
import com.utsman.kucingapes.learnhistoryidn.Adapter.ItemViewHolder;
import com.utsman.kucingapes.learnhistoryidn.Adapter.RcGetter;

import java.util.Date;

public class MainMenu extends AppCompatActivity {
    private FirebaseRecyclerAdapter<RcGetter, ItemCategoryHolder> cAdapter;
    private FirebaseRecyclerAdapter<RcGetter, ItemViewHolder> pAdapter;

    FirebaseUser user;
    FirebaseAuth auth;

    RecyclerView recyclerView;
    EmptyRecyclerView pinRecyclerView;
    TextView textPin;

    RelativeLayout empty;

    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        recyclerView = findViewById(R.id.recycler_view_cat);
        pinRecyclerView = findViewById(R.id.pin_list);
        textPin = findViewById(R.id.text_pin);
        empty = findViewById(R.id.empty_view);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("kategori");
        databaseReference.keepSynced(true);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = databaseReference.orderByKey();

        FirebaseRecyclerOptions recyclerOptions = new FirebaseRecyclerOptions.Builder<RcGetter>()
                .setQuery(query, RcGetter.class).build();

        cAdapter = new FirebaseRecyclerAdapter<RcGetter, ItemCategoryHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ItemCategoryHolder holder, int position, @NonNull final RcGetter model) {
                holder.setTitleCat(model.getTitleCat());
                holder.setImgCat(getBaseContext(), model.getImgCat());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String kategori = model.getNameCat();
                        Intent intent = new Intent(getApplicationContext(), Recycler.class);
                        intent.putExtra("kategori", kategori);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ItemCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_recycler_cat, parent, false);
                return new ItemCategoryHolder(view);
            }
        };

        /*final DatabaseReference pDatabase = FirebaseDatabase.getInstance().getReference()
                .child("user").child(user.getUid());
        pDatabase.keepSynced(true);

        pinRecyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        pinRecyclerView.setLayoutManager(linearLayoutManager);
        Query dataQuery = pDatabase.orderByKey();
        final long daysInpast= new Date().getTime();
        Query pinQuery = pDatabase.orderByChild("pin").equalTo("y");
        //pDatabase.orderByChild("date").startAt(daysInpast);
       // pDatabase.orderByChild("date").startAt(daysInpast);


        FirebaseRecyclerOptions pinOption = new FirebaseRecyclerOptions.Builder<RcGetter>()
                .setQuery(pinQuery, RcGetter.class).build();

        pAdapter = new FirebaseRecyclerAdapter<RcGetter, ItemViewHolder>(pinOption) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final RcGetter model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImg(getBaseContext(), model.getImg());
                holder.setProgress(model.getProg());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = model.getUrl();
                        final int id = model.getId();
                        Intent intent = new Intent(getApplicationContext(), Detail.class);
                        intent.putExtra("url", url);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pin, parent, false);
                return new ItemViewHolder(view);
            }
        };

        pinRecyclerView.setEmptyView(empty);*/

        recyclerView.setAdapter(cAdapter);
       // pinRecyclerView.setAdapter(pAdapter);

        /*if (pinRecyclerView.getVisibility() == View.GONE) {
            textPin.setVisibility(View.GONE);
        } else textPin.setVisibility(View.VISIBLE);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                startActivity(new Intent(this, Input.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        cAdapter.startListening();
       // pAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cAdapter.stopListening();
       // pAdapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();
    }
}
