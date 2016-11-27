package demaikel.chatflash.Views.chat;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import demaikel.chatflash.data.FirebaseRef;
import demaikel.chatflash.data.UserData;
import demaikel.chatflash.models.Message;

/**
 * Created by Michael on 19-11-2016.
 */

public class CreateMessage {

    private String chatId, message;
    private Message msg;

    private CreateMessageCallback callback;

    public CreateMessage(String chatId, String message, CreateMessageCallback callback) {
        this.chatId = chatId;
        this.message = message;
        this.callback = callback;
        String userEmail = new UserData().email();
        msg = new Message(message, userEmail);
    }

    public void send() {

        if (msg.message.trim().length() >0){
            new FirebaseRef().messages().child(chatId).push().setValue(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    callback.clear();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.error("No se pudo enviar, intentelo más tarde");
                }
            });
        }else {
            callback.clear();
        }
    }
}
