package com.utsman.kucingapes.learnhistoryidn;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.utsman.kucingapes.learnhistoryidn.Adapter.ItemCategoryHolder;
import com.utsman.kucingapes.learnhistoryidn.Adapter.RcGetter;

public class MainMenu extends AppCompatActivity {
    private FirebaseRecyclerAdapter<RcGetter, ItemCategoryHolder> cAdapter;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_cat);
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

        recyclerView.setAdapter(cAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cAdapter.stopListening();
    }
}
