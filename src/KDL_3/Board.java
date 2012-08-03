/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;


/**
 * This class uses the setup object to build a Board object.
 * A Board object is basically a Room[], with the size dependent on the setup contents
 * @author Edward Moore
 */

import org.jdom2.*;
import javax.swing.*;
import java.util.*;

public class Board {

    public ArrayList<Room> mansion = new ArrayList<Room>();
    Document setup;
    Map<String, Room> map = new HashMap<String, Room>();
    JTextArea gameOutputTextArea;

    //variables used during each Room initialisation
    String locationName;
    Room nextLocation;
    boolean cardRoom;

    //variables used during setup iteration for each room
    String cardRoomStatus;
    String match = "true";
    List <Element> moveToRoomNames = new ArrayList<Element>(0);
    List <Element> lineOfSightRoomNames = new ArrayList<Element>(0);

// @SuppressWarnings("unchecked")
    public Board(Document setup, JTextArea gameOutputTextArea){
        
        ArrayList<Room> moveTo;
        ArrayList<Room> lineOfSight;
        this.setup = setup;
        this.gameOutputTextArea = gameOutputTextArea;

        //parse setup Document to create each Room in mansion.
        //Initialise args list is Initialise(String locationName, Room nextLocation, Room[] moveTo, Room[] lineOfSight, boolean cardRoom)

        Element root = setup.getRootElement();
        Element board = root.getChild("board");
        List <Element> rooms = board.getChildren("room");

        //initial parse of Document creates an ArrayList<Room> with only locationName variable initialised.
        //also adds key value pairs to Map- String is locationName, Room is mansion (ArrayList) ref.
        Iterator<?> iterator = rooms.iterator();

        gameOutputTextArea.append("\n The mansion now consists of; \n");
        while (iterator.hasNext()){
            Element child = (Element) iterator.next();
            Room addRoom = new Room(child.getAttributeValue("locationName"));
            //add addRoom to mansion ArrayList<Room>
            mansion.add(addRoom);
            gameOutputTextArea.append("\n"+addRoom.getRoomName());
            //add room to map
            map.put(child.getAttributeValue("locationName"), addRoom);
            addRoom = null;
        }

        //parse setup Document a second time to get data for each Room Initialise()
        Iterator <?> secondIterator = rooms.iterator();
        while (secondIterator.hasNext()){

            Element child = (Element) secondIterator.next();
            locationName = child.getAttributeValue("locationName");
            nextLocation = map.get(child.getAttributeValue("nextLocation"));
            cardRoomStatus = child.getAttributeValue("cardRoom");
            //convert cardRoomStatus into a boolean and use to set cardRoom
            if (cardRoomStatus.equals(match)) {
                cardRoom = true;
            } else {
                cardRoom = false;
            }
            moveToRoomNames = child.getChildren("moveTo");
            moveTo = new ArrayList<Room>();
            lineOfSight = new ArrayList<Room>();

            //N.B. moveToRoomNames is List<Element, Element, Element, etc>
            //get name of each Room in moveToRoomNames and use map to add corresponding Rooms to moveTo and lineOfSight ArrayList<Room>s
            for (int i=0; i<moveToRoomNames.size(); i++){
                String roomName = moveToRoomNames.get(i).getValue();
                Room temp = map.get(roomName);
                moveTo.add(temp);
                lineOfSight.add(temp);
            }
            lineOfSightRoomNames = child.getChildren("lineOfSight");
            //N.B. lineOfSightRoomNames is List<Element, Element, Element, etc>
            //get name of each Room in lineOfSightRoomNames and use map to add corresponding Rooms to lineOfSight ArrayList<Room>
            for (int i=0; i<lineOfSightRoomNames.size(); i++){
                String roomName = lineOfSightRoomNames.get(i).getValue();
                lineOfSight.add(map.get(roomName));
            }
            //initialise Room
            map.get(locationName).Initialise(nextLocation, moveTo, lineOfSight, cardRoom);
        }
    }
}

  