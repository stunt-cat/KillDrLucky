/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 * This class uses JDOM to create a Document from the KDLsetup XML file
 * @author Edward Moore
 */
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import java.io.File;
import org.jdom2.*;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class Game {

//create new JTextArea for output
JTextArea gameOutputTextArea = new javax.swing.JTextArea();

// playerCount variable set by user
int playerCount;

// variables for Document build from XML N.B. build is achieved in Game constructor
SAXBuilder builder = new SAXBuilder();
Document boardSetup = null;
Document cardsSetup = null;

//create new Board, which is specified in the KDLsetup XML file (initialised in Game constructor)
Board drLuckyBoard;

//create new CardPiles (initialised in Game constructor)
CardPiles drLuckyCards;

//create new RandomWords generator
RandomWords wordsSelector = new RandomWords();

ArrayList<Person> participants = new ArrayList<Person>();
boolean isThereAWinner = false;
Player currentPlayer;
ArrayList<Player> LuckyLocationPlayers = new ArrayList<Player>();
ArrayList<Player> LuckyLocationPlayersOrdered = new ArrayList<Player>();
boolean DLAlone = true;
int currentPlayerIndex;
ICard cardBeingPlayed = null;
ICard weaponCardBeingPlayed = null;
int attackValue = 1;
int failureValue = 0;
ArrayList<Player> FailurePlayersOrdered = new ArrayList<Player>();
String mansionName;
String cardsName;

//TODO N.B. failuseCardsLeft is initialised in Game constructor
int failureCardsLeft = 0;

public Game(int playerCount, JTextArea gameOutputTextArea, String boardChoice, String cardsChoice){

    this.playerCount = playerCount;
    this.gameOutputTextArea = gameOutputTextArea;
    // create all components of game, ready to begin first player turn

     //create a new Document from the correct XML file for the user-specified board choice
    {
        try {
            if (boardChoice.equals("Original Mansion")){
                boardSetup = builder.build(new File("src/KDL_3/KDLboardsetup_originalmansion.xml"));
            } else if (boardChoice.equals("Directors Cut Mansion")){
                boardSetup = builder.build(new File("src/KDL_3/KDLboardsetup_directorscutmansion.xml"));
            } else {
                gameOutputTextArea.append("\n Incorrect board specification. Idiot.");
            }
        }
        catch (JDOMException e){
            gameOutputTextArea.append(e.getMessage());
        }
        catch (IOException e){
            gameOutputTextArea.append(e.getMessage());
        }
    }


    //create a new Document from the correct XML file for the user-specified cards choice
    {
        try {
            if (cardsChoice.equals("Original Cards")){
                cardsSetup = builder.build(new File("src/KDL_3/KDLcardssetup_originalcards.xml"));
            } else if (cardsChoice.equals("Reduced Failure Cards")){
                cardsSetup = builder.build(new File("src/KDL_3/KDLcardssetup_reducedfailurecards.xml"));
            } else {
                gameOutputTextArea.append("\n Incorrect cards specification. Idiot.");
            }
        }
        catch (JDOMException e){
            gameOutputTextArea.append(e.getMessage());
        }
        catch (IOException e){
            gameOutputTextArea.append(e.getMessage());
        }
    }

     //initialise Board
    drLuckyBoard = new Board(boardSetup, gameOutputTextArea);

    //initialise CardPiles - which makes a Deck of ICards (ArrayList) and shuffles them and also an empty Discard pile (ArrayList)
    //also assign value of failureCardsLeft
    drLuckyCards = new CardPiles(gameOutputTextArea, drLuckyBoard, cardsSetup);
    failureCardsLeft = drLuckyCards.getNumberOfFailureCards();

    //print name of mansion and card distribution to output area
    Element boardRoot = boardSetup.getRootElement();
    Element board = boardRoot.getChild("board");
    mansionName = board.getAttributeValue("name");
    Element cardsRoot = cardsSetup.getRootElement();
    Element cardPiles = cardsRoot.getChild("cardPiles");
    cardsName = cardPiles.getAttributeValue("name");
    gameOutputTextArea.append("\n This game is played in the "+mansionName+
            " and with the "+cardsName+" set of cards.\n");


            // create DL
            Person drLucky = new Person("Doctor Lucky");
            participants.add(drLucky);

            //initialise DL start location
            // TODO; NB - can currently start DL in a non-named room!!
            int startLocation = (int)(32*Math.random());
            drLucky.setLocation(this.drLuckyBoard.mansion.get(startLocation));
            this.gameOutputTextArea.append("\n\n Dr. Lucky began his doom in the "+drLucky.currentLocation.getRoomName()+"\n");

           // create players and initialise in foyer
            if (playerCount <0){
                this.gameOutputTextArea.append("\n Negative players? You are an idiot. Make sense or go away.\n");
            } else if (playerCount == 0){
                this.gameOutputTextArea.append("\n There are no players! Dr Lucky lives forever! EPIC FAIL.\n");
            } else if (playerCount <3){
                this.gameOutputTextArea.append("\n There are not enough players! Between 3 and 7 (inclusive) please!\n");
            } else if(playerCount>7){
                this.gameOutputTextArea.append("\n There are more than 7 players! Dr Lucky has no chance! I will not allow this game to proceed.\n");
            } else {
                for (int i=0; i<playerCount; i++){
                    Player newPlayer = new Player(String.format("Player %d", i+1));
                    newPlayer.setLocation(this.drLuckyBoard.map.get("Foyer"));
                    participants.add(newPlayer);
                }
            
            
            //TEMP print out who is in the foyer
            this.gameOutputTextArea.append("\n There are now "+(participants.size()-1)+ " players in the foyer.\n");
         
         
            // deal 6 cards to each player N.B. participants[0]= DL
           for (int i=1; i<(participants.size()); i++){
                for (int j=0; j<6; j++){
                    Player dealToMe = (Player)participants.get(i);
                    dealToMe.Hand.add(this.drLuckyCards.getDeckCard(dealToMe));
                }
            }

           /*
            // print out the cards of each player 
            for (int i=1; i<(participants.size()); i++){
                for (int j=0; j<6; j++){
                    Player checkMyCards = (Player)participants.get(i);
                    if(checkMyCards.Hand.get(j) instanceof RoomCard){
                        System.out.println("Player "+i+" got dealt the "+((RoomCard)checkMyCards.Hand.get(j)).getName()+" Room card.");
                    } else if (checkMyCards.Hand.get(j) instanceof WeaponCard){
                        System.out.println("Player "+i+" got dealt the "+((WeaponCard)checkMyCards.Hand.get(j)).getName()+" Weapon card.");
                    } else if (checkMyCards.Hand.get(j) instanceof FailureCard){
                        System.out.println("Player "+i+" got dealt a "+((FailureCard)checkMyCards.Hand.get(j)).getValue()+" value Failure card.");
                    } else if (checkMyCards.Hand.get(j) instanceof SpecialWeaponCard){
                        System.out.println("Player "+i+" got dealt the "+((SpecialWeaponCard)checkMyCards.Hand.get(j)).getName()+" Special Weapon card.");
                    } else if (checkMyCards.Hand.get(j) instanceof MoveCard){
                        System.out.println("Player "+i+" got dealt a "+((MoveCard)checkMyCards.Hand.get(j)).getValue()+" value Move card.");
                    }
                }
            }
          */
           
            // choose current player i.e. start player.  N.B. participants[0]=DL
            int startPlayerNumber = (int)((playerCount*Math.random())+1);
            currentPlayer = (Player) participants.get(startPlayerNumber);

            //TEMP check start player
            this.gameOutputTextArea.append("\n\n The starting player is "+currentPlayer.getName()+"\n");

            }
        }
 
            
           

public void playGame(){
    // while isThereAWinner == false, do next player turn
// choose 'get cards' or 'play cards' turn type
// if 'get cards' turn type
//      possibly move 1 and/or
//      get card (check room okay)
// if 'play cards' turn type
//      move 1 and/or
//      play card and/or
//      attempt kill
// determine next player

gameLoop:{
    do {
        //if DL location == currentPlayer location, have a play cards turn
        if (participants.get(0).currentLocation == currentPlayer.currentLocation){
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" is in the same room as Dr. Lucky! PLAY cards turn it must be!");
            playCardsTurn();
            makeKillAttempt();
                if (isThereAWinner == true){
                    break gameLoop;
                }

        // otherwise, randomly choose which turn type to have
        } else if (Math.random()<0.5){
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" randomly decided to GET cards this turn.");
            getCardsTurn();

            } else {
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" randomly decided to PLAY cards this turn.");
            playCardsTurn();
            makeKillAttempt();
                if (isThereAWinner == true){
                    break gameLoop;
                }
            }

        //move DL to next room on his circuit
        participants.get(0).setLocation(participants.get(0).getLocation().getNextRoom());
        this.gameOutputTextArea.append("\n After "+currentPlayer.getName()+" finished their turn, Dr. Lucky moved on to the "+participants.get(0).currentLocation.getRoomName()+".");

        //get next player
        currentPlayer = getNextPlayer();

    } while (true);
}

    //this code will execute when a kill attempt has succeeded!
    if(weaponCardBeingPlayed instanceof WeaponCard){
        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" has won! He/she used the "+((WeaponCard)weaponCardBeingPlayed).getName()
                +" in the "+currentPlayer.getLocation().getRoomName()+" and the Failure cards total was only "+failureValue+". "
                +"\n"+this.wordsSelector.winner((int)(Math.random()*10)+1));
    } else if(weaponCardBeingPlayed == null){
        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" has won! He/she used their bare hands(!) in the "+currentPlayer.getLocation().getRoomName()
                +" and the Failure cards total was only "+failureValue+". "+this.wordsSelector.winner((int)(Math.random()*10)+1));
    }
}


public void getCardsTurn(){

    //if in a named room, 50/50 move 1 room
    if (currentPlayer.getLocation().checkCardRoom()==true){
        if (Math.random()<0.5){
            //move 1 room
                //get list of available moves
                //pick random room from available moves
                //update current player location to chosen room
            currentPlayer.currentLocation = currentPlayer.currentLocation.moveTo.get((int)((currentPlayer.getLocation().moveTo.size())*Math.random()));
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" randomly decided to use his/her 1 move to go to the "+currentPlayer.currentLocation.getRoomName()+".");
        } else {
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" randomly decided not to use his/her 1 move. "+this.wordsSelector.negative((int)(Math.random()*10)+1));
        }
    } else {
        //if not in a named room, move 1 room
            currentPlayer.currentLocation = currentPlayer.currentLocation.moveTo.get((int)((currentPlayer.getLocation().moveTo.size())*Math.random()));
            this.gameOutputTextArea.append("\n"+currentPlayer.name+" randomly decided to use his/her 1 move to go to the "+currentPlayer.currentLocation.getRoomName()+".");
    }

   //finally, if in named room, get card
    if (currentPlayer.getLocation().checkCardRoom()==true){
        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" searched the room for a card.");
        currentPlayer.Hand.add(this.drLuckyCards.getDeckCard(currentPlayer));
    }
    }


public void playCardsTurn(){

    // WHAT THIS METHOD DOES;
    // ?move 1 before playing cards
    //check cards in hand
    //     75% ?play move card (1,2,3)
    //       75% PL
    //           move randomly to adjacent rooms X times
    //     75% ?play room card
    //      50% DL or PL
    //
    //
    // make kill attempt
    //     check room only has DL + Pl
     //    check no line of sight to DL from any PL
     //    check if weapon card (use no weapon if not)
     //        check if bonus applies to special weapon
     //    start kill attempt with weapon value
     //        loop through other players
     //        check if have failure cards
     //       play if so up to weapon value
     //           break if weapon value met
     //               go to next player turn
     //       end game if weapon value not met - win!


           //if currentPlayer in same room as DL, no movement, make kill attempt.
        if (currentPlayer.currentLocation == participants.get(0).currentLocation){
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" is in the same room as Dr. Lucky - he/she is staying put for the kill!");
            return;

            // otherwise, possibly move 1 before playing cards
        } else if (Math.random()<0.5){
            currentPlayer.currentLocation = currentPlayer.currentLocation.moveTo.get((int)((currentPlayer.getLocation().moveTo.size())*Math.random()));
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" used his/her 1 move to go to the "+currentPlayer.currentLocation.getRoomName()+".");
            if (currentPlayer.currentLocation == participants.get(0).currentLocation){
                this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" just moved into the same room as Dr. Lucky! Kill, Kill, Kill!");
                return;
            }
        } else {
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" decided not to use his/her 1 move. "+this.wordsSelector.negative((int)(Math.random()*10)+1));
        }

         //check cards in hand
        //     75% possibly play move card (1,2,3)
        //       75% PL
        //           move randomly to adjacent rooms X times
        //     75% possibly play room card
        //      50% DL or PL


        // Move cards loop
        for (int i=0; i<currentPlayer.Hand.size(); i++){
            if (currentPlayer.Hand.get(i) instanceof MoveCard){
                if (Math.random()<0.75){
                    if (Math.random()<0.75){
                        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" used a Move "+((MoveCard)currentPlayer.Hand.get(i)).getValue()+" card on his/herself.");
                        for (int j=0; j<((MoveCard)currentPlayer.Hand.get(i)).getValue(); j++){
                            ((MoveCard)currentPlayer.Hand.get(i)).useCard(currentPlayer);
                            // break out of 'play Move Card' loop (and entire playCardsTurn method) if DL+CP in same room
                            if (participants.get(0).currentLocation == currentPlayer.currentLocation){
                                this.gameOutputTextArea.append("\n"+currentPlayer.getName()+ " just intercepted Dr. Lucky! Commence destruction!");
                                return;
                            }
                        }

                    } else {
                        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" used a Move "+((MoveCard)currentPlayer.Hand.get(i)).getValue()+" card on Dr Lucky.");
                        for (int j=0; j<((MoveCard)currentPlayer.Hand.get(i)).getValue(); j++){
                            ((MoveCard)currentPlayer.Hand.get(i)).useCard(participants.get(0));
                            // break out of 'play Move Card' loop (and entire playCardsTurn method) if DL+CP in same room
                            if (participants.get(0).currentLocation == currentPlayer.currentLocation){
                                this.gameOutputTextArea.append("\n"+currentPlayer.getName()+ " just intercepted Dr. Lucky! Commence destruction!");
                                return;
                            }
                        }

                    }
                }
            // put used Move card in the discard pile
            cardBeingPlayed = currentPlayer.Hand.get(i);
            currentPlayer.Hand.remove(i);
            this.drLuckyCards.discard.add(cardBeingPlayed);
            }
        }

        // Room cards loop
        for (int i=0; i<currentPlayer.Hand.size(); i++){
            if (currentPlayer.Hand.get(i) instanceof RoomCard){
                if (Math.random()<0.75){
                    if (Math.random()<0.5){
                        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" used the "+((RoomCard)currentPlayer.Hand.get(i)).getName()+" Room card on his/herself");
                        ((RoomCard)currentPlayer.Hand.get(i)).useCard(currentPlayer);
                        // break out of 'play Room Card' loop (and entire playCardsTurn method) if DL+CP in same room
                            if (participants.get(0).currentLocation == currentPlayer.currentLocation){
                                this.gameOutputTextArea.append("\n"+currentPlayer.getName()+ " just intercepted Dr. Lucky! Commence destruction!");
                                return;
                            }
                    } else {
                        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" used the "+((RoomCard)currentPlayer.Hand.get(i)).getName()+" Room card on Dr. Lucky.");
                        ((RoomCard)currentPlayer.Hand.get(i)).useCard(participants.get(0));
                        // break out of 'play Room Card' loop (and entire playCardsTurn method) if DL+CP in same room
                            if (participants.get(0).currentLocation == currentPlayer.currentLocation){
                                this.gameOutputTextArea.append("\n"+currentPlayer.getName()+ " just intercepted Dr. Lucky! Commence destruction!");
                                return;
                    }
                }
            // put used Room card in the discard pile
            cardBeingPlayed = currentPlayer.Hand.get(i);
            currentPlayer.Hand.remove(i);
            this.drLuckyCards.discard.add(cardBeingPlayed);
            }
        }
    }
}


public void makeKillAttempt(){

    // Kill attempt loop
    // check if DL in same location as currentPlayer
    if (participants.get(0).getLocation() == currentPlayer.getLocation()){

        //check if anyone else is too
        for (int i=1; i<participants.size(); i++){
            if (participants.get(0).currentLocation == participants.get(i).currentLocation){
                // remove self-check result!
                if (participants.get(i) != currentPlayer){
                    this.gameOutputTextArea.append("\n Dr. Lucky is not alone with the assailant! "+participants.get(i).getName()+" is there too! Kill attempt failed!");
                    return;
                }
            }
        }

        //check if anyone else has line of sight
        for (int i=1; i<participants.size(); i++){
            if ((participants.get(0).currentLocation.checkLineOfSight(participants.get(i).currentLocation)) == true){
                // remove self-check result!
                if (participants.get(i) != currentPlayer){
                    this.gameOutputTextArea.append("\n Luckily for Dr. Lucky, "+participants.get(i).getName()+" is watching the action. Kill attempt failed!");
                    return;
                }
            }
        }

        // this code executes if DL is alone with one currentPlayer and nobody else can see!
        // check if currentPlayer has any Weapon cards, and find the one with highest value N.B. SpecialWeaponCard is a subclass of WeaponCard!
        for (int i=0; i<currentPlayer.Hand.size(); i++){
            if (currentPlayer.Hand.get(i) instanceof WeaponCard){
                if (((WeaponCard)currentPlayer.Hand.get(i)).getValue(currentPlayer.currentLocation) > attackValue){
                    attackValue = ((WeaponCard)currentPlayer.Hand.get(i)).getValue(currentPlayer.currentLocation);
                    weaponCardBeingPlayed = currentPlayer.Hand.get(i);
                }
            }
        }

        // find out which Weapon/SpecialWeapon card is being played (if any). Output it and remove card to discard pile
        if (weaponCardBeingPlayed == null){
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" has no weapon cards, so is attempting to Kill Dr. Lucky with his/her bare hands! "
                    +this.wordsSelector.positive((int)(Math.random()*10)+1));

        } else if (weaponCardBeingPlayed instanceof WeaponCard){
            this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" is using the "+ ((WeaponCard)weaponCardBeingPlayed).getName()+
                    " on Dr. Lucky! "+this.wordsSelector.positive((int)(Math.random()*10)+1)
                    +" It's value is "+attackValue+" in the "+currentPlayer.currentLocation.getRoomName()+". ");
            for (int i=0; i<currentPlayer.Hand.size(); i++){
                if (currentPlayer.Hand.get(i) == weaponCardBeingPlayed){
                    currentPlayer.Hand.remove(i);
                    this.drLuckyCards.discard.add(weaponCardBeingPlayed);
                }
            }
        }
        
        // attackValue is now defined, with a minimum of 1
        // see if any other players have any failure cards. If they do, play them until attackValue is met or bettered.
            // first, put other players in order for playing failure cards - i.e. turn order after current player
                //find currentPlayer index
                    for (int i=1; i<participants.size(); i++){
                            if(participants.get(i)==currentPlayer){
                                currentPlayerIndex = i;
                            }
                    }
                //add Players after currentPlayer to ordered list
                    for (int i=currentPlayerIndex+1; i<participants.size(); i++){
                        FailurePlayersOrdered.add((Player)participants.get(i));
                        //TEMP - to check this is working
                        //System.out.println("Added "+((Player)participants.get(i)).getName()+" to FailurePlayersOrdered list.");
                    }

                //add Players before currentPlayer (not including currentPlayer)
                    for (int i=1; i<currentPlayerIndex; i++){
                       FailurePlayersOrdered.add((Player)participants.get(i));
                       //TEMP - to check this is working
                       //System.out.println("Added "+((Player)participants.get(i)).getName()+" to FailurePlayersOrdered list.");
                    }

           // second, cycle through players in FailurePlayersOrdered list
                for (int i=0; i<FailurePlayersOrdered.size(); i++){
                    for (int j=0; j<((Player)FailurePlayersOrdered.get(i)).Hand.size(); j++){
                        if (((Player)FailurePlayersOrdered.get(i)).Hand.get(j) instanceof FailureCard){
                            failureValue += ((FailureCard)((Player)FailurePlayersOrdered.get(i)).Hand.get(j)).getValue();
                            failureCardsLeft = failureCardsLeft-1;
                            
                            // 0utput failure progress so far
                            this.gameOutputTextArea.append("\n"+FailurePlayersOrdered.get(i).getName()+" played a "+((FailureCard)((Player)FailurePlayersOrdered.get(i)).Hand.get(j)).getValue()
                                    +" value Failure card. The Failure cards value total is now "+failureValue+" and there are "
                                    +failureCardsLeft+" Failure cards left somewhere. "
                                    +this.wordsSelector.failure((int)(Math.random()*10)+1));
                            
                            //trash used Failure card
                            cardBeingPlayed = ((FailureCard)((Player)FailurePlayersOrdered.get(i)).Hand.get(j));
                            ((Player)FailurePlayersOrdered.get(i)).Hand.remove(j);
                            this.drLuckyCards.add(cardBeingPlayed);

                            if (failureValue >= attackValue){
                                // the kill attempt has failed at this point, so end player turn and reset temporary 'kill attempt' variables
                                this.gameOutputTextArea.append("\n The kill attempt has been thwarted!");
                                cardBeingPlayed = null;
                                weaponCardBeingPlayed = null;
                                failureValue = 0;
                                attackValue = 1;
                                FailurePlayersOrdered.clear();
                                return;
                            }
                        }
                    }
                }

        // this code will execute if the attackValue has not been met!
        isThereAWinner = true;

    } else {
        //DL not in same room as current player - instant fail
        this.gameOutputTextArea.append("\n"+currentPlayer.getName()+" is not in the same room as Dr. Lucky. Nothing dastardly can occur.");
    }
}


