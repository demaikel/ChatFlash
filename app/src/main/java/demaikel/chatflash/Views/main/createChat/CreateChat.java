package demaikel.chatflash.Views.main.createChat;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import demaikel.chatflash.Views.main.drawer.PhotoData;
import demaikel.chatflash.data.FirebaseRef;
import demaikel.chatflash.data.UserData;
import demaikel.chatflash.models.Chat;
import demaikel.chatflash.models.User;

import static android.R.attr.key;

/**
 * Created by Michael on 16-11-2016.
 */

public class CreateChat {

    private VerificationCallback callback;
    private String email;
    private Context context;
    private UserData userData = new UserData();
    private User otherUser = new User();

    public CreateChat(Context context, String email, VerificationCallback callback) {
        this.context = context;
        this.email = email;
        this.callback = callback;
    }

    public void init(){
        if (email.trim().length() == 0 || !email.contains(".") || !email.contains("@") || !email.contains(" ")) {
            callback.invalid("Email no valido");
        }else{
            if (email.equals(userData.email())) {
                callback.self("Ese es tu mismo email");
            }else {
                search();
            }
        }
    }
    private void search() {
        new FirebaseRef().users().orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    otherUser = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                    creation();
                }else{
                    callback.notFound("Usuario no Existe");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.notFound("Error del servidor");
            }
        });
    }

    private void creation () {
        String key = userData.email().replace(".", "DOT") + " : " + otherUser.email.replace(".", "DOT");
        Chat chat = new Chat (
                key,
                userData.email(),
                new PhotoData(context).getUrl(),
                otherUser.email,
                otherUser.photo,
                true

        );
        DatabaseReference reference = new FirebaseRef().chatList();
        reference.child(userData.user().getUid()).child(key).setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.success();
            }
        });
        reference.child(otherUser.user_id).child(key).setValue(chat);
    }
}
