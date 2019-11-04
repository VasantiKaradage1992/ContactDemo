package com.contact.demo.activity;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import android.support.v4.content.CursorLoader;
import android.util.Base64;

import com.contact.demo.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ContactFetcher {
    private final Context context;
    private Bitmap bitmap;
    private String contactNumber;
    ArrayList<Contact> listContacts;
    private String encodedImage = "null";

    public ContactFetcher(Context c) {
        this.context = c;
    }

    public ArrayList<Contact> fetchAll() {
        contactDetails();
        return listContacts;
    }

    private void contactDetails() {
        String[] projectionFields = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts.HAS_PHONE_NUMBER

        };
        listContacts = new ArrayList<>();
        CursorLoader cursorLoader = new CursorLoader(context,
                ContactsContract.Contacts.CONTENT_URI,
                projectionFields, // the columns to retrieve
                null, // the selection criteria (none)
                null, // the selection args (none)
                null // the sort order (default)
        );


        Cursor c = cursorLoader.loadInBackground();

        final Map<String, Contact> contactsMap = new HashMap<>(c.getCount());

        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                String contactId = c.getString(idIndex);
                String contactDisplayName = c.getString(nameIndex);
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    contactNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                Uri contactPhotoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(Uri.encode(contactId)));

                InputStream photoDataStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), contactPhotoUri); // <-- always null
                bitmap = BitmapFactory.decodeStream(photoDataStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                } else {
                    encodedImage = null;
                }
                // }
                Contact contact = new Contact(contactId, contactDisplayName, encodedImage, contactNumber);
                contactsMap.put(contactId, contact);
                listContacts.add(contact);
            } while (c.moveToNext());
        }

        c.close();


    }

}
