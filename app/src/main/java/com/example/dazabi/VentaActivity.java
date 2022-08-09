package com.example.dazabi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class VentaActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Canal";

    String id,idR;

    ImageView img;
    Bundle Data;
    String extra;
    TextView producto0,producto1,producto2,producto3,producto4,producto5,producto6,producto7,producto8,producto9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        img = (ImageView)findViewById(R.id.img);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            extra = bundle.getString("data");
            transformar(extra);
            /*try {
                Toast.makeText(this, extra, Toast.LENGTH_SHORT).show();

            } catch (Throwable t) {
                Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
            }*/
        }

        //Data = getIntent().getExtras();
        //String datos = Data.getString("data");
        producto0 = findViewById(R.id.producto0);
    }

    public void transformar(String data){
        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = "https://dgs801.com/desencriptar.php?data="+data;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        RequestQueue requestQueue;

                        // Instantiate the cache
                        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

                        // Set up the network to use HttpURLConnection as the HTTP client.
                        Network network = new BasicNetwork(new HurlStack());

                        // Instantiate the RequestQueue with the cache and network.
                        requestQueue = new RequestQueue(cache, network);

                        // Start the queue
                        requestQueue.start();


                        String dato0 = null;
                        try {
                            dato0 = response.getString("recompensa");
                            idR = dato0;
                            id = response.getString("user");
                            Toast.makeText(VentaActivity.this, id+idR, Toast.LENGTH_SHORT).show();
                            String urlx = "https://dazabi-nodejs.azurewebsites.net/recompensas/"+dato0;

                            JsonObjectRequest jsonObjectRequestx = new JsonObjectRequest
                                    (Request.Method.GET, urlx, null, new Response.Listener<JSONObject>(){
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Glide.with(getApplication()).load(response.getString("img")).into(img);
                                                producto0.setText(response.getString("recompensa"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // TODO: Handle error
                                            Toast.makeText(VentaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            requestQueue.add(jsonObjectRequestx);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(VentaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

    public void venta(View v){
        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = "https://dazabi-nodejs.azurewebsites.net/recompensas/phone/"+idR+"/"+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(VentaActivity.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplication(), MenuActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(VentaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}