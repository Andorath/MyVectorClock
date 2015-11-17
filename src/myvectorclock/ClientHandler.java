/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvectorclock;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
class ClientHandler implements Runnable
{
    private VectorClock vc;
    
    public ClientHandler(VectorClock vc)
    {
        this.vc = vc;
    }

    @Override
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        String choice;
        int destinationPort;
        
        while(true)
        {
            System.out.println("MESSAGGIO o EVENTO?");
            choice = scanner.next();
            //In questo momento sta avvenendo un evento e quindi bisogna
            //aggiornare il proprio clock al proprio indice;
            vc.updateVectorClock();
            System.out.println("VC: ");
            vc.printVectorClock();
            
            if(choice.equalsIgnoreCase("messaggio"))
            {
                System.out.println("A CHI E' DIRETTO? (indicare la porta)");
                destinationPort = scanner.nextInt();
                
                sendVCTo(destinationPort);
            }
        }
    }
    
    private void sendVCTo(int destinationPort)
    {
        Socket receiver = null;
        
        try
        {
            receiver = new Socket("localhost", destinationPort);
            ObjectOutputStream oos = new ObjectOutputStream(receiver.getOutputStream());
            oos.writeObject(vc);
            System.out.println("VC mandato!");
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (!receiver.isClosed())
            {
                try
                {
                    receiver.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
