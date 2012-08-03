/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */
public class FailureCard
implements ICard{

    int failValue;

    public FailureCard(int value){
        this.failValue = value;
    }

    public int getValue(){
        return this.failValue;
    }

}
