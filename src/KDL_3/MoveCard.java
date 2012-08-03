/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Web3D Boss
 */
import javax.swing.*;

public class MoveCard
implements ICard{

    JTextArea gameOutputTextArea = new javax.swing.JTextArea();
    int value;

    public MoveCard(JTextArea gameOutputTextArea, int value){
        this.gameOutputTextArea = gameOutputTextArea;
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public void useCard(Person mover){
        mover.currentLocation = mover.currentLocation.moveTo.get((int)((mover.getLocation().moveTo.size())*Math.random()));
        this.gameOutputTextArea.append("\n"+mover.getName()+" moved to the "+mover.currentLocation.getRoomName());
        }
}
