/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Stavka;
import model.Zemlja;

/**
 *
 * @author USER
 */
public class t {
    public static void main(String[] args) {
        Date a=new Date(2025, 4, 10);
//        Date b=new Date(2021, 10, 20);
//        Date u=new Date(2021, 10, 9);
//        Date i=new Date(2021, 10, 11);
//        System.out.println(u.compareTo(b)<=0 && a.compareTo(i)<=0);
        
//        List<Stavka>l=new ArrayList<>();
//        Stavka a=new Stavka(null, new Zemlja(1, ""));
//        Stavka b=new Stavka(null, new Zemlja(1, ""));
//        Stavka c=new Stavka(null, new Zemlja(1, ""));
//        
//        l.add(a);
//        l.add(b);
//        l.add(c);
//        Date d=new Date();
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy.");
//        try {
//            d=format.parse(format.format(d));
//        } catch (ParseException ex) {
//            Logger.getLogger(t.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(a.equals(d));   
        String jmbg="1111111111111";
        int dan=Integer.parseInt(jmbg.substring(0, 2));
        int mesec=Integer.parseInt(jmbg.substring(2, 4));
        int godina=Integer.parseInt(jmbg.substring(4, 7));
        if(godina>=900)godina+=1000;
        else godina+=2000;
        try {
            format.parse(dan+"."+mesec+"."+godina);
        } catch (Exception e) {
            System.out.println("nije dobar format jmgba");
        }
    
    }
}
