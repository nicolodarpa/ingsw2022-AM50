package it.polimi.ingsw.comunication;

/**
 * Template class to encode and decode json with a command and two optional values
 */
public class Command {

    /**
     * Command name
     */
    public String command;
    /**
     * Optional value 1
     */
    public String value1;

    /**
     * Optional value 2
     */
    public String value2;

    public Command(String command, String value1){
        this.command = command;
        this.value1 = value1;
        this.value2 = "1";
    }

    public Command(String command, String value1, String value2){
        this.command = command;
        this.value1 = value1;
        this.value2 = value2;
    }
}
