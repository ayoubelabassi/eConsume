package com.elab.consume.checker.expence;

import java.util.Date;

/**
 * Created by AYOUB on 11/03/2018.
 */

public class MonthExpence {
    private Date date;
    private float montant;

    public MonthExpence(Date date, float montant) {
        this.date = date;
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }


}
