/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class WrapPrijava implements Serializable{
    private Prijava p;
    private String zemlje;
    
    public WrapPrijava() {
    }

    public WrapPrijava(Prijava p, String zemlje) {
        this.p = p;
        this.zemlje = zemlje;
    }

    public Prijava getP() {
        return p;
    }

    public void setP(Prijava p) {
        this.p = p;
    }

    public String getZemlje() {
        return zemlje;
    }

    public void setZemlje(String zemlje) {
        this.zemlje = zemlje;
    }
    
}
