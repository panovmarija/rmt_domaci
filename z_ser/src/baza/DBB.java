/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

/**
 *
 * @author USER
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Korisnik;
import model.Prijava;
import model.Stanovnik;
import model.Stavka;
import model.WrapPrijava;
import model.Zemlja;

public class DBB {
//proverava da li postoji korisnik sa usernameom, ako postoji mora da izabere drugi za registraciju. ne proverava za unos prijave, tu je par[0] prazan string 
//proverava da li postoji stanovnik sa tim jmbgom i brojem pasosa i da li taj stanovnik ima nalog. ako ima morace da se ulpguje. i za registraciju i za unos prijave
//    ako uopste ne postoji takav (jmbg, brpasosa) u stanovnik, vraca gresku
    public String validacijaJedinstvenih(String[] par) {
        try {
            String u = "select * from korisnik where korisnickoime =?";
            PreparedStatement ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setString(1, par[0]);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "Vec postoji korisnik sa datim korisnickim imenom!";
            }

            u = "select *, case when k.korisnikid is null then \"nije registrovan\" else \"registrovan\" end r\n"
                    + "from korisnik k right join stanovnik s on(s.korisnikid=k.korisnikid)\n"
                    + "where jmbg=? and brojpasosa=?";
            ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setString(1, par[1]);
            ps.setString(2, par[2]);
            rs = ps.executeQuery();
            if (rs.next()) {
                String r = rs.getString("r");
                System.out.println(rs.getInt("s.stanovnikid"));
                if (r.equals("registrovan")) {
                    return "Vec postoji nalog.\n Ulogujte se!";
                }
            } else {
                return "Ne postoji takav stanovnik. Los unos jmbg i/ili broja pasosa";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ok";
    }
    public boolean registracija(Stanovnik stanovnik) {
        try {
            int id = 1;
            String u = "INSERT INTO korisnik (korisnickoime, mail, lozinka) VALUES (?,?,?)";
            PreparedStatement ps = Konekcija.getInstance().getConn().prepareStatement(u, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, stanovnik.getK().getUser());
            ps.setString(2, stanovnik.getK().getMail());
            ps.setString(3, stanovnik.getK().getPass());
            int afr = ps.executeUpdate();
            if (afr == 0) {
                return false;
            }
            ResultSet rsid = ps.getGeneratedKeys();
            if (rsid.next()) {
                id = rsid.getInt(1);
                System.out.println(id);
            } else {
                return false;
            }
            u = "UPDATE stanovnik SET korisnikid=? WHERE jmbg=? AND  brojpasosa=?";
            ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setInt(1, id);
            ps.setString(2, stanovnik.getJmbg());
            ps.setString(3, stanovnik.getBp());
            ps.executeUpdate();
            Konekcija.getInstance().getConn().commit();
        } catch (SQLException ex) {
            try {
                Konekcija.getInstance().getConn().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public Stanovnik login(Korisnik korisnik) {
        try {
            String u = "select * from stanovnik s join korisnik k on(s.korisnikid=k.korisnikid) where korisnickoime=? and lozinka=?";
            PreparedStatement ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setString(1, korisnik.getUser());
            ps.setString(2, korisnik.getPass());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                korisnik = new Korisnik(rs.getInt("korisnikid"), rs.getString("korisnickoime"), rs.getString("mail"), rs.getString("lozinka"));
                Stanovnik s = new Stanovnik(rs.getInt("stanovnikid"), rs.getString("ime"), rs.getString("prezime"), rs.getString("jmbg"), rs.getString("brojpasosa"), korisnik);
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Stanovnik();
    }
    public List<Zemlja> vrati_z() {
        List<Zemlja> l = new ArrayList<>();
        try {
            String u = "select * from zemlja";
            Statement s = Konekcija.getInstance().getConn().createStatement();
            ResultSet rs = s.executeQuery(u);
            while (rs.next()) {
                Zemlja z = new Zemlja(rs.getInt("zemljaid"), rs.getString("naziv"));
                l.add(z);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }
    public boolean proveraPutovanja(List<Stavka> list) {
        try {
            Prijava p = list.get(0).getP();
            java.util.Date unov = p.getDu();
            java.util.Date inov = p.getDi();

            String u = "SELECT p.datumulaska du, p.datumizlaska di, z.zemljaid zid\n"
                    + "FROM prijava p JOIN stavkaprijave sp ON(sp.prijavaid=p.prijavaid) JOIN zemlja z ON (z.zemljaid=sp.zemljaid) join stanovnik s on(s.stanovnikid = p.stanovnikid)\n"
                    + "WHERE s.jmbg=? AND s.brojpasosa=?";
            PreparedStatement ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setString(1, p.getS().getJmbg());
            ps.setString(2, p.getS().getBp());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                 java.util.Date ustar = new java.util.Date(rs.getDate("du").getTime());
                java.util.Date istar = new java.util.Date(rs.getDate("di").getTime());
//                ako se preklapaju termini i lista novih stavki sadrzi stavku sa starom zemljom
                if (unov.compareTo(istar) <= 0 && ustar.compareTo(inov) <= 0 
                        && list.contains(new Stavka(null, new Zemlja(rs.getInt("zid"), "")))) 
                     return false;
             }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    public List<WrapPrijava> vrati_prijave(Stanovnik s) {
        List<WrapPrijava> l = new ArrayList<>();
        try {
            String u = "select p.*, group_concat(z.naziv separator \",\") z\n"
                    + "from stanovnik s join prijava p on(p.stanovnikid=s.stanovnikid) join stavkaprijave sp on(sp.prijavaid=p.prijavaid) join zemlja z on(z.zemljaid=sp.zemljaid)\n"
                    + "WHERE s.jmbg=? AND s.brojpasosa=?\n"
                    + "group by p.prijavaid";
            PreparedStatement ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setString(1, s.getJmbg());
            ps.setString(2, s.getBp());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prijava p = new Prijava(rs.getInt("prijavaid"), s, new java.util.Date(rs.getDate("datumulaska").getTime()), new java.util.Date(rs.getDate("datumizlaska").getTime()), rs.getString("nacinputovanja"));
                WrapPrijava w = new WrapPrijava(p, rs.getString("z"));
                l.add(w);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }
    public boolean sacuvaj_prijavu(List<Stavka> list) {
        Prijava p = list.get(0).getP();
        int sid=1;
        String u;
        PreparedStatement ps;
        try {
//            ako nije registrovan korisnik, nadji njeogov id
            if(p.getS().getId()==-1)
            {
             u="select stanovnikid from stanovnik where jmbg=? and brojpasosa=?";
             ps=Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setString(1, p.getS().getJmbg());
            ps.setString(2, p.getS().getBp());
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                sid=rs.getInt("stanovnikid");
            }
            }
            u = "insert into prijava ( datumulaska, datumizlaska, nacinputovanja, stanovnikid) values(?,?,?,?)";
             ps = Konekcija.getInstance().getConn().prepareStatement(u, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new Date(p.getDu().getTime()));
            ps.setDate(2, new Date(p.getDi().getTime()));
            ps.setString(3, p.getNp());
            ps.setInt(4, p.getS().getId()==-1?sid:p.getS().getId());
            int afr = ps.executeUpdate();
            if (afr == 0) return false;
            int pid = 1;
            ResultSet rsid = ps.getGeneratedKeys();
            if (rsid.next()) {
                pid = rsid.getInt(1);
            }
            u = "insert into stavkaprijave values(?,?)";
            ps = Konekcija.getInstance().getConn().prepareStatement(u);
            for (Stavka s : list) {
                ps.setInt(1, pid);
                ps.setInt(2, s.getZ().getId());
                ps.addBatch();
            }
            ps.executeBatch();
            Konekcija.getInstance().getConn().commit();
        } catch (SQLException ex) {
            try {
                Konekcija.getInstance().getConn().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }
    public boolean izmeni_prijavu(List<Stavka> list) {
        Prijava p = list.get(0).getP();
         String u;
        PreparedStatement ps;
        try {
             u = "update prijava set datumulaska=?, datumizlaska=?, nacinputovanja=? where prijavaid=?";
             ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setDate(1, new Date(p.getDu().getTime()));
            ps.setDate(2, new Date(p.getDi().getTime()));
            ps.setString(3, p.getNp());
            ps.setInt(4, p.getId());
            int afr = ps.executeUpdate();
            if (afr == 0) return false;
            u=" DELETE FROM stavkaprijave WHERE prijavaid=?";
            ps=Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setInt(1, p.getId());
            afr=ps.executeUpdate();
            if(afr==0)return false;
            u = "insert into stavkaprijave values(?,?)";
            ps = Konekcija.getInstance().getConn().prepareStatement(u);
            for (Stavka s : list) {
                ps.setInt(1, p.getId());
                ps.setInt(2, s.getZ().getId());
                ps.addBatch();
            }
            ps.executeBatch();
            Konekcija.getInstance().getConn().commit();
        } catch (SQLException ex) {
            try {
                Konekcija.getInstance().getConn().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public String[] vratiIP(Stanovnik stanovnik) {
        String[]  l = new String[2];
        try {
            String u = "select * from stanovnik where jmbg=? and brojpasosa=?";
            PreparedStatement ps = Konekcija.getInstance().getConn().prepareStatement(u);
            ps.setString(1, stanovnik.getJmbg());
            ps.setString(2, stanovnik.getBp());
            ResultSet rs = ps.executeQuery();
            int i=0;
            while (rs.next()) {
                l[0]=rs.getString("ime");
                l[1]=rs.getString("prezime");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }
}
