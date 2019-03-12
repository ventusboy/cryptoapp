package com.example.crypto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls =new ArrayList<>();

    RequestQueue rq;

    public Button buttonget;
    String coinName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String jsonstring;



        rq = Volley.newRequestQueue(this);
        buttonget=findViewById(R.id.button);






        buttonget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText text= (EditText)findViewById(R.id.searchcoininput);

                coinName= text.getText().toString();

                jsonrequest(coinName);


            }
        });

        initImageBitmaps();









    }

    private void initImageBitmaps(){
        //mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        //mNames.add("Havasu Falls");

        initRecyclerView();

    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.rView);
        recycleviewadapter adapter = new recycleviewadapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void jsonrequest(final String coin){

        //coin="eth";

        final String coin1 = coin.toUpperCase();

        String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms="+ coin1 + "&tsyms=USD&api_key=dcb8ee8a020fc0aae10aca956d92c330d4fa95e38433ba76caf58291f4b199a2";

        //TextView text=(TextView)findViewById(R.id.usd_price);

        // text.setText("test1");

        //TextView text=(TextView)findViewById(R.id.usd_price);
        //text.setText("Some text....");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        TextView text2=(TextView)findViewById(R.id.usd_price);

                        try {

                            JSONObject data= (JSONObject) response.getJSONObject("RAW");
                            JSONObject coinName= (JSONObject) data.getJSONObject(coin1);
                            JSONObject info= (JSONObject) coinName.getJSONObject("USD");

                            String price = (String) info.getString("PRICE");

                            String imgurl= (String) info.getString("IMAGEURL");

                            mImageUrls.add("https://www.cryptocompare.com"+imgurl);
                            mNames.add(coin1);


                            text2.setText(price);

                            initImageBitmaps();

                        } catch (JSONException e) {

                            text2.setText(coin1);
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        TextView text=(TextView)findViewById(R.id.usd_price);

                        text.setText("error");

                    }
                });



        /*String url = "https://min-api.cryptocompare.com/data/price?fsym="+ coin.toUpperCase() + "&tsyms=USD&api_key=dcb8ee8a020fc0aae10aca956d92c330d4fa95e38433ba76caf58291f4b199a2";

        //TextView text=(TextView)findViewById(R.id.usd_price);

       // text.setText("test1");

        //TextView text=(TextView)findViewById(R.id.usd_price);
        //text.setText("Some text....");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        TextView text2=(TextView)findViewById(R.id.usd_price);

                        try {
                            String price = (String) response.getString("USD");

                            //text.setText(response.get("USD"));
                            text2.setText(price);
                        } catch (JSONException e) {

                            text2.setText(coin1);
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        TextView text=(TextView)findViewById(R.id.usd_price);

                        text.setText("error");

                    }
                });*/
        rq.add(jsonObjectRequest);



    }
}
