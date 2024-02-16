package org.streamreasoning.gsp.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class SPOTAT {

    private String s;
    private String p;
    private String o;
    private String val_time;
    private String win_start;
    private String win_end;

    public SPOTAT(String s, String p, String o, String val_time, String win_start, String win_end) {
        this.s = s;
        this.p = p;
        this.o = o;
        this.val_time = val_time;
        this.win_start = win_start;
        this.win_end = win_end;
    }

    public SPOTAT() {

    }

    public String getWin_start() {
        return win_start;
    }

    public void setWin_start(String win_start) {
        this.win_start = win_start;
    }

    public String getWin_end() {
        return win_end;
    }

    public void setWin_end(String win_end) {
        this.win_end = win_end;
    }

    public String getS() {
        return s;
    }

    public void setS(String firstName) {
        this.s = firstName;
    }

    public String getP() {
        return p;
    }

    public void setP(String lastName) {
        this.p = lastName;
    }

    public String getVal_time() {
        return val_time;
    }

    public void setVal_time(String email) {
        this.val_time = email;
    }

    public void setO(String important) {
        this.o = important;
    }

    public String getO() {
        return o;
    }

}
