package it.polimi.ingsw.comunication;

public class Command {

    public String cmd;
    public String value1;

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
