package com.premiernoobs.rakhbokoi.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;

import com.premiernoobs.rakhbokoi.R;

public class OtpDialog {

    private Activity activity;
    private Dialog searchDialog;
    public TextView directionButton, otpMessage, slotNumberTextView;

    public OtpDialog(Activity activity) {
        this.activity = activity;
        setUpDialog();
    }

    public void show(){
        searchDialog.show();
    }

    public void dismiss(){
        searchDialog.dismiss();
    }

    private void setUpDialog() {
        searchDialog = new Dialog(activity);
        searchDialog.setContentView(R.layout.dialog_otp);
        searchDialog.setCancelable(false);
        directionButton = searchDialog.findViewById(R.id.cancelButton_id);
        otpMessage = searchDialog.findViewById(R.id.textViewId_otp);
        slotNumberTextView = searchDialog.findViewById(R.id.textViewId_slot);
    }

}
