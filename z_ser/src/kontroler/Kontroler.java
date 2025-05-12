/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import baza.DBB;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Korisnik;
import model.Prijava;
import model.Stanovnik;
import model.Stavka;
import model.WrapPrijava;
import model.Zemlja;

/**
 *
 * @author USER
 */
public class Kontroler {
    private DBB dbb;
    private static Kontroler instance;

    private Kontroler() {
    dbb=new DBB();
    }

    public static Kontroler getInstance() {
    if(instance==null)
        instance=new Kontroler();
        return instance;
    }

    public String validacijaJedinstvenih(String[] par) {
        return dbb.validacijaJedinstvenih(par);
    }

    public boolean registracija(Stanovnik stanovnik) {
        return dbb.registracija(stanovnik);
    }

    public Stanovnik login(Korisnik korisnik) {
        return dbb.login(korisnik);
    }

    public List<Zemlja> vrati_z() {
        return dbb.vrati_z();
    }

    public boolean proveraPutovanja(List<Stavka> list) {
        return dbb.proveraPutovanja(list);
    }

    public boolean sacuvaj_prijavu(List<Stavka> list) {
        return dbb.sacuvaj_prijavu(list);
    }

    public List<WrapPrijava> vrati_prijave(Stanovnik s) {
        return dbb.vrati_prijave(s);
    }

    public boolean izmeni_prijavu(List<Stavka> list) {
        return dbb.izmeni_prijavu(list);
    }

    public String napravi_string(List<Stavka> list) {
        Prijava p=list.get(0).getP();
        Stanovnik s=p.getS();
        String [] ip=dbb.vratiIP(s);
        s.setIme(ip[0]);
        s.setPrezime(ip[1]);
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy.");
        List<String>zemlje=new ArrayList<>();
        for(Stavka stavka:list)
        {
            zemlje.add(stavka.getZ().getNaz());
        }
        int dan=Integer.parseInt(s.getJmbg().substring(0, 2));
        int mesec=Integer.parseInt(s.getJmbg().substring(2, 4));
        int godina=Integer.parseInt(s.getJmbg().substring(4, 7));
        if(godina>=900)godina+=1000;
        else godina+=2000;

        Date dicist=null;
        Date ducist=null;
        Date drodj=null;
        try {
             ducist=sdf.parse(sdf.format(p.getDu()));
            dicist=sdf.parse(sdf.format(p.getDi()));
            drodj=sdf.parse(dan+"."+mesec+"."+godina+".");
        } catch (ParseException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        int brdana=(int) ((dicist.getTime()-ducist.getTime())/1000/60/60/24);

        LocalDate dns=LocalDate.now();
        LocalDate rodj=LocalDate.of(godina, mesec, dan);
        int brgod=Period.between(rodj, dns).getYears();
         String str="Ime: "+s.getIme()+"\nPrezime: "+s.getPrezime()+"\nJMBG: "+s.getJmbg()+"\nBroj pasosa: "+s.getBp()+"\nZemlja/Zemlje odredista: "+String.join(",", zemlje)
                +"\nDatum prijave: "+sdf.format(new Date())+"\nDatum ulaska u EU: "+sdf.format(p.getDu())+"\nPredvidjen broj dana u EU: "+brdana+"\nPlaca prijavu: "+(brgod>=18 && brgod<=70?"Da":"Ne");
       return str;
    }
    
}
