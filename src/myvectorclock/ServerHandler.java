/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvectorclock;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
class ServerHandler implements Runnable
{
    private VectorClock vc;
    private Socket client;
    private ObjectInputStream ois;
    
    public ServerHandler(VectorClock vc, Socket client)
    {
        this.vc = vc;
        this.client = client;
        try
        {
            ois = new ObjectInputStream(this.client.getInputStream());
        }
        catch (IOException ex)
        {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run()
    {
        VectorClock receivingClock;
        try
        {
            receivingClock = (VectorClock) ois.readObject();
            //Se tutto è andato a buon fine ho ricevuto un messaggio valido
            //e quindi devo aggiornare il clock usando il timestamp del
            //messaggio ricevuto;
            vc.updateVectorClock(receivingClock);
            System.out.println("Ricevuto! VC:");
            vc.printVectorClock();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (!client.isClosed())
                try { client.close(); }
                catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
}
