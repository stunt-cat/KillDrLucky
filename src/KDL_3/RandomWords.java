/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */
public class RandomWords {

    String returnWords;

    public String negative(int selector){
        switch (selector){
            case 1: returnWords = "The nincompoop!"; break;
            case 2: returnWords = "The idiot!"; break;
            case 3: returnWords = "The dolt!"; break;
            case 4: returnWords = "The abject fool!"; break;
            case 5: returnWords = "The binary-brained weasel!"; break;
            case 6: returnWords = "Why?! Why?! Why?!"; break;
            case 7: returnWords = "There was no clear reasoning."; break;
            case 8: returnWords = "The loser!"; break;
            case 9: returnWords = "Honestly, why are they even in the game?"; break;
            case 10: returnWords = "I implored the case against to no avail."; break;
        }
        return returnWords;
    }

    public String positive(int selector){
        switch (selector){
            case 1: returnWords = "The legend!"; break;
            case 2: returnWords = "The hero!"; break;
            case 3: returnWords = "Top class!"; break;
            case 4: returnWords = "Woohooooo!"; break;
            case 5: returnWords = "Simply marvellous."; break;
            case 6: returnWords = "Naturally."; break;
            case 7: returnWords = "It is the only sensible course of action."; break;
            case 8: returnWords = "Who wouldn't?"; break;
            case 9: returnWords = "Fingers crossed!"; break;
            case 10: returnWords = "The wonders never cease."; break;
        }
        return returnWords;
    }

    public String failure(int selector){
        switch (selector){
            case 1: returnWords = "Was it really necessary?"; break;
            case 2: returnWords = "Oh for pity's sake..."; break;
            case 3: returnWords = "There was no real need."; break;
            case 4: returnWords = "The cretin!"; break;
            case 5: returnWords = "Give us a break."; break;
            case 6: returnWords = "Will the old bastard ever die?"; break;
            case 7: returnWords = "Wake me up soon."; break;
            case 8: returnWords = "Fail."; break;
            case 9: returnWords = "Yes, they are *that* kind of opponent."; break;
            case 10: returnWords = "This was deemed unacceptable behaviour for guests."; break;
        }
        return returnWords;
    }

    public String winner(int selector){
        switch (selector){
            case 1: returnWords = "\n Winner!"; break;
            case 2: returnWords = "\n Finally!"; break;
            case 3: returnWords = "\n Not so lucky now old bean!"; break;
            case 4: returnWords = "\n Hello World."; break;
            case 5: returnWords = "\n That took some doing!"; break;
            case 6: returnWords = "\n Who could have predicted that?"; break;
            case 7: returnWords = "\n GAME OVER"; break;
            case 8: returnWords = "\n And so ends another delightful evening."; break;
            case 9: returnWords = "\n Luck?! It was skill I tell you!"; break;
            case 10: returnWords = "\n Call that winning?"; break;
        }
        return returnWords;
    }

}
