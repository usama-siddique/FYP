package com.example.smsimagecommunicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;


public class MainActivity extends AppCompatActivity {




    boolean image_selected = false;
    static int PICK_IMAGE = 1;
    static int CAPTURE_IMAGE=33;




    LinearLayout images_list;
    ViewSwitcher viewSwitcher;

    TextView progress;
    Button send;
    //just o test
    static TextView textView;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get References
        viewSwitcher=findViewById(R.id.view_switcher);
        images_list=findViewById(R.id.images_list);
        progress=findViewById(R.id.progress);
        send=findViewById(R.id.send);

        textView=findViewById(R.id.a);

    }
    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                image_selected = true;
                //decode Intent to String
                Uri j = data.getData();
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(j);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageView imageView = findViewById(R.id.image_view);
                imageView.setImageBitmap(bitmap);


                //button settings
                send.setText("Send");
                send.setEnabled(true);
                progress.setText("");

            }

        }
        else if(requestCode == CAPTURE_IMAGE&&resultCode==RESULT_OK) {
            if (data != null) {
                image_selected = true;
                //decode Intent to String
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                Bitmap bitmap = photo;
                ImageView imageView = findViewById(R.id.image_view);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(this, "Image Selected From Camera!", Toast.LENGTH_SHORT).show();


                send.setText("Send");
                send.setEnabled(true);
                progress.setText("");
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    public void openCamera(View view) {
        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAPTURE_IMAGE);
    }
}
