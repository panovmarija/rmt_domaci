/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class ModelZemlja extends AbstractTableModel{
    private List<Zemlja>l;
    private String []kol=new String[]{"Unete zemlje"};

    public ModelZemlja(List<Zemlja> l) {
        this.l = l;
    }

    @Override
    public int getRowCount() {
        return l.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zemlja z=l.get(rowIndex);
        return z.getNaz();
    }

    @Override
    public String getColumnName(int column) {
        return kol[column];
    }

    public List<Zemlja> getL() {
        return l;
    }
    
}
