package com.dat.yecxanh.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.dat.yecxanh.R;
import com.dat.yecxanh.base.BaseScannerActivity;
import com.dat.yecxanh.fragment.CameraSelectorDialogFragment;
import com.dat.yecxanh.fragment.FormatSelectorDialogFragment;
import com.dat.yecxanh.fragment.MessageDialogFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FullScannerActivity extends BaseScannerActivity implements MessageDialogFragment.MessageDialogListener,
        ZXingScannerView.ResultHandler, FormatSelectorDialogFragment.FormatSelectorDialogListener,
        CameraSelectorDialogFragment.CameraSelectorDialogListener {
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


    public static void start(Context context) {
        Intent intent = new Intent(context, FullScannerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        if (state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }

        setContentView(R.layout.activity_simple_scanner);
        setupToolbar();

        try {
            resultData = getIntent().getExtras().getString(RESULT_DATA);
        } catch (Exception e) {
            resultData = "";
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        setupFormats();
        contentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
        }
        //showMessageDialog("Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().toString());
        if (!TextUtils.isEmpty(rawResult.getText())) {
            returnData(rawResult.getText());
        }
    }

    private void returnData(String code) {
        Intent intent = new Intent();
        intent.putExtra(resultData, code);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void showMessageDialog(String message) {
        DialogFragment fragment = MessageDialogFragment.newInstance("Scan Results", message, this);
        fragment.show(getSupportFragmentManager(), "scan_results");
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Resume the camera
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onFormatsSaved(ArrayList<Integer> selectedIndices) {
        mSelectedIndices = selectedIndices;
        setupFormats();
    }

    @Override
    public void onCameraSelected(int cameraId) {
        mCameraId = cameraId;
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if (mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for (int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for (int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if (mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }
}

