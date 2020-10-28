package com.fricsoft.gocorona;

import java.util.Comparator;

public class State{
    private String state,cases,deaths,recovered;

    public State(String state, String cases,String recovered,String deaths) {
        this.state = state;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered=recovered;
    }

    public String getState() {
        return state;
    }

    public String getCases() {
        return cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getActive() {
        int cases=Integer.parseInt(getCases());
        int deaths=Integer.parseInt(getDeaths());
        int rec=Integer.parseInt(getRecovered());
        return ""+(cases-deaths-rec);
    }

    public static Comparator<State> stateConfirmedComparator=new Comparator<State>() {
        @Override
        public int compare(State state0, State state1) {
            int cases0=Integer.parseInt(state0.getCases());
            int cases1=Integer.parseInt(state1.getCases());
            return cases1-cases0;
        }
    };

    public static Comparator<State> stateActiveComparator=new Comparator<State>() {
        @Override
        public int compare(State state0, State state1) {
            int cases0=Integer.parseInt(state0.getActive());
            int cases1=Integer.parseInt(state1.getActive());
            return cases1-cases0;
        }
    };
    public static Comparator<State> stateRecoveredComparator=new Comparator<State>() {
        @Override
        public int compare(State state0, State state1) {
            int cases0=Integer.parseInt(state0.getRecovered());
            int cases1=Integer.parseInt(state1.getRecovered());
            return cases1-cases0;
        }
    };
    public static Comparator<State> stateDeathsComparator=new Comparator<State>() {
        @Override
        public int compare(State state0, State state1) {
            int cases0=Integer.parseInt(state0.getDeaths());
            int cases1=Integer.parseInt(state1.getDeaths());
            return cases1-cases0;
        }
    };
}
