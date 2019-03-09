package com.example.crypto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    RequestQueue rq;

    public Button buttonget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String jsonstring;


        rq = Volley.newRequestQueue(this);
        buttonget=findViewById(R.id.button);

        jsonrequest();

        buttonget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });







    }

    public void jsonrequest(){



        String url = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD&api_key=dcb8ee8a020fc0aae10aca956d92c330d4fa95e38433ba76caf58291f4b199a2";

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
                            String name = (String) response.getString("USD");

                            //text.setText(response.get("USD"));
                            text2.setText(name);
                        } catch (JSONException e) {

                            text2.setText("error");
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
        rq.add(jsonObjectRequest);

    }
}
