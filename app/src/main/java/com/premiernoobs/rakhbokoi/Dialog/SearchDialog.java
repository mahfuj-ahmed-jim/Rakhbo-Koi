package com.premiernoobs.rakhbokoi.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;

import com.premiernoobs.rakhbokoi.R;

public class SearchDialog {

    private Activity activity;
    private Dialog searchDialog;
    public TextView cancelButton;

    public SearchDialog(Activity activity) {
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
        searchDialog.setContentView(R.layout.dialog_search);
        searchDialog.setCancelable(false);
        cancelButton = searchDialog.findViewById(R.id.cancelButton_id);
    }

    // internet checking
    public  boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        // check wifi & mobile data
        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileDataConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        // check connection
        if(wifiConnection!=null && wifiConnection.isConnected() || mobileDataConnection!=null && mobileDataConnection.isConnected()) {
            return true;
        }else{
            return false;
        }
    }

}
