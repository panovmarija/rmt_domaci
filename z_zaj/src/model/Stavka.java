/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author USER
 */
public class Stavka implements Serializable{
   private Prijava p;
   private Zemlja z;

    public Stavka() {
    }

    public Stavka(Prijava p, Zemlja z) {
        this.p = p;
        this.z = z;
    }

    public Prijava getP() {
        return p;
    }

    public void setP(Prijava p) {
        this.p = p;
    }

    public Zemlja getZ() {
        return z;
    }

    public void setZ(Zemlja z) {
        this.z = z;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stavka other = (Stavka) obj;
        return Objects.equals(this.z, other.z);
    }
   
}
