package com.dat.yecxanh.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.dat.yecxanh.R;
import com.dat.yecxanh.ui.SearchActivity;

public class ContactsAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SearchView searchView;

    public ContactsAdapter(SearchActivity context, Cursor contacts, androidx.appcompat.widget.SearchView searchView) {
        super(context, contacts);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = mContext;
        this.searchView = searchView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.item_contact, searchView, false);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

        String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

        String hasPhone = "Has Phone Number";

        if (phone.equals(0)) {
            hasPhone = "Without Phone Number";
        }
        TextView nameTv = view.findViewById(R.id.tv_name);
        nameTv.setText(name);

        TextView phoneNo = view.findViewById(R.id.tv_phone);
        phoneNo.setText(hasPhone);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView pName = (TextView) view.findViewById(R.id.tv_name);
                searchView.setIconified(true);

                Toast.makeText(context, "Selected Contact " + pName.getText(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
