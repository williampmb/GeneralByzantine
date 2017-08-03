/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byzantinebailuremodelclient;

import java.util.Random;

/**
 *
 * @author willi
 */
public enum Personality {
    LOYAL(0),
    TRAITOR(1);
    
    private int character;

    public int getValue() {
        return character;
    }

    public void setValue(int value) {
        this.character = value;
    }

    private Personality(int v) {
        character = v;
    }
    
    public static Personality randomPersonality(int probability){
        Personality character;
        Random r = new Random();
        int loyal = r.nextInt(100);
        if (loyal > probability) {
            character = Personality.LOYAL;
        } else {
            character = Personality.TRAITOR;
        }
        
        return character;
    }
    
    
}
