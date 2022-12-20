package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
       
        Socket s = new Socket("localhost", 5000);
        
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        ServerConnection serverConnection = new ServerConnection(s);

        serverConnection.start();

        boolean a = true;

        System.out.println("Inserisci nome utente: ");
        String user = keyboard.readLine();
        out.println(user);

        while (a) {

            //SCELTA
            System.out.println("Scegli una tra le seguenti opzioni:");
            System.out.println(">'pubblica' per accedere ad una chat pubblica");
            System.out.println(">'privata' per accedere ad una chat privata");
            System.out.println(">'lista' per visualizzare gli utenti connessi alla chat");
            String scelta = keyboard.readLine();
            out.println(scelta);

            if (scelta.equals("pubblica")) {
                String mess = keyboard.readLine();
                out.println(mess);

            }else if(scelta.equals("privata")){
                System.out.println(">Digita il nome dell'utente con cui vuoi connetterti:");
                String userDa = keyboard.readLine();
                out.println(userDa);

                System.out.println(">Digita il messaggio da inviare:");
                String mess = keyboard.readLine();
                out.println(mess);
            }
            String serverResponse = in.readLine();
            System.out.println(serverResponse);
        }
        in.close();
        out.close();
        s.close();
    }
}