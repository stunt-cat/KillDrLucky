/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */

import java.util.*;
import javax.swing.*;
import org.jdom2.*;

public class CardPiles {

    JTextArea gameOutputTextArea;
    ArrayList<ICard> deck = new ArrayList<ICard>();
    ArrayList<ICard> discard = new ArrayList<ICard>();
    Board board;
    ICard returnedCard;
    Document setup;
    Integer totalNumberOfFailureCards = 0;

    public CardPiles(JTextArea gameOutputTextArea, Board board, Document setup){

        this.gameOutputTextArea = gameOutputTextArea;
        this.board = board;
        this.setup = setup;

        //create all cards and add to deck
        //parse setup Document to create each ICard and add to deck (ArrayList<ICard>)

        Element root = setup.getRootElement();
        Element cardPiles = root.getChild("cardPiles");
        List <Element> cards = cardPiles.getChildren("card");

        Iterator<?> iterator = cards.iterator();

        while (iterator.hasNext()){
            Element child = (Element) iterator.next();
            //test for type of ICard and process according to type
            if (child.getAttributeValue("type").equals("FailureCard")){
                //do failure card creation and add to deck
                //N.B. FailureCard constuctor is FailureCard(int value)
                int value = Integer.parseInt(child.getAttributeValue("value"));
                int count = Integer.parseInt(child.getAttributeValue("count"));
                for (int i=0; i<count; i++){
                    deck.add(new FailureCard(value));
                    totalNumberOfFailureCards++;
                }
            } else if (child.getAttributeValue("type").equals("MoveCard")){
                //do move card creation
                //N.B. MoveCard constructor is MoveCard(JTextArea gameOutputTextArea, int value)
                int value = Integer.parseInt(child.getAttributeValue("value"));
                int count = Integer.parseInt(child.getAttributeValue("count"));
                for (int i=0; i<count; i++){
                    deck.add(new MoveCard(gameOutputTextArea, value));
                }
            } else if (child.getAttributeValue("type").equals("WeaponCard")){
                //do weapon card creation
                //N.B. WeaponCard constructor is WeaponCard(String name, int value)
                 String name = child.getAttributeValue("name");
                int value = Integer.parseInt(child.getAttributeValue("value"));
                deck.add(new WeaponCard(name, value));

            } else if (child.getAttributeValue("type").equals("SpecialWeaponCard")){
                //do special weapon card creation
                //N.B. SpecialWeaponCard constructor is SpecialWeaponCard(String name, int value, int specialV, Room specialR)
                String name = child.getAttributeValue("name");
                int value = Integer.parseInt(child.getAttributeValue("value"));
                int specialWeaponValue = Integer.parseInt(child.getAttributeValue("specialWeaponValue"));
                Room specialWeaponLocation = board.map.get(child.getAttributeValue("specialWeaponLocation"));
                deck.add(new SpecialWeaponCard(name, value, specialWeaponValue, specialWeaponLocation, gameOutputTextArea));

            } else if (child.getAttributeValue("type").equals("RoomCard")){
                //do special weapon card creation
                //N.B. RoomCard constructor is RoomCard(JTextArea gameOutputTextArea, String name, Room value)
                String name = child.getAttributeValue("name");
                Room value = board.map.get(child.getAttributeValue("name"));
                deck.add(new RoomCard(gameOutputTextArea, name, value));
            } else {
                gameOutputTextArea.append("Incorrect card type specified in XML. Complain to whomever you feel like.");
            }
        }
        
        //shuffle deck
        Collections.shuffle(deck);

        
        //print out shuffled order of deck
        this.gameOutputTextArea.append("\n\n The shuffled deck now consists of; \n");
        for (int i=0; i<deck.size(); i++){
            if(deck.get(i) instanceof RoomCard){
                this.gameOutputTextArea.append("\n Card "+(i+1)+" is the "+(((RoomCard)deck.get(i)).getName())+" Room card.");
            }else if(deck.get(i) instanceof SpecialWeaponCard){
                this.gameOutputTextArea.append("\n Card "+(i+1)+" is the "+(((SpecialWeaponCard)deck.get(i)).getName())+" Special Weapon card.");
            }else if(deck.get(i) instanceof WeaponCard){
                this.gameOutputTextArea.append("\n Card "+(i+1)+" is the "+((WeaponCard)deck.get(i)).getName()+" Weapon card.");
            }else if(deck.get(i) instanceof MoveCard){
                this.gameOutputTextArea.append("\n Card "+(i+1)+" is a "+((MoveCard)deck.get(i)).getValue()+" value Move card.");
            }else if(deck.get(i) instanceof FailureCard){
                this.gameOutputTextArea.append("\n Card "+(i+1)+" is a "+((FailureCard)deck.get(i)).getValue()+" value Failure card.");
            }
        }
      
    }

