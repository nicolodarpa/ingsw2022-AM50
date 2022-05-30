package it.polimi.ingsw.comunication;

import org.w3c.dom.Text;

public class TextMessage {

   public String type;
   public String context;
   public String message;


    public TextMessage(String msg) {
        this.type = "msg";
        this.message = msg;
    }

    public TextMessage(String type, String msg) {
        this.type = type;
        this.message = msg;
    }

    public TextMessage(String type, String context, String msg){
        this.type = type;
        this.context = context;
        this.message = msg;
    }

}
