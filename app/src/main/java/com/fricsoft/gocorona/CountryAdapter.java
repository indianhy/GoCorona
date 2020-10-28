package com.fricsoft.gocorona;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    ArrayList<Country> countries;


    public CountryAdapter(ArrayList<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.country_card,parent,false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        Country country=countries.get(position);
        holder.cases.setText(country.getCases());
        holder.active.setText(country.getActive());
        holder.recovered.setText(country.getRecovered());
        holder.deaths.setText(country.getDeaths());
        holder.country.setText(country.getCountry());
        //if(flags.get(position)==null)
            //new DownloadImageTask(holder.flag).execute(country.getFlagURL());
        //else
            holder.flag.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cases,country,active,recovered,deaths;
        ImageView flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cases=itemView.findViewById(R.id.cases);
            country=itemView.findViewById(R.id.country);
            active=itemView.findViewById(R.id.active);
            recovered=itemView.findViewById(R.id.cured);
            deaths=itemView.findViewById(R.id.deaths);
            flag=itemView.findViewById(R.id.flag);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            bmImage.setImageBitmap(result);
        }
    }

}
