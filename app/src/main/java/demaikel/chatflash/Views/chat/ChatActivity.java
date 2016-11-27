package demaikel.chatflash.Views.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import demaikel.chatflash.R;
import demaikel.chatflash.Views.main.chatlist.ChatListFragment;

import static demaikel.chatflash.Views.main.chatlist.ChatListFragment.CHAT_ID;
import static demaikel.chatflash.Views.main.chatlist.ChatListFragment.OTHER_USER;

public class ChatActivity extends AppCompatActivity implements  CreateMessageCallback{

    private EditText userInput;
    private ImageButton sendBtn;
    private String chatId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatId = getIntent().getStringExtra(ChatListFragment.CHAT_ID);
        String otherName = getIntent().getStringExtra(ChatListFragment.OTHER_USER);
        getSupportActionBar().setTitle(otherName);


        userInput = (EditText) findViewById(R.id.messagesEt);
        sendBtn = (ImageButton) findViewById(R.id.sendBtn);
        SetSendBtn();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new UpdateChat(chatId).send();;

    }

    private void SetSendBtn (){
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = userInput.getText().toString();
                new CreateMessage(chatId, message, ChatActivity.this).send();
            }
        });
    }

    @Override
    public void clear() {
        userInput.setText("");
        if (userInput.getError() != null){
            userInput.setError(null);
        }
    }

    @Override
    public void error(String error) {
        userInput.setError(error);
    }
}
