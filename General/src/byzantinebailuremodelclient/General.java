/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byzantinebailuremodelclient;

/**
 *
 * @author willi
 */
public class General {

    private final int id;
    //parameter that it passed on the args in Main
    private int numberOfGenerals = Main.numberOfGenerals;
    private String decision;
    static String splitTag = "->";
    public int confirmation[];
    public int online[];
    private Personality character;
    private boolean ready;
    public boolean receivedFromGeneral;
    String AlmightyOrder;
    //less probability to be traitor. more probability more chance to be traitor
    static int probabilityPersonality = Main.probabilityOfTraitors;

    public int[] getOnline() {
        return online;
    }

    public void setOnline(int[] online) {
        this.online = online;
    }

    public void setOnlineById(int id) {
        this.online[id] = 1;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getId() {
        return id;
    }

    public General(String msg) throws Exception {
        String[] tk = msg.split(splitTag);
        if (tk[0].equals("register")) {
            this.id = Integer.parseInt(tk[1]);
        } else {
            throw new Exception("Invalid registration");
        }

//        decision = character == Personality.LOYAL ? "1" : "-1";
        confirmation = new int[numberOfGenerals];
        online = new int[numberOfGenerals];
        receivedFromGeneral = false;

        if (this.id == 0) {
            ready = false;
            online[0] = 1;
            character = character = Personality.LOYAL;
        } else {
            ready = true;
            character = Personality.randomPersonality(probabilityPersonality);
        }
        decision = character == Personality.LOYAL ? "1" : "-1";
        confirmation[id] = Integer.valueOf(decision);
    }


    String getOrder() {
        if (decision == null) {
            double count = 0;
            for (int i = 0; i < confirmation.length; i++) {
                if (confirmation[i] == 1) {
                    count++;
                }
            }
            if (count >= ((int) ((2.0 * numberOfGenerals / 3.0) + 1.0))) {
                decision = "ATTACK";
            } else {
                decision = "HOLD";
            }
        }
        return decision;
    }

    boolean analiseVotes() {
        int traitors = 0;
        int notReceived = 0;
        int loyals = 0;
        for (int i = 0; i < confirmation.length; i++) {
            if (confirmation[i] < 0) {
                traitors++;
            } else if (confirmation[i] == 0) {
                notReceived++;
            } else {
                loyals++;
            }
        }
        
        if (notReceived != 0) {
            return false;
        } else {
            int check = ((int)((2.0 * numberOfGenerals / 3.0) + 1.0));
            if (loyals >= check) {
                decision = "ATTACK";
            } else {
                decision = "HOLD";
            }

            return true;
        }

    }

    String thinkHisDecision(String receiveOrder) {
        String replyGeneralOrder = character == Personality.LOYAL ? receiveOrder : "-1";
        return replyGeneralOrder;
    }

    void checkReady() {
        for (int i : online) {
            if (i != 1) {
                return;
            }
        }
        ready = true;
    }

}
