package it.polimi.ingsw.comunication;

/**
 * Template class to encode and decode json with a command and two optional values
 */
public class Command {

    /**
     * Command name
     */
    public String cmd;
    /**
     * Optional value 1
     */
    public String value1;

    /**
     * Optional value 2
     */
    public String value2;

    public Command(String cmd, String value1){
        this.cmd = cmd;
        this.value1 = value1;
        this.value2 = "1";
    }

    public Command(String cmd, String value1, String value2){
        this.cmd = cmd;
        this.value1 = value1;
        this.value2 = value2;
    }
}
