/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvectorclock;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
public class Peer
{
    int myPort;
    int myIndex;
    VectorClock vc;
    
    public Peer(int port, int index)
    {
        this.myPort = port;
        this.myIndex = index;
        this.vc = new VectorClock(3, index);
    }
    
    private void startClientComponent()
    {
        (new Thread(new ClientHandler(vc))).start();
    }
    
    private void startServerComponent()
    {       
        try
        {
            ServerSocket server = new ServerSocket(myPort);
            System.out.println("Server avviato sulla port: " + myPort);
            Executor executor = Executors.newFixedThreadPool(100);
            
            Socket client = null;
            ServerHandler serverWorker;
            
            while(true)
            {
                client = server.accept();
                serverWorker = new ServerHandler(vc, client);
                executor.execute(serverWorker);
            }
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start()
    {        
        startClientComponent();
        startServerComponent();        
    }
}
