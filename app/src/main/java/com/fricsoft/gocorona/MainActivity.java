package com.fricsoft.gocorona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.Permissions;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView wConfirmed,wActive,wCured,wDeaths,wTime,iConfirmed,iActive,iCured,iDeaths,iTime;
    SwipeRefreshLayout swipeRefreshLayout;
    public  static Country world,india;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wConfirmed=findViewById(R.id.wcases);
        wActive=findViewById(R.id.wactive);
        wCured=findViewById(R.id.wcured);
        wDeaths=findViewById(R.id.wdeaths);
        wTime=findViewById(R.id.wtime);
        iConfirmed=findViewById(R.id.icases);
        iActive=findViewById(R.id.iactive);
        iCured=findViewById(R.id.icured);
        iDeaths=findViewById(R.id.ideaths);
        iTime=findViewById(R.id.itime);
        swipeRefreshLayout=findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                work();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        work();
    }

    public void work()
    {
        getWorldData();
        getIndiaData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        work();
    }

    public void getWorldData()
    {
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://corona.lmao.ninja/v2/all";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    wConfirmed.setText(jsonObject.getString("cases"));
                    wDeaths.setText(jsonObject.getString("deaths"));
                    wCured.setText(jsonObject.getString("recovered"));
                    wActive.setText(jsonObject.getString("active"));
                    long l=Long.parseLong(jsonObject.getString("updated"));
                    Timestamp timestamp = new Timestamp(l);
                    Date date=new Date(timestamp.getTime());
                    wTime.setText(date.toLocaleString());

                    world=new Country("World",
                            jsonObject.getString("cases"),
                            jsonObject.getString("active"),
                            jsonObject.getString("recovered"),
                            jsonObject.getString("deaths"),
                            jsonObject.getString("todayCases"),
                            jsonObject.getString("todayDeaths"),
                            jsonObject.getString("tests"),
                            jsonObject.getString("critical"),
                            jsonObject.getString("updated"),
                            ""
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "xa"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void getIndiaData()
    {
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://corona.lmao.ninja/v2/countries/india";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    iConfirmed.setText(jsonObject.getString("cases"));
                    iDeaths.setText(jsonObject.getString("deaths"));
                    iCured.setText(jsonObject.getString("recovered"));
                    iActive.setText(jsonObject.getString("active"));
                    long l=Long.parseLong(jsonObject.getString("updated"));
                    Date date=new Date(l);
                    iTime.setText(date.toLocaleString());
                    india=new Country("India",
                            jsonObject.getString("cases"),
                            jsonObject.getString("active"),
                            jsonObject.getString("recovered"),
                            jsonObject.getString("deaths"),
                            jsonObject.getString("todayCases"),
                            jsonObject.getString("todayDeaths"),
                            jsonObject.getString("tests"),
                            jsonObject.getString("critical"),
                            jsonObject.getString("updated"),
                            ""
                    );

                    swipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "xb"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }


    public void openCountryActivity(View v)
    {
        startActivity(new Intent(this,CountryActivity.class));

    }

    public void openStateActivity(View v)
    {
        startActivity(new Intent(this,StateActivity.class));

    }

    public void openWebActivity(View v){
        startActivity(new Intent(this,WebActivity.class).putExtra("url","https://drive.google.com/file/d/18ovLF9iCiSpKVBD33O0Vk5ft0uflJuUR/view?usp=drivesdk"));
    }

    public void openPayActivity(View v)
    {
        String spaytm="net.one97.paytm",
                sbhim="in.org.npci.upiapp",
                sgpay="com.google.android.apps.nbu.paisa.user",
                sphonepe="com.phonepe.app";
        Intent paytm = getPackageManager().getLaunchIntentForPackage(spaytm);
        Intent bhim = getPackageManager().getLaunchIntentForPackage(sbhim);
        Intent gpay = getPackageManager().getLaunchIntentForPackage(sgpay);
        Intent phonepe = getPackageManager().getLaunchIntentForPackage(sphonepe);
        Intent intent=i(sbhim)?bhim : i(spaytm)?paytm:i(sphonepe)?phonepe:i(sgpay)?gpay:null;
        if(intent==null)
        {
            Toast.makeText(this, "No Payment App Installed!", Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(intent);
    }

    public boolean i(String p)
    {
        try {
            getPackageManager().getPackageInfo(p,0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    public void playVideo2(View v) {
        Intent intent=new Intent(this,WebActivity.class);
        intent.putExtra("url",
                "https://www.youtube.com/playlist?list=PL68fssGDuh3xZGDIxpb1-DqG9TEJAZm3e&v=hZch8NXQa5A&index=1");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_k_drawer,menu);
        return true;
    }


    public void setu(View v)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=nic.goi.aarogyasetu"));
        startActivity(intent);
    }

    public void playVideo(View v)
    {
        Intent intent=new Intent(this,WebActivity.class);
        intent.putExtra("url","https://www.youtube.com/watch?v=4HX6LrAEsb0");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                openCountryActivity(new View(this));
                return true;
            case R.id.nav_gallery:
                openStateActivity(new View(this));
                return true;
            case R.id.nav_slideshow:
                playVideo2(new View(this));
                return true;
            case R.id.nav_x:
                sendMail();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendMail(){

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=918178182087"));//&text=&source=&data=&app_absent="));
        startActivity(intent);
/*
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:9910902666"));
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_PHONE_NUMBER, "9911902666");
            intent.putExtra(Intent.EXTRA_TEXT, "From GoCorona app.");
            //intent.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(intent,"HY"));
        }
        catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
*/

        //intent.putExtra(Intent.EXTRA_TEXT," Download GoCorona App for Google Play Store for statistics: https://play.google.com/store/apps/details?id=com.fricsoft.gocorona");
        //startActivity(Intent.createChooser(intent,"Send Email..."));
    }

    public void alert()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        TextView tv=new TextView(this);
        tv.setText("cc");
        builder.setView(tv);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
    }
}