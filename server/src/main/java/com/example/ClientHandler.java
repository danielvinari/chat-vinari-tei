package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler extends Thread{

    private PrintWriter out = null;
    private BufferedReader in = null;
    private ArrayList<ClientHandler>clients;
    private String username;
    
    public ClientHandler(Socket socket, ArrayList<ClientHandler>clients) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            this.clients = clients;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }


    public void run() {

        boolean a = true;

        try {
            while (a) {
                
                String r = in.readLine();
                
                for (int i = 0; i < clients.size(); i++) {
                    if(r.equals(clients.get(i).getUsername())){
                        out.println("Utente già presente");
                        a = false;
                    }   
                } 
                
                clients.add(this);
                username = r;
                
                String scelta = in.readLine();

                if (scelta.equals("pubblica")) {
                    String mess = in.readLine();
                    mandaMsgTutti(mess);

                }else if (scelta.equals("privata")) {
                    String inviaAdUser = in.readLine();
                    String mess = in.readLine();
                    for (int i=0; i < clients.size(); i++) {
                        if (inviaAdUser.equals(clients.get(i).getUsername())) {
                            mandaMsg(mess);
                        }
                    }

                }else if (scelta.equals("lista")) {
                    for (int i=0; i < clients.size(); i++) {
                        out.println("UTENTE --> "+clients.get(i).getUsername()+" e' connesso alla chat. ");
                        }
                    }
                }

            } catch (IOException e) {
                out.close();
            }
        }
    
    
    public void mandaMsg(String msg){
        out.println(username+ ": " + msg);
    }

    public void mandaMsgTutti(String msg){

        for(int i=0; i < clients.size(); i++){
            if (clients.get(i) != null) {
                clients.get(i).mandaMsg(msg);
            }
        }
    }

    public void rimuoviClient(){
        clients.remove(this);
        mandaMsgTutti("SERVER: " + username + "e' uscito dalla chat.");
    }

    public void chiudiTutto(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        rimuoviClient();
        try {
            if (out != null) {
                out.close();   
            }
            if (in != null) {
                in.close();   
            }
            if (socket != null) {
                socket.close();   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
