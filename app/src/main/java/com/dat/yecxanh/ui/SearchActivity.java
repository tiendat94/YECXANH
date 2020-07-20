package com.dat.yecxanh.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.dat.yecxanh.R;
import com.dat.yecxanh.adapter.ContactsAdapter;
import com.dat.yecxanh.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    private static final int REQUEST_PERMISSION = 1;

    private SearchView searchView;
    private ListView contactsList;

    @Override
    protected String getTitleScreen() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {
        contactsList = findViewById(R.id.contact_list);
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }


    @Override
    protected void afteriInitView() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, REQUEST_PERMISSION);
        }
    }

    @Override
    protected void initListener() {

    }

    private final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            Cursor contacts = getListOfContactNames(s);
            String[] cursorColumns = {
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
            int[] viewIds = {R.id.tv_name};

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                    SearchActivity.this,
                    R.layout.item_contact,
                    contacts,
                    cursorColumns,
                    viewIds,
                    0);

            contactsList.setAdapter(simpleCursorAdapter);
            contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int i, long l) {
                    //get contact details and display
                    TextView tv = view.findViewById(R.id.tv_name);
                    Toast.makeText(SearchActivity.this,
                            "Selected Contact " + tv.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            Cursor contacts = getListOfContactNames(s);
            ContactsAdapter cursorAdapter = new ContactsAdapter
                    (SearchActivity.this, contacts, searchView);
           // searchView.setSuggestionsAdapter(cursorAdapter);
            return true;
        }
    };

    public Cursor getListOfContactNames(String searchText) {
        Cursor cursor = null;
        ContentResolver contentResolver = getContentResolver();
        String[] mProjection = {ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String selection = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + "LIKE ?";
        String[] selectionArgs = new String[]{"%" + searchText + "%"};

        cursor = contentResolver.query(uri, mProjection, selection, selectionArgs, null);
        return cursor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(onQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }
}
