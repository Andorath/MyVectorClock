/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvectorclock;

/**
 *
 * @author Marco
 */
public class UI
{
    public final static int OFFSET = 9990;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("ERRORE nessun argomento!");
            System.exit(1);
        }
        
        int myPort = Integer.parseInt(args[0]);
        int myIndex = myPort % OFFSET;
        
        Peer currentPeer = new Peer(myPort, myIndex);
        currentPeer.start();
    }
    
}
