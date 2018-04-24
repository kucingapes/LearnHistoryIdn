package com.utsman.kucingapes.learnhistoryidn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    MyAdapter mAdapter;
    FirebaseDatabase database;
    DatabaseReference mRef;

    String mSatu, mDua, mTiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        viewPager = findViewById(R.id.pager);

        mAdapter = new MyAdapter(this);
        viewPager.setAdapter(mAdapter);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("data");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String satu = dataSnapshot.getValue(String.class);
                String dua = dataSnapshot.getValue(String.class);
                String tiga = dataSnapshot.getValue(String.class);

                mSatu = satu;
                mDua = dua;
                mTiga = tiga;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class MyAdapter extends PagerAdapter{
        Context context;
        LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.context = context;
        }

        String[] list_string = {
                mSatu, mDua, mTiga
        };

        @Override
        public int getCount() {
            return list_string.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return (view == object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_pager, container, false);
            //LinearLayout linearLayout = view.findViewById(R.id.lay_item);
            TextView textView = view.findViewById(R.id.text_body);

            textView.setText(list_string[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
