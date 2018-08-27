package com.v.testapp;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by revinfotech on 1/22/2018.
 */

public class PleaseWaitDialog extends ProgressDialog {

    ProgressDialog progressDialog;
    String type = "";

    public PleaseWaitDialog(Context context, String type) {
        super(context);
        progressDialog = new ProgressDialog(context);
        this.type = type;

    }

    @Override
    public void dismiss(){
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
        /** dismiss the progress bar and clean up here **/
    }

    public void open(String msg) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        if(type.equals("horizontal")) {
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }

        try{
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

        final int totalProgressTime = 1000;

        final Thread t = new Thread() {

            @Override
            public void run() {

                int jumpTime = 0;
                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(50);
                        jumpTime += 5;
                        progressDialog.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };t.start();
    }
}