package com.elab.consume.utils.tools;

import com.elab.consume.checker.compagne.User;

import java.util.Date;

/**
 * Created by NAWAR on 27/02/2018.
 */

public class CommonCriterias {
    private Date dateDebut;
    private Date dateFin;
     private User user;
    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
