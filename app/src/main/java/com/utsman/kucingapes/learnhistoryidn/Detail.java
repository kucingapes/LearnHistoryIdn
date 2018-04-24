package com.utsman.kucingapes.learnhistoryidn;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
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

    WebView webView;
    ScrollView scroll;
    MarkdownView markdownView;

    private static int save;
    private static int saveScroll;
    FirebaseDatabase database;
    DatabaseReference mRef;

    String url;
    int id;

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
        markdownView.loadMarkdownFromUrl(url);


        webView = findViewById(R.id.web);
        webView.loadUrl("https://github.com/antonKozyriatskyi/CircularProgressIndicator/blob/master/README.md");


        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                database = FirebaseDatabase.getInstance();
                mRef = database.getReference().child("bah");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int Y = dataSnapshot.getValue(Integer.class);
                        //int Y = Integer.parseInt(scrol);

                        webView.setScrollY(Y);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //final int lenght = webView.getMeasuredHeight();
                final int lenght = webView.getContentHeight();
                //final int nah = scroll.getChildAt(0).getHeight();
                final int nah = scroll.getMeasuredHeight();


                //database = FirebaseDatabase.getInstance();

            }
        }, 5000);*/
    }

    @Override
    public void onBackPressed() {
        save = webView.getScrollY();
        saveScroll = scroll.getScrollY();
        //int totalHeight = scroll.getChildAt(0).getHeight();
        int scrollHeight = scroll.getChildAt(0).getHeight();

        RelativeLayout relativeLayout = findViewById(R.id.parent);
        int parentHeight = relativeLayout.getHeight();

        final int total = scrollHeight-parentHeight;
        final int persen = (int) (saveScroll*100f/total);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("bah");
        mRef.setValue(saveScroll);

        mRef = database.getReference().child("cl");
        mRef.setValue(persen);

        final DatabaseReference cDat = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        cDat.child(String.valueOf(id)).child("prog").setValue(persen);

       // mRef = database.getReference().child("data")
        super.onBackPressed();
    }
}
