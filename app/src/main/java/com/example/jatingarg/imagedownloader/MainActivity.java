package com.example.jatingarg.imagedownloader;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements Downloader.ImageDownloadListener {
    private static final String TAG = "MainActivity";
    private ProgressBar mProgressBar;
    private Button downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        downloadBtn = (Button)findViewById(R.id.downloadButton);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Downloader mdownloader = new Downloader(MainActivity.this,MainActivity.this);
                mdownloader.execute("http://hdwallpaperbackgrounds.net/wp-content/uploads/2017/01/images-tiger.jpg");
            }
        });


    }

    @Override
    public void imageAvailable(Bitmap map) {
        ((ImageView) findViewById(R.id.downloadedImage)).setImageBitmap(map);
    }

    @Override
    public void progressRecorded(int progress) {
        mProgressBar.setProgress(progress);
    }
}
