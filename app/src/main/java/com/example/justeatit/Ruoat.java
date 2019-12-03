package com.example.justeatit;

import androidx.annotation.NonNull;
/**
 * (C) Matias Hätönen
 */
public class Ruoat {
    private String ruoka;
    private int kalorit;
    private String aika;
    public Ruoat(String aika, String ruoka, int kalorit){
        this.aika = aika;
        this.ruoka = ruoka;
        this.kalorit = kalorit;
    }

    public String getRuoka(){
        return this.ruoka;
    }
    public String getKalorit(){
        return Integer.toString(this.kalorit);
    }
    public String getAika(){
        return this.aika;
    }

    @NonNull
    @Override
    public String toString() {
        return this.ruoka;
    }
}
