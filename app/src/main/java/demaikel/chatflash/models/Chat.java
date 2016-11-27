package demaikel.chatflash.models;

/**
 * Created by Michael on 16-11-2016.
 */

public class Chat {

    public String chat_id, creator, creator_photo, receiver, receiver_photo;
    public boolean notification;
    public long timestamp;

    public Chat() {
    }

    public Chat(String chat_id, String creator, String creator_photo, String receiver, String receiver_photo, boolean notification) {
        this.chat_id = chat_id;
        this.creator = creator;
        this.creator_photo = creator_photo;
        this.receiver = receiver;
        this.receiver_photo = receiver_photo;
        this.notification = notification;
    }
}
