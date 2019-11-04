package com.contact.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;
import com.contact.demo.R;
import com.contact.demo.model.Contact;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyHolder> implements AbsListView.OnScrollListener {
    private Context mContext;
    private ArrayList<Contact> contactArrayList;



    public ContactsAdapter(Context mContext, ArrayList<Contact> listContacts) {
        this.mContext = mContext;
        this.contactArrayList = listContacts;
    }

    @NonNull
    @Override
    public ContactsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_item, viewGroup, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.MyHolder myHolder, int i) {
        myHolder.tvName.setText(contactArrayList.get(i).name);
        String image = contactArrayList.get(i).photo;

        if(image!=null) {

            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myHolder.circleImageView.setImageBitmap(decodedByte);
        }
        else
        {
            myHolder.circleImageView.setImageResource(R.drawable.ic_user);
        }

        myHolder.tvNumber.setText(contactArrayList.get(i).number);
    }


    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        Log.d("entered onScroll", " " + i + i1
                + i2);
        if (((i + i1) >= i2 - 1)) {
            Log.d("entered if", " " + i + i1
                    + i2);
            // if we're at the bottom of the listview, load more data
            addData(i2, i2); // values.get(totalItemCount));
        }

    }

    private void addData(int totalItemCount, int productId) {

        Toast.makeText(mContext, "last item", Toast.LENGTH_SHORT).show();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvName,tvNumber;
        private CircleImageView circleImageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            circleImageView = itemView.findViewById(R.id.image);
            tvNumber=itemView.findViewById(R.id.tv_number);

        }
    }
}
