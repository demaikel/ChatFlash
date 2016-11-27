package demaikel.chatflash.Views.main.createChat;

/**
 * Created by Michael on 15-11-2016.
 */

public interface VerificationCallback {

    void invalid (String error);
    void self (String error);
    void  notFound (String error);
    void success();

}