public Player getNextPlayer(){

   

    // check if DL is alone
    for (int i=1; i<participants.size(); i++){
        if (participants.get(0).currentLocation == participants.get(i).currentLocation){
            DLAlone = false;
        }
    }

    // if DL alone, get next player
    if (DLAlone == true){
         // if last player, get first player
        if (currentPlayer == participants.get(participants.size()-1)){
            currentPlayer = (Player) participants.get(1);
            this.gameOutputTextArea.append("\n The next player is "+currentPlayer.getName()+".\n");
            return currentPlayer;
        } else {
            // get next player
            currentPlayer = (Player) participants.get((participants.indexOf(currentPlayer))+1);
            this.gameOutputTextArea.append("\n The next player is "+currentPlayer.getName()+".\n");
            return currentPlayer;
        }
    } else {
        
            this.gameOutputTextArea.append("\n Dr. Lucky is not alone!");

        //print out players in room with DL and add to LuckyLocationPlayers
            for (int i=1; i<participants.size(); i++){
                if (participants.get(0).currentLocation == participants.get(i).currentLocation){
                    this.gameOutputTextArea.append("\n"+participants.get(i).getName()+" is in the same room as Dr. Lucky!");
                    LuckyLocationPlayers.add((Player)participants.get(i));
                }
            }

     
            //add Players with DL to LuckyLocationPlayersOrdered list in 'natural next turn order'
            //find currentPlayerIndex
                for (int i=1; i<participants.size(); i++){
                    if (participants.get(i) == currentPlayer){
                        currentPlayerIndex = i;
                    }
                }

            //add Players after currentPlayer to ordered list
                for (int i=0; i<LuckyLocationPlayers.size(); i++){
                    for (int j=currentPlayerIndex; j<participants.size(); j++){
                        if (LuckyLocationPlayers.get(i) == participants.get(j)){
                            LuckyLocationPlayersOrdered.add((Player)LuckyLocationPlayers.get(i));

                            //TEMP - to check this is working
                            //System.out.println("Added "+((Player)LuckyLocationPlayers.get(i)).getName()+" to LuckyLocationPlayersOrdered list.");

                        }
                    }
                }
            //add Players before currentPlayer (including currentPlayer) to list
                for (int i=0; i<LuckyLocationPlayers.size(); i++){
                    for (int j=0; j<currentPlayerIndex; j++){
                        if (LuckyLocationPlayers.get(i) == participants.get(j)){
                           LuckyLocationPlayersOrdered.add((Player)LuckyLocationPlayers.get(i));
                           //TEMP - to check this is working
                           //System.out.println("Added "+((Player)LuckyLocationPlayers.get(i)).getName()+" to LuckyLocationPlayersOrdered list.");
                        }
                    }
                }

            //get next player from those in room (first in ordered list)
            //if more than one player, remove case of 1st in list being currentPlayer
                if (LuckyLocationPlayersOrdered.size() == 1){
                    currentPlayer = LuckyLocationPlayersOrdered.get(0);
                } else if (LuckyLocationPlayersOrdered.get(0) == currentPlayer){
                    currentPlayer = LuckyLocationPlayersOrdered.get(1);
                } else {
                    currentPlayer = LuckyLocationPlayersOrdered.get(0);
                }

            this.gameOutputTextArea.append("\n Because Dr. Lucky was intercepted, the next player is "+currentPlayer.getName()+". \n");

            //reset fields
            DLAlone = true;
            LuckyLocationPlayers.clear();
            LuckyLocationPlayersOrdered.clear();
            return currentPlayer;
    }
}

}
