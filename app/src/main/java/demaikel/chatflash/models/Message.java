package demaikel.chatflash.models;

/**
 * Created by Michael on 19-11-2016.
 */

public class Message {

    public String message, owner;

    public Message () {
    }

    public Message(String message, String owner) {
        this.message = message;
        this.owner = owner;
    }
}
