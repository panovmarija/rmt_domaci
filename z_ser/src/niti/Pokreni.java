/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Pokreni extends Thread{

    @Override
    public void run() {
        try {
            ServerSocket ss=new ServerSocket(9000);
            while (true) {
                Socket s=ss.accept();
                System.out.println("dosao kl");
                Obrada o=new Obrada(s);
                o.start();
            }
        } catch (IOException ex) {
            System.out.println("port je vec zauzet");
         }
    }
    
}
