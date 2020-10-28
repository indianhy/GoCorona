package com.fricsoft.gocorona;

import java.util.Comparator;

public class Country {
    private String country,cases,deaths,flagURL,active,recovered,newCases,newDeaths,tests,critical,updated;

    public Country(String country, String cases,String active,String recovered, String deaths,String newCases,String newDeaths,String tests,String critical,String updated,String flagURL) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.flagURL=flagURL;
        this.active=active;
        this.recovered=recovered;
        this.newCases=newCases;
        this.newDeaths=newDeaths;
        this.tests=tests;
        this.critical=critical;
        this.updated=updated;
    }

    public String getCountry() {
        return country;
    }

    public String getCases() {
        return cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public String getActive() {
        return active;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getNewCases() {
        return newCases;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public String getTests() {
        return tests;
    }

    public String getCritical() {
        return critical;
    }

    public String getUpdated() {
        return updated;
    }

    public static Comparator<Country> countryConfirmedComparator=new Comparator<Country>() {
        @Override
        public int compare(Country country0, Country country1) {
            int cases0=Integer.parseInt(country0.getCases());
            int cases1=Integer.parseInt(country1.getCases());
            return cases1-cases0;
        }
    };

    public static Comparator<Country> countryActiveComparator=new Comparator<Country>() {
        @Override
        public int compare(Country country0, Country country1) {
            int cases0=Integer.parseInt(country0.getActive());
            int cases1=Integer.parseInt(country1.getActive());
            return cases1-cases0;
        }
    };
    public static Comparator<Country> countryRecoveredComparator=new Comparator<Country>() {
        @Override
        public int compare(Country country0, Country country1) {
            int cases0=Integer.parseInt(country0.getRecovered());
            int cases1=Integer.parseInt(country1.getRecovered());
            return cases1-cases0;
        }
    };
    public static Comparator<Country> countryDeathsComparator=new Comparator<Country>() {
        @Override
        public int compare(Country country0, Country country1) {
            int cases0=Integer.parseInt(country0.getDeaths());
            int cases1=Integer.parseInt(country1.getDeaths());
            return cases1-cases0;
        }
    };

}
