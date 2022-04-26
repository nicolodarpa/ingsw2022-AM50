package it.polimi.ingsw.comunication;

public class TextMessage {

   public String type;
   public String message;


    public TextMessage(String msg) {
        this.type = "msg";
        this.message = msg;
    }

    public TextMessage(String type, String msg) {
        this.type = type;
        this.message = msg;
    }

}
