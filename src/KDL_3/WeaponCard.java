/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */
public class WeaponCard
implements ICard{

    String weaponName;
    int weaponValue;

    public WeaponCard(String name, int value){
        this.weaponName = name;
        this.weaponValue = value;
    }

    public String getName(){
        return this.weaponName;
    }

    public int getValue(Room playerLocation){
        return this.weaponValue;
    }
}
