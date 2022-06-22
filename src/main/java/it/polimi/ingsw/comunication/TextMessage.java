package it.polimi.ingsw.comunication;

/**
 * Template class to encode and decode json as simple messages with three different fields

 */
public class TextMessage {

   public String type;
   public String context;
   public String message;


    /**
     * Constructor method
     * @param type type of the message
     * @param msg payload of the message
     */
    public TextMessage(String type, String msg) {
        this.type = type;
        this.message = msg;
    }
    /**
     * Constructor method
     * @param type type of the message. Example msg, error, confirmation, command
     * @param context context of the message
     * @param msg payload of the message
     */
    public TextMessage(String type, String context, String msg){
        this.type = type;
        this.context = context;
        this.message = msg;
    }

}
