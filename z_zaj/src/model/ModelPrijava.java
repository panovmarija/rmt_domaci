/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class ModelPrijava extends AbstractTableModel{
    private List<WrapPrijava>l;
    private String []kol=new String[]{"Datum ulaska", "Datum izlaska", "Nacin putovanja", "Zemlje", "Status"};

    public ModelPrijava(List<WrapPrijava> l) {
        this.l = l;
    }

    @Override
    public int getRowCount() {
        return l.size();
    }

    @Override
    public int getColumnCount() {
        return kol.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WrapPrijava z=l.get(rowIndex);
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy.");
        switch (columnIndex) {
            case 0:
                return sdf.format(z.getP().getDu());
            case 1:
                return sdf.format(z.getP().getDi());
            case 2:
                return z.getP().getNp();
            case 3:
                return z.getZemlje();
            case 4:
                Date dnscist=null;
                Date ducist=null;
                Date dicist=null;
                try {
                    dnscist=sdf.parse(sdf.format(new Date()));
                    ducist=sdf.parse(sdf.format(z.getP().getDu()));
                    dicist=sdf.parse(sdf.format(z.getP().getDi()));
                } catch (ParseException ex) {
                    Logger.getLogger(ModelPrijava.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(dicist.before(dnscist))return "ZAVRSENA";
                int brs= (int) (ducist.getTime()-dnscist.getTime())/1000/60/60;
                if(brs<48 && brs>=0)return "ZAKLJUCANA";
                return "U OBRADI";

            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return kol[column];
    }

    public List<WrapPrijava> getL() {
        return l;
    }
    
}
