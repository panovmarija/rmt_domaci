/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
public class Prijava implements Serializable{
private int id;
private Stanovnik s;
private Date du, di;
private String np;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stanovnik getS() {
        return s;
    }

    public void setS(Stanovnik s) {
        this.s = s;
    }

    public Date getDu() {
        return du;
    }

    public void setDu(Date du) {
        this.du = du;
    }

    public Date getDi() {
        return di;
    }

    public void setDi(Date di) {
        this.di = di;
    }


    public String getNp() {
        return np;
    }

    public void setNp(String np) {
        this.np = np;
    }

    public Prijava(int id, Stanovnik s, Date du, Date di, String np) {
        this.id = id;
        this.s = s;
        this.du = du;
        this.di = di;
        this.np = np;
    }



    public Prijava() {
    }

}
