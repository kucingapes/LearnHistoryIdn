package com.utsman.kucingapes.learnhistoryidn;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.utsman.kucingapes.learnhistoryidn.Adapter.ItemViewHolder;
import com.utsman.kucingapes.learnhistoryidn.Adapter.RcGetter;

public class Recycler extends AppCompatActivity {
    private FirebaseRecyclerAdapter<RcGetter, ItemViewHolder> mAdapter;

    FirebaseUser user;
    FirebaseAuth mAuth;
    String kategori;
    //String itung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        final RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            kategori = bundle.getString("kategori");
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("user").child(user.getUid());

        final DatabaseReference cDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query catQuery = mDatabase.orderByChild("cat").equalTo(kategori);

        cDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.child("data").getChildren()) {
                    int itung = 0;
                    int hasilitung;
                   // snap.equals("kategori");
                    hasilitung = (int) (itung+ snap.getChildrenCount());
                    //cDatabase.child("kategori").child().child("itung").setValue(hasilitung);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*FirebaseRecyclerOptions recyclerOptions = new FirebaseRecyclerOptions.Builder<RcGetter>()
                .setQuery(dataQuery, RcGetter.class).build();*/

        FirebaseRecyclerOptions recyclerOptions = new FirebaseRecyclerOptions.Builder<RcGetter>()
                .setQuery(catQuery, RcGetter.class).build();

        mAdapter = new FirebaseRecyclerAdapter<RcGetter, ItemViewHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final RcGetter model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImg(getBaseContext(), model.getImg());
                holder.setProgress(model.getProg());
                holder.setNumbProg(model.getProg());

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
                        .inflate(R.layout.item_recycler, parent, false);
                return new ItemViewHolder(view);
            }
        };

        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


}
