package com.example.asus.tugas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Rincian2 extends AppCompatActivity {

    private Button proses , btn;
    private ImageView imageview, tentang , back;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    String email, nohp , topup , atm;
    TextView email1 , nohp1 , norek1 , topup1;
    private DatabaseReference tempatRef;
    private String jenisStr;
    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl;

    private StorageReference PostsImageReference;

    private Uri contentURI;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rincian2.this, Iphone.class);
                startActivity(intent);
            }
        });
        tentang = findViewById(R.id.tentang);
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rincian2.this, Tentang.class);
                startActivity(intent);
            }
        });
        proses = (Button) findViewById(R.id.proses);

        tempatRef = FirebaseDatabase.getInstance().getReference().child("Iphone");
        PostsImageReference = FirebaseStorage.getInstance().getReference();


        btn = (Button) findViewById(R.id.btn);
        imageview = (ImageView) findViewById(R.id.iv);
        email1 = findViewById(R.id.email);
        nohp1 = findViewById(R.id.nohp);
        norek1 = findViewById(R.id.norek);
        topup1 = findViewById(R.id.topup);

        email = getIntent().getExtras().get("name").toString();
        nohp = getIntent().getExtras().get("No Hp").toString();
        topup= getIntent().getExtras().get("Top Up").toString();
        atm = getIntent().getExtras().get("Via Atm").toString();

        email1.setText( email );
        nohp1.setText(nohp);
        topup1.setText(topup);

        if ( atm.equals("BCA")){
            norek1.setText("294 - 0560 - 198");
        }
        if ( atm.equals("MANDIRI")){
            norek1.setText("070 - 00 - 01401982 - 5 ");
        }
        if ( atm.equals("BNI")){
            norek1.setText("0469576763");

        }
        if ( atm.equals("BRI")){
            norek1.setText("2092 - 01 - 008890 - 50 - 1");
        }

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoringImageToFirebaseStorage();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }

    private void saveIphone() {
        String emaill = email1.getText().toString();
        String nohpl = nohp1.getText().toString();
        String topupl = topup1.getText().toString();
        final String tmp_key = tempatRef.push().getKey();
        final HashMap iphonemap =  new HashMap();
        iphonemap.put("Email", emaill);
        iphonemap.put("No Hp", nohpl);
        iphonemap.put("Top Up", topupl);
        iphonemap.put("gambar1", downloadUrl);

        tempatRef.child(tmp_key).updateChildren(iphonemap)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Rincian2.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                            Intent gambar1Intent = new Intent(Rincian2.this, MainActivity.class);
                            startActivity(gambar1Intent);
                        }
                    }
                });
    }




    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(Rincian2.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Rincian2.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);

            saveImage(thumbnail);
            Toast.makeText(Rincian2.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void StoringImageToFirebaseStorage() {

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath = PostsImageReference.child("Bukti Tf").child(contentURI.getLastPathSegment() + postRandomName + ".jpg");

        filepath.putFile(contentURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            saveIphone();
                        }
                    });


                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(Rincian2.this, "Error occured: " + message, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}