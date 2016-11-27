package demaikel.chatflash.Views.main.drawer;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.github.siyamed.shapeimageview.OctogonImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

import demaikel.chatflash.R;
import demaikel.chatflash.Views.login.FullscreenActivity;
import demaikel.chatflash.data.UserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class DraweFragment extends Fragment implements PhotoValidationCallback {

    private MagicalCamera magicalCamera;
    private OctogonImageView avatarView;

    public DraweFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.logoutTv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        avatarView = (OctogonImageView) view.findViewById(R.id.userAvatar);

        new PhotoValidation(getContext(), this).init();

        TextView nickTv = (TextView) view.findViewById(R.id.userNick);
        nickTv.setText(new UserData().email());

    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        new PhotoData(getContext()).saveUrl(null);
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode) {
            magicalCamera.resultPhoto(requestCode, resultCode, data);
            Bitmap photo = magicalCamera.getMyPhoto();
            avatarView.setImageBitmap(photo);

            new PhotoToServer(getContext()).send(magicalCamera, photo);

        } else {
            selfie();
        }
    }

    @Override
    public void selfie() {
        magicalCamera = new MagicalCamera(getActivity(), 600);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Tomate una Selfie");
        dialog.setMessage("Para completar tu perfil debes tomarte una selfie");
        dialog.setPositiveButton("selfie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (magicalCamera.takeFragmentPhoto()) {
                    startActivityForResult(magicalCamera.getIntentFragment(), MagicalCamera.TAKE_PHOTO);
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void setPhoto(String url) {
        Picasso.with(getContext()).load(url).into(avatarView);

    }
}