package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection extends Thread{
    
    private Socket server;
    static private BufferedReader in;
    static private PrintWriter out;

    public ServerConnection(Socket s) throws IOException{
        server = s;
        out = new PrintWriter(s.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    
    }

    public static void main(String[] args) throws IOException{
        
        boolean a = true;
        try {
            while (a) {
                String serverResponse = in.readLine();
                System.out.println("Server: " + serverResponse);
                
            }
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
