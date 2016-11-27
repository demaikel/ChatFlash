package demaikel.chatflash.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Michael on 14-11-2016.
 */

public class UserData {

    public FirebaseUser user () {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String email (){
        return user().getEmail();
    }

}
