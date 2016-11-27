package demaikel.chatflash.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Michael on 15-11-2016.
 */

public class FirebaseRef {

    private DatabaseReference root(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public  DatabaseReference users(){
        return root().child("user");
    }

    public DatabaseReference chatList(){        return root().child("chat_list");    }

    public DatabaseReference messages(){        return root().child("messages");    }



}
