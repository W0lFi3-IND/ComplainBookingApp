package com.wolfie.regcomplain;

public class Complaints {
    private  String name , txt;
    private Long ph;

    public Complaints() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Long getPh() {
        return ph;
    }

    public void setPh(Long ph) {
        this.ph = ph;
    }
}
