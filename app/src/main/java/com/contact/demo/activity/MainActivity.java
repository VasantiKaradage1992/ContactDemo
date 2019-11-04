package com.contact.demo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.contact.demo.R;
import com.contact.demo.adapter.ContactsAdapter;
import com.contact.demo.model.Contact;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;



public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    private ArrayList<Contact> listContacts;
    private RecyclerView lvContacts;
    public static final int REQUEST_PERMISSION = 1001;
    public  static  final  int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    private ContactsAdapter adapterContacts;
    private AdapterLoad adapter;
    private Toolbar mTopToolbar;
    public  static  final  int PERMISSIONS_CODE=1;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
        setSupportActionBar(mTopToolbar);

        if (checkPermission()) {
            RVadapter();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);


         //  callAdapter();




            // ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_CONTACTS, WRITE_CONTACTS}, REQUEST_PERMISSION);
           // RVadapter();
        }
        //RVadapter();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(READ_CONTACTS)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                      RVadapter();
                    } else {//requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_CODE);
                    }
                }
            }
        }

    }

    private void callAdapter() {
        RVadapter();
    }

    private void init() {
        lvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        listContacts=new ArrayList<>();
    }


    //set Information to adapter
    private void RVadapter() {
        adapter = new AdapterLoad();
        listContacts = new ContactFetcher(this).fetchAll();
        lvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        adapterContacts = new ContactsAdapter(this, listContacts);
        lvContacts.setAdapter(adapterContacts);

    }

    // request permission
    private void requestPermission() {

    }

    //check permission
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        boolean loadMore = /* maybe add a padding */
                i + i1 >= i2;

        if (loadMore) {
            adapter.count += i1; // or any other amount
            adapter.notifyDataSetChanged();
        }

    }

    public class AdapterLoad extends BaseAdapter {

        int count = 1000; /* starting amount */

        public int getCount() {
            return count;
        }

        public Object getItem(int pos) {
            return pos;
        }

        public long getItemId(int pos) {
            return pos;
        }

        public View getView(int pos, View v, ViewGroup p) {
            TextView view = new TextView(MainActivity.this);
            view.setText("entry View : " + pos);
            return view;
        }

    }
}
