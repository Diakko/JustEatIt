package com.example.justeatit;

import androidx.annotation.NonNull;

public class Ruoat {
    private String ruoka;
    private int kalorit;
    public Ruoat(String ruoka, int kalorit){
        this.ruoka = ruoka;
        this.kalorit = kalorit;
    }

    public String getRuoka(){
        return this.ruoka;
    }
    public String getKalorit(){
        return Integer.toString(this.kalorit);
    }

    @NonNull
    @Override
    public String toString() {
        return this.ruoka;
    }
}
