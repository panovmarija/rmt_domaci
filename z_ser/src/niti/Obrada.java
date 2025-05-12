/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Korisnik;
import model.Stanovnik;
import model.Stavka;
import transfer.KlijentZahtev;
import transfer.ServerOdgovor;

/**
 *
 * @author USER
 */
public class Obrada extends Thread{
    private Socket s;
    private String sadrzaj_za_txt_fajl="";
    public Obrada(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        KlijentZahtev kz;
        while ((kz=procitaj())!=null) {            
            ServerOdgovor so=new ServerOdgovor();
            switch (kz.getOp()) {
                case operacije.Operacije.validacijaJedinstvenih:
                    so.setOdg(kontroler.Kontroler.getInstance().validacijaJedinstvenih((String[])kz.getPar()));
                    posalji(so);
                    break;
                case operacije.Operacije.registracija:
                    so.setOdg(kontroler.Kontroler.getInstance().registracija((Stanovnik)kz.getPar()));
                    posalji(so);
                    break;
                case operacije.Operacije.login:
                    so.setOdg(kontroler.Kontroler.getInstance().login((Korisnik)kz.getPar()));
                    posalji(so);
                    break;
                case operacije.Operacije.vrati_z:
                    so.setOdg(kontroler.Kontroler.getInstance().vrati_z());
                    posalji(so);
                    break;
                case operacije.Operacije.proveraPutovanja:
                    so.setOdg(kontroler.Kontroler.getInstance().proveraPutovanja((List<Stavka>)kz.getPar()));
                    posalji(so);
                    break;
                case operacije.Operacije.sacuvaj_prijavu:
                    so.setOdg(kontroler.Kontroler.getInstance().sacuvaj_prijavu((List<Stavka>)kz.getPar()));
                    posalji(so);
                    sadrzaj_za_txt_fajl=kontroler.Kontroler.getInstance().napravi_string((List<Stavka>)kz.getPar());
                    so.setOdg(sadrzaj_za_txt_fajl);
                    posalji(so);
                    break;
                case operacije.Operacije.vrati_prijave:
                    so.setOdg(kontroler.Kontroler.getInstance().vrati_prijave((Stanovnik)kz.getPar()));
                    posalji(so);
                    break;
                case operacije.Operacije.izmeni_prijavu:
                    so.setOdg(kontroler.Kontroler.getInstance().izmeni_prijavu((List<Stavka>)kz.getPar()));
                    posalji(so);
                    break;
                default:
                    throw new AssertionError();
            }
        }
        ugasiS();
    }
    
    public KlijentZahtev procitaj()
    {
        try {
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            return (KlijentZahtev) ois.readObject();
        } catch (IOException ex) {
            System.out.println("pad klijenta/mreze/gasenje forme");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Obrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void posalji(ServerOdgovor so)
    {
        try {
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Obrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ugasiS()
    {
        if(s!=null && !s.isClosed())
        {
            try {
                s.close();
                System.out.println("klijent soket ugasen: "+s.isClosed());
            } catch (IOException ex) {
                Logger.getLogger(Obrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
