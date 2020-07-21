package com.dat.yecxanh.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dat.yecxanh.R;
import com.dat.yecxanh.base.BaseFragment;
import com.dat.yecxanh.ui.FullScannerActivity;

public class TestFragment extends BaseFragment {

    private Button openScannerQrcode;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private static final int REQUEST_ACTIVITY_RESULT = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.test_layout_fragment;
    }

    @Override
    protected void bindView(View view) {
        openScannerQrcode = (Button) view.findViewById(R.id.openScannerQrcode);
    }

    @Override
    protected void initListener(View view) {
        openScannerQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(FullScannerActivity.class);
            }
        });
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(getActivity(), clss);
            intent.putExtra(FullScannerActivity.RESULT_DATA, FullScannerActivity.RESULT_ACTIVITY_SERIAL_SIM);
            startActivityForResult(intent, REQUEST_ACTIVITY_RESULT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mClss != null) {
                        Intent intent = new Intent(getActivity(), mClss);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACTIVITY_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra(FullScannerActivity.RESULT_ACTIVITY_SERIAL_SIM)) {
                    String result = data.getStringExtra(FullScannerActivity.RESULT_ACTIVITY_SERIAL_SIM);
                    Toast.makeText(getActivity(), "result = " + result, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
