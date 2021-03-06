package demaikel.chatflash.Views.main.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import demaikel.chatflash.data.FirebaseRef;
import demaikel.chatflash.data.UserData;
import demaikel.chatflash.models.User;

/**
 * Created by Michael on 14-11-2016.
 */

public class PhotoToServer {

    private Context context;

    public PhotoToServer(Context context) {
        this.context = context;
    }

    public void send(MagicalCamera magicalCamera, Bitmap photo){
        String name = "flasChat_avatar_" + System.currentTimeMillis();
        magicalCamera.savePhotoInMemoryDevice(photo,name,"flashChat", MagicalCamera.JPEG,false);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final UserData currentUser = new UserData();
        final String photoServer = currentUser.email().replace("@", "_at_").replace(".", "_dot_") + ".jpeg";
        String refUrl = "gs://chatflash-c0920.appspot.com/avatars/"+photoServer;
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(refUrl);
        File file = new File ("/storage/sdcard1/Pictures/flashChat/"+name+".jpeg");
        storageReference.putFile(Uri.fromFile(file)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = "https://firebasestorage.googleapis.com/v0/b/chatflash-c0920.appspot.com/o/avatars%2F"+ photoServer + "?alt=media";
                new PhotoData(context).saveUrl(url);
                sendUser(currentUser, url);
            }
        });
    }
    private void sendUser(UserData currentUser, String photoUrl) {
        String uid = currentUser.user().getUid();
        User user = new User(currentUser.user().getDisplayName(),
                             currentUser.email(),
                             photoUrl,
                             uid);


        new FirebaseRef().users().child(uid).setValue(user);
    }


}
