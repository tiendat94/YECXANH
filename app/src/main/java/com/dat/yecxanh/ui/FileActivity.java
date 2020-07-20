package com.dat.yecxanh.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.dat.yecxanh.R;
import com.dat.yecxanh.base.BaseActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.StatsSnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileActivity extends BaseActivity {

    private static final int READ_REQ = 24;

    private static final int WRITE_REQ = 25;

    private static final int DELETE_REQ = 27;

    private String productImage =
            "https://www.android.com/static/2016/img/devices/phones/htc-10/htc-10-02_w_1x.jpg";

    private ImageView productImg;

    @Override
    protected String getTitleScreen() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_file;
    }

    @Override
    protected void initView() {
        productImg = (ImageView) findViewById(R.id.product_img);
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        loadImage();
    }

    @Override
    protected void afteriInitView() {

    }

    @Override
    protected void initListener() {

    }

    private void loadImage() {
        StatsSnapshot ss = Picasso.get().getSnapshot();
        Log.d("download image stats", "" + ss.cacheHits);
        Log.d("download image stats", "" + ss.cacheMisses);
        Log.d("download image stats", "" + ss.downloadCount);
        Log.d("download image stats", "" + ss.totalDownloadSize);
        //clear cache and cancel pending requests
        Picasso.get().invalidate(productImage);
        Picasso.get().cancelRequest(productImg);

        //set image rotation and placeholder image
        RequestCreator requestCreator = Picasso.get().load(productImage);
        requestCreator = requestCreator.rotate(20).placeholder(R.drawable.ic_launcher_background);

        //set error image, memory policy
        requestCreator = requestCreator.error(R.drawable.ic_launcher_background);
        requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);

        // resize and crop
        requestCreator = requestCreator.resize(400, 400).centerCrop();

        //load image to imageview
        requestCreator.into(productImg);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
            }
            if (requestCode == WRITE_REQ) {
                editDocument(uri);
            } else if (requestCode == DELETE_REQ) {
                deleteFile(uri);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void deleteFile(Uri uri) {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                String flags = cursor.getString(cursor.getColumnIndex(DocumentsContract.Document.COLUMN_FLAGS));
                String[] colums = cursor.getColumnNames();

                for (String col : colums) {
                    Log.i("", "Colum Flags " + col);
                }
                Log.i(" ", "Delete Flags " + flags);
                if (flags.contains(" " + DocumentsContract.Document.FLAG_SUPPORTS_DELETE)) {
                    DocumentsContract.deleteDocument(getContentResolver(), uri);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    private void editDocument(Uri uri) {
        try {
            ParcelFileDescriptor fileDescriptor = this.getContentResolver().openFileDescriptor(uri, "w");
            File file;
            FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor.getFileDescriptor());
            fileOutputStream.write(("android latest updates \n").getBytes());
            fileOutputStream.write(("android latest features \n").getBytes());
            fileOutputStream.close();
            fileDescriptor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFile(View view) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "zoftino.txt");
        startActivityForResult(intent, WRITE_REQ);

    }

    public void deleteFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("text/plain");
        startActivityForResult(intent, DELETE_REQ);
    }


}
