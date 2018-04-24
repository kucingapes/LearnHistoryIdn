package com.utsman.kucingapes.learnhistoryidn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Input extends AppCompatActivity {

    DatabaseReference mDatabase, cDatabase;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void idEdit(View view) {
        final DatabaseReference basDatabase = FirebaseDatabase.getInstance().getReference().child("data");
        final DatabaseReference newDatabase = FirebaseDatabase.getInstance().getReference().child("newdata");
        final DatabaseReference cDatabase = FirebaseDatabase.getInstance().getReference().child("user");
        cDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateData(newDatabase, cDatabase, basDatabase);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateData(final DatabaseReference newDatabase, final DatabaseReference cDatabase, final DatabaseReference basDatabase) {
        newDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot update : dataSnapshot.getChildren()) {
                    String id = update.child("id").toString();
                    id = id.replaceAll("[^\\d.]|\\.(?!\\d)", "");
                   // Map<String, Object> map = (Map<String, Object>)update.getValue(Map.class);
                    Map<String, Object> map = new HashMap<>();
                    map.put(id, update.getValue());
                    cDatabase.child(user.getUid()).updateChildren(map);
                    basDatabase.updateChildren(map);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
