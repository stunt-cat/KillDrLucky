/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */

import java.util.ArrayList;

public class Room {
    
    String locationName;
    Room nextLocation;
    ArrayList<Room> moveTo;
    ArrayList<Room> lineOfSight;
    boolean cardRoom;

    public Room(String locationName){
        this.locationName = locationName;
    }

    public void Initialise(Room nextLocation, ArrayList<Room> moveTo, ArrayList<Room> lineOfSight, boolean cardRoom){
        this.nextLocation = nextLocation;
        this.moveTo = moveTo;
        this.lineOfSight = lineOfSight;
        this.cardRoom = cardRoom;
    }

    public boolean checkMove(Room intendedLocation){
    // checks if planned move is legal, by checking if intendedLocation occurs in this.moveTo
        boolean foundIt = false;

        for (int i=0; i<this.moveTo.size(); i++){
            if (this.moveTo.get(i) == intendedLocation){
                foundIt = true;
            }
        }
        return foundIt;
    }

    public boolean checkLineOfSight(Room viewerLocation){
    // checks if there is LOS between specified rooms, by checking if viewerLocation occurs in this.lineOfSight
        boolean foundIt = false;

        for (int i=0; i<this.lineOfSight.size(); i++){
            if (this.lineOfSight.get(i) == viewerLocation){
                foundIt = true;
            }
        }
        return foundIt;
    }

    public Room getNextRoom(){
        return this.nextLocation;
    }

    public boolean checkCardRoom(){
        return this.cardRoom;
    }

    public String getRoomName(){
        return this.locationName;
    }
}