    public int getNumberOfFailureCards(){
        return totalNumberOfFailureCards;
    }

    public ICard getDeckCard(Player cardReceiver){
        //check there is a card in Deck
        //   if there isn't a card in Deck,
        //          transfer contents of discard to deck
        //          shuffle deck
        //          return card
        //   if there is a card in Deck
        //          return card

        if (deck.isEmpty()){

            //check if empty deck AND empty discard - get no card if so!
            if (discard.isEmpty()){
                this.gameOutputTextArea.append("\n There are no cards left! No card for "+cardReceiver.getName());
                return null;
            }

            //otherwise transfer contents of discard to deck
            deck.addAll(discard);
            discard.clear();

            //shuffle deck
            Collections.shuffle(deck);
            this.gameOutputTextArea.append("\n ********** The deck ran out of cards, so the discards have been shuffled and have become the new deck. **********");

            //print out shuffled order of deck
            this.gameOutputTextArea.append("\n The shuffled deck now consists of;");
            for (int i=0; i<deck.size(); i++){
                if(deck.get(i) instanceof RoomCard){
                    this.gameOutputTextArea.append("\n Card "+(i+1)+" is the "+(((RoomCard)deck.get(i)).getName())+" Room card.");
                }else if(deck.get(i) instanceof SpecialWeaponCard){
                    this.gameOutputTextArea.append("\n Card "+(i+1)+" is the "+(((SpecialWeaponCard)deck.get(i)).getName())+" Special Weapon card.");
                }else if(deck.get(i) instanceof WeaponCard){
                    this.gameOutputTextArea.append("\n Card "+(i+1)+" is the "+((WeaponCard)deck.get(i)).getName()+" Weapon card.");
                }else if(deck.get(i) instanceof MoveCard){
                    this.gameOutputTextArea.append("\n Card "+(i+1)+" is a "+((MoveCard)deck.get(i)).getValue()+" value Move card.");
                }else if(deck.get(i) instanceof FailureCard){
                    this.gameOutputTextArea.append("\n Card "+(i+1)+" is a "+((FailureCard)deck.get(i)).getValue()+" value Failure card.");
                }
            }


            //return first card and delete it from deck
            returnedCard = deck.get(0);
            deck.remove(0);
            //print what card is
            if (returnedCard instanceof RoomCard){
                this.gameOutputTextArea.append("\n "+cardReceiver.getName()+" got dealt the "+((RoomCard)returnedCard).getName()+" Room card.");
            } else if (returnedCard instanceof SpecialWeaponCard){
                this.gameOutputTextArea.append("\n "+cardReceiver.getName()+" got dealt the "+((SpecialWeaponCard)returnedCard).getName()+" Weapon card.");
            } else if (returnedCard instanceof WeaponCard){
                this.gameOutputTextArea.append("\n "+cardReceiver.getName()+" got dealt the "+((WeaponCard)returnedCard).getName()+" Weapon card.");
            } else if (returnedCard instanceof MoveCard){
                this.gameOutputTextArea.append("\n "+cardReceiver.getName()+" got dealt the "+((MoveCard)returnedCard).getValue()+" value Move card.");
            } else if (returnedCard instanceof FailureCard){
                this.gameOutputTextArea.append("\n "+cardReceiver.getName()+" got dealt the "+((FailureCard)returnedCard).getValue()+" value Failure card.");
            }
            return returnedCard;

        } else {
            //return first card and delete it from deck
            returnedCard = deck.get(0);
            deck.remove(0);

            //print what card is
            if (returnedCard instanceof RoomCard){
                this.gameOutputTextArea.append("\n"+cardReceiver.getName()+" got dealt the "+((RoomCard)returnedCard).getName()+" Room card.");
            } else if (returnedCard instanceof SpecialWeaponCard){
                this.gameOutputTextArea.append("\n"+cardReceiver.getName()+" got dealt the "+((SpecialWeaponCard)returnedCard).getName()+" Weapon card.");
            } else if (returnedCard instanceof WeaponCard){
                this.gameOutputTextArea.append("\n"+cardReceiver.getName()+" got dealt the "+((WeaponCard)returnedCard).getName()+" Weapon card.");
            } else if (returnedCard instanceof MoveCard){
                this.gameOutputTextArea.append("\n"+cardReceiver.getName()+" got dealt the "+((MoveCard)returnedCard).getValue()+" value Move card.");
            } else if (returnedCard instanceof FailureCard){
                this.gameOutputTextArea.append("\n"+cardReceiver.getName()+" got dealt the "+((FailureCard)returnedCard).getValue()+" value Failure card.");
            }
            return returnedCard;
        }
    }

    public void add(ICard card){
        //check type of card
        //      if failure
        //          trash (remove reference)
        //      if !failure
        //          add to discard
         if (card instanceof FailureCard){
            card = null;
        } else {
            discard.add(card);
        }
    }
}
