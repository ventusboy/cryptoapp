package com.example.crypto;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Context context=this;

    private static Context mContext;


    //vars
    public ArrayList<String> mNames = new ArrayList<>();
    public ArrayList<String> mImageUrls =new ArrayList<>();
    private ArrayList<String> mPrices =new ArrayList<>();



    TinyDB tinydb;


    RequestQueue rq;

    public Button buttonget;
    String coinName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String jsonstring;

        tinydb=new TinyDB(this);

        //mContext = getApplicationContext();




        rq = Volley.newRequestQueue(this);
        buttonget=findViewById(R.id.button);

        mNames=tinydb.getListString("prevNames");
        mImageUrls=tinydb.getListString("prevImg");
        mPrices=tinydb.getListString("prevPrice");

        initImageBitmaps();




        buttonget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EditText text= findViewById(R.id.searchcoininput);

                coinName= text.getText().toString();

                if(coinName.matches("")){
                    for(String coin:mNames){
                        jsonrequest(coin);
                    }
                }
                else
                    jsonrequest(coinName);








            }
        });

    }

    public static Context getContext() {
        return mContext;
    }

    public class init{

        //TinyDB tinydb = new TinyDB(this);

    }


    private void initImageBitmaps(){
        //mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        //mNames.add("Havasu Falls");

        initRecyclerView();

    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.rView);
        recycleviewadapter adapter = new recycleviewadapter(this, mNames, mImageUrls, mPrices);
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

                        TextView text2= findViewById(R.id.usd_price);

                        try {

                            JSONObject data= response.getJSONObject("RAW");
                            JSONObject coinName= data.getJSONObject(coin1);
                            JSONObject info= coinName.getJSONObject("USD");

                            String price = info.getString("PRICE");

                            String imgurl= info.getString("IMAGEURL");

                            if(mNames.contains(coin1)){
                                //update price
                                mPrices.set(mNames.indexOf(coin1),price);

                            }
                            else{
                                mImageUrls.add("https://www.cryptocompare.com"+imgurl);
                                mNames.add(coin1);
                                mPrices.add(price);

                                tinydb.putListString("prevNames",mNames);
                                tinydb.putListString("prevImg",mImageUrls);
                                tinydb.putListString("prevPrice",mPrices);

                            }




                            text2.setText("$"+price);

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
                        TextView text= findViewById(R.id.usd_price);

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
