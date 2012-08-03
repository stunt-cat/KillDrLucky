/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */
import javax.swing.*;

public class RoomCard
implements ICard{

    JTextArea gameOutputTextArea = new javax.swing.JTextArea();
    String name;
    Room effectLocation;

    public RoomCard(JTextArea gameOutputTextArea, String name, Room value){
        this.gameOutputTextArea = gameOutputTextArea;
        this.name = name;
        this.effectLocation = value;
    }

    public String getName(){
        return this.name;
    }

    public Room getEffectLocation(){
        return this.effectLocation;
    }

    // teleport function
    public void useCard(Person teleportee){
        teleportee.setLocation(this.effectLocation);
        this.gameOutputTextArea.append("\n"+teleportee.getName()+" is, therefore, now in the "+this.getName()+".");
    }
}
