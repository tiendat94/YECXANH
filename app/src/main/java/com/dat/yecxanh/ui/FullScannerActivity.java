package com.dat.yecxanh.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;


import com.dat.yecxanh.R;
import com.dat.yecxanh.base.BaseActivity;
import com.dat.yecxanh.fragment.CameraSelectorDialogFragment;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FullScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler, CameraSelectorDialogFragment.CameraSelectorDialogListener {

    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    public static final String RESULT_DATA = "RESULT_DATA";
    private String resultData = "";

    public static final String RESULT_ACTIVITY_SERIAL_SIM = "RESULT_ACTIVITY_SERIAL_SIM";
    public static final String RESULT_ACTIVITY_DNI = "RESULT_ACTIVITY_DNI";
    public static final String RESULT_ACTIVITY_IMEI_HANDSET = "RESULT_ACTIVITY_IMEI_HANDSET";

    @Override
    protected String getTitleScreen() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_simple_scanner;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFlash = savedInstanceState.getBoolean(FLASH_STATE, false);
            mAutoFocus = savedInstanceState.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = savedInstanceState.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = savedInstanceState.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }

        try {
            resultData = getIntent().getExtras().getString(RESULT_DATA);
        } catch (Exception e) {
            resultData = "";
            e.printStackTrace();
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    @Override
    protected void afteriInitView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onCameraSelected(int cameraId) {
        mCameraId = cameraId;
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(rawResult.getText())) {
            returnData(rawResult.getText());
        }
    }

    private void returnData(String code) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(resultData, code);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
