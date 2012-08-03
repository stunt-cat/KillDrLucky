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

public class SpecialWeaponCard
extends WeaponCard
implements ICard{

    int specialWeaponValue;
    Room specialWeaponLocation;
    JTextArea gameOutputTextArea;

    public SpecialWeaponCard(String name, int value, int specialV, Room specialR, JTextArea gameOutputTextArea){
        super(name, value);
        this.specialWeaponValue = specialV;
        this.specialWeaponLocation = specialR;
        this.gameOutputTextArea = gameOutputTextArea;
    }

    //override super getValue() to incorporate potential special room bonus value
@Override
    public int getValue(Room playerLocation){
        if (playerLocation == (this.specialWeaponLocation)){
            gameOutputTextArea.append("\n SPECIAL WEAPON USE !_____________________________________________________________");
            return this.specialWeaponValue;
        } else {
            return this.weaponValue;
        }
    }

}
