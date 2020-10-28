package com.fricsoft.gocorona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.fricsoft.gocorona.MainActivity.world;

public class CountryActivity extends AppCompatActivity {

    TextView wConfirmed,wActive,wCured,wDeaths,wTime,wNewCases,wNewDeaths,wTests,wCritical,sortby;
    RecyclerView rCountry;
    ArrayList<Country> countries;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);


        wConfirmed=findViewById(R.id.wcases);
        wActive=findViewById(R.id.wactive);
        wCured=findViewById(R.id.wcured);
        wDeaths=findViewById(R.id.wdeaths);
        wTime=findViewById(R.id.wtime);
        wNewCases=findViewById(R.id.newcases);
        wNewDeaths=findViewById(R.id.newdeaths);
        wCritical=findViewById(R.id.critical);
        wTests=findViewById(R.id.tests);
        sortby=findViewById(R.id.sortby);
        sortby.setText("Confirmed Cases");

        rCountry=findViewById(R.id.recyclerView);
        rCountry.setLayoutManager(new LinearLayoutManager(this));
        setWorldData();
        getCountriesData();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void setWorldData()
    {
        if(world==null)finish();
        wConfirmed.setText(world.getCases());
        wDeaths.setText(world.getDeaths());
        wCured.setText(world.getRecovered());
        wActive.setText(world.getActive());
        wNewCases.setText(world.getNewCases());
        wNewDeaths.setText(world.getNewDeaths());
        wTests.setText(world.getTests());
        wCritical.setText(world.getCritical());

        long l=Long.parseLong(world.getUpdated());
        Timestamp timestamp = new Timestamp(l);
        Date date=new Date(timestamp.getTime());
        wTime.setText(date.toLocaleString());

    }

    public void getCountriesData()
    {
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://corona.lmao.ninja/v2/countries";
        countries=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null){
                    try {
                        System.out.println(response);

                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject data=jsonArray.getJSONObject(i);
                            JSONObject cInfo=data.getJSONObject("countryInfo");
                            countries.add(new Country(
                                    data.getString("country"),
                                    data.getString("cases"),
                                    data.getString("active"),
                                    data.getString("recovered"),
                                    data.getString("deaths"),
                                    data.getString("todayCases"),
                                    data.getString("todayDeaths"),
                                    data.getString("tests"),
                                    data.getString("critical"),
                                    data.getString("updated"),
                                    cInfo.getString("flag")
                            ));
                        }
                        Collections.sort(countries,Country.countryConfirmedComparator);
                        rCountry.setAdapter(new CountryAdapter(countries));
                        progressDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CountryActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    boolean b = true;
    public  void  anime(View v)
    {
        if(b)
            findViewById(R.id.wdata).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.wdata).setVisibility(View.GONE);
        b=!b;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.k,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cases:
                sortByConfirmed();
                return true;
            case R.id.active:
                sortByActive();
                return true;
            case R.id.recovered:
                sortByRecovered();
                return true;
            case R.id.deaths:
                sortByDeaths();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sorter(View v)
    {
        PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
        MenuInflater inflater=popupMenu.getMenuInflater();
        inflater.inflate(R.menu.k,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.cases:
                        sortByConfirmed();
                        return true;
                    case R.id.active:
                        sortByActive();
                        return true;
                    case R.id.recovered:
                        sortByRecovered();
                        return true;
                    case R.id.deaths:
                        sortByDeaths();
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void sortByConfirmed(){
        Collections.sort(countries,Country.countryConfirmedComparator);
        rCountry.setAdapter(new CountryAdapter(countries));
        sortby.setText("Confirmed Cases");
    }


    public void sortByActive(){
        Collections.sort(countries,Country.countryActiveComparator);
        rCountry.setAdapter(new CountryAdapter(countries));
        sortby.setText("Active Cases");
    }

    public void sortByRecovered(){
        Collections.sort(countries,Country.countryRecoveredComparator);
        rCountry.setAdapter(new CountryAdapter(countries));
        sortby.setText("Recovered");
    }


    public void sortByDeaths(){
        Collections.sort(countries,Country.countryDeathsComparator);
        rCountry.setAdapter(new CountryAdapter(countries));
        sortby.setText("Deaths");
    }



}
