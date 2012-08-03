/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */
public class Person {
    String name;
    Room currentLocation;

    public Person(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setLocation(Room location){
        this.currentLocation = location;
    }

    public Room getLocation(){
        return this.currentLocation;
    }
}

