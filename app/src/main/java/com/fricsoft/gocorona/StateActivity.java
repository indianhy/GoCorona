package com.fricsoft.gocorona;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.fricsoft.gocorona.MainActivity.india;

public class StateActivity extends AppCompatActivity {

    TextView wConfirmed,wActive,wCured,wDeaths,wTime,wNewCases,wNewDeaths,wTests,wCritical,sortby;
    RecyclerView rStates;
    ArrayList<State> states;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

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
        rStates=findViewById(R.id.recyclerView);
        rStates.setLayoutManager(new LinearLayoutManager(this));
        try {
            setIndiaData();
            getStatesData();
        }
        catch (Exception e){
            finish();
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void setIndiaData()
    {
        if(india==null)finish();
        Country world=india;
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

    public void getStatesData()
    {
        states=new ArrayList<>();
        new StateTask().execute();

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

    private class StateTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String url="https://www.mohfw.gov.in";
            try {
                Document doc= Jsoup.connect(url).get();

                outer: for(Element table:doc.select("table[class=table table-striped]"))
                {
                    for(Element row:table.select("tr:gt(0)")){
                        Elements tds=row.select("td");
                        State x=new State(tds.get(1).text(),tds.get(2).text(),tds.get(3).text(),tds.get(4).text());
                        states.add(x);
                        if(tds.get(1).text().equals("West Bengal")) break outer;
                    }
                }




            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Collections.sort(states,State.stateConfirmedComparator);
            rStates.setAdapter(new StateAdapter(states));
            progressDialog.dismiss();
        }
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
        Collections.sort(states,State.stateConfirmedComparator);
        rStates.setAdapter(new StateAdapter(states));
        sortby.setText("Confirmed Cases");
    }

    public void sortByActive(){
        Collections.sort(states,State.stateActiveComparator);
        rStates.setAdapter(new StateAdapter(states));
        sortby.setText("Active Cases");
    }

    public void sortByRecovered(){
        Collections.sort(states,State.stateRecoveredComparator);
        rStates.setAdapter(new StateAdapter(states));
        sortby.setText("Recovered");
    }

    public void sortByDeaths(){
        Collections.sort(states,State.stateDeathsComparator);
        rStates.setAdapter(new StateAdapter(states));
        sortby.setText("Deaths");
    }



}
