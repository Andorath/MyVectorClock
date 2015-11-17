/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvectorclock;

import java.io.Serializable;

/**
 *
 * @author Marco
 */
public class VectorClock implements Serializable
{
    private int processVector[];
    private int myIndex;
    
    public VectorClock(int size, int index)
    {
        this.processVector = new int[size];
        this.myIndex = index;
    }
    
    synchronized public int [] getProcessVector()
    {
        return this.processVector;
    }
    
    synchronized public void printVectorClock()
    {
        for(int e : processVector)
        {
            System.out.print(e + "\t");
        }
        System.out.println();
    }
    
    synchronized public void updateVectorClock(VectorClock vc)
    {
        int[] tempVector = vc.getProcessVector();
        
        for(int i= 0; i < tempVector.length; ++i)
        {
            this.processVector[i] = Math.max(this.processVector[i], tempVector[i]);
            if (myIndex == i)
                this.updateVectorClock();
        }
    }

    synchronized public void updateVectorClock()
    {
        this.processVector[myIndex]++;
    }
}
