package com.v.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView mainactivity;
    // creating arraylist of MyItem type to set to adapter
    ArrayList<MyItem> myitems = new ArrayList<>();
    CustomAdapter customAdapter;
    PleaseWaitDialog pleaseWaitDialog;
    TextView tvTotal;
    EditText etNum;
    String etNumGet = "1";
    int preSelectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        getSupportActionBar().hide();
        mainactivity = (ListView) findViewById(R.id.mainactivitylistview);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        etNum = (EditText) findViewById(R.id.etNum);

        mainactivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = String.valueOf(i);
                tvTotal.setText(name);

                MyItem model = myitems.get(i);

                if (model.isSelected())
                    model.setSelected(false);

                else
                    model.setSelected(true);

                myitems.set(i, model);

                //now update adapter
                customAdapter.updateRecords(myitems);
            }
        });


        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    etNumGet = etNum.getText().toString();
                    callGetMethod(etNumGet);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                // print bfx text in this method

            }
        });

        callGetMethod(etNumGet);
    }


    private void callGetMethod(String etNumGet) {

        String url = Config.url + etNumGet;

        pleaseWaitDialog = new PleaseWaitDialog(MainActivity.this, "vertical");
        pleaseWaitDialog.open("Loading\nPlease wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pleaseWaitDialog.dismiss();

                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("hits");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MyItem myItem = new MyItem();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String created_at = jsonObject.getString("created_at");
                                String title = jsonObject.getString("title");

                                String string = created_at;
                                String[] parts = string.split("T");
                                String part1 = parts[0]; // 004
                                String part2 = parts[1]; // 034556

                                String time = part2;
                                String[] part = time.split("\\.");
                                String first = part[0];
                                String second = part[1];


                                myItem.setCreate(part1);
                                myItem.setTitle(title);
                                myItem.setSelected(false);
                                myItem.setTime(first);
                                myitems.add(myItem);
                                customAdapter = new CustomAdapter(MainActivity.this, myitems);
                                mainactivity.setAdapter(customAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pleaseWaitDialog.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        int socketTimeout = 30000; //30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
