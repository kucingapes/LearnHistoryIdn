package com.utsman.kucingapes.learnhistoryidn;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

public class Detail extends AppCompatActivity {

    ScrollView scroll;
    MarkdownView markdownView;

    FirebaseDatabase database;
    DatabaseReference mRef;

    String url, kategori;
    int id, patokan;

    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        scroll = findViewById(R.id.scroll);
        markdownView = findViewById(R.id.markdown_view);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        url = bundle.getString("url");
        id = bundle.getInt("id");

        markdownView.addStyleSheet(new Github());
        markdownView.getSettings().setAppCacheEnabled(true);
        markdownView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        markdownView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        markdownView.loadMarkdownFromUrl(url);
    }

    @Override
    public void onBackPressed() {
        int saveScroll = scroll.getScrollY();
        int scrollHeight = scroll.getChildAt(0).getHeight();

        RelativeLayout relativeLayout = findViewById(R.id.parent);
        int parentHeight = relativeLayout.getHeight();

        final int total = scrollHeight-parentHeight;
        final int persen = (int) (saveScroll *100f/total);

        final String equalid = String.valueOf(id);

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
}
