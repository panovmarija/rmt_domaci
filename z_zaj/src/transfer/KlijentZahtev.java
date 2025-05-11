/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class KlijentZahtev implements Serializable {
        private int op;
    private Object par;

    public KlijentZahtev() {
    }

    public KlijentZahtev(int op, Object par) {
        this.op = op;
        this.par = par;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public Object getPar() {
        return par;
    }

    public void setPar(Object par) {
        this.par = par;
    }
    

}
