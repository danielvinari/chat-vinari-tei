package com.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

  private static ArrayList<ClientHandler>clients = new ArrayList<>();


  public static void main(String[] args) throws Exception {
    
    ServerSocket ss = new ServerSocket(5000);
    System.out.println("Server sulla porta 5000");
    boolean running = true;
    
    while (running) {
      Socket s = ss.accept();
      System.out.println("Connesso al client!");
      ClientHandler clientThread = new ClientHandler(s, clients);
      clients.add(clientThread);
      clientThread.start();
    }
    ss.close();
  }
}
