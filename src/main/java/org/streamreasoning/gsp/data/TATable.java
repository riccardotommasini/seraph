package org.streamreasoning.gsp.data;

import jakarta.persistence.Entity;

@Entity
public class TATable extends AbstractEntity {

    private String UID;
    private String SID;
    private String hops;
    private String val_time;
    private String win_start;
    private String win_end;

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


    public String getUID() {
        return UID;
    }
    public void setUID(String firstName) {
        this.UID = firstName;
    }
    public String getSID() {
        return SID;
    }
    public void setSID(String lastName) {
        this.SID = lastName;
    }
    public String getVal_time() {
        return val_time;
    }
    public void setVal_time(String email) {
        this.val_time = email;
    }
    public String isHops() {
        return hops;
    }
    public void setHops(String important) {
        this.hops = important;
    }

}
