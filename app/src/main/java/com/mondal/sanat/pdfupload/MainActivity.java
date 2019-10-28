package com.mondal.sanat.pdfupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(10)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == 10 && resultCode == RESULT_OK){
            File f  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            String content_type  = getMimeType(f.getPath());
            String file_path = f.getAbsolutePath();
            Log.i(TAG, "onActivityResult: file_path===>>>" + file_path);
            Log.i(TAG, "onActivityResult: content_type====>>>" + content_type);

            try {
                byte[] buffer = new byte[(int) f.length() + 100];
                int length = new FileInputStream(f).read(buffer);
                String base64 = Base64.encodeToString(buffer, 0, length,
                        Base64.DEFAULT);
                Log.i(TAG, "onActivityResult: ");

            } catch (Exception ex){
                Log.i(TAG, "onActivityResult: Error: " + ex.getMessage());
            }

            int index = file_path.lastIndexOf(".");
            String test = String.valueOf(file_path);
            String[] s = test.split(".");
            String t = s[0];
            String t1 = s[1];
            String yourCuttedString = file_path.substring(1, index);

            if(content_type.equalsIgnoreCase("image/jpeg") ||
                    content_type.equalsIgnoreCase("image/jpg") ||
                    content_type.equalsIgnoreCase("image/png") ||
                    content_type.equalsIgnoreCase("image/gif")){
                // Image section
                Toast.makeText(this, "Image section", Toast.LENGTH_SHORT).show();

            } else if (content_type.equalsIgnoreCase("application/pdf")){
                // PDF section
                Toast.makeText(this, "PDF section", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Un supported formate", Toast.LENGTH_SHORT).show();
            }
            
        }
    }

    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
