package com.example.jatingarg.imagedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jatingarg on 07/04/17.
 */

public class Downloader extends AsyncTask<String,String,String> {
    private static final String TAG = "Downloader";
    private Context mContext;
    private ImageDownloadListener mListener;

    public interface ImageDownloadListener{
        void imageAvailable(Bitmap map);
        void progressRecorded(int progress);
    }

    public Downloader(Context mContext, ImageDownloadListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... params) {
        int count;
        try{
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(),8192);
            FileOutputStream outputStream = mContext.openFileOutput("downloadedFile.jpg",Context.MODE_PRIVATE);
            byte data[] = new byte[1024];
            long total = 0;
            while((count = input.read(data))!= -1){
                total += count;
                publishProgress("" + (int)((total*100)/fileLength));
                outputStream.write(data,0,count);
            }

            outputStream.flush();
            outputStream.close();
            input.close();
        }catch (IOException e){
            Log.e(TAG, "doInBackground: Exception occurec while downloading" + e.getMessage());
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.d(TAG, "onProgressUpdate: " + values[0]);
        int progress = Integer.parseInt(values[0]);
        mListener.progressRecorded(progress);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: download complete");
        try{
            FileInputStream fileInputStream = mContext.openFileInput("downloadedFile.jpg");
            BufferedInputStream buf = new BufferedInputStream(fileInputStream);
            Bitmap map = BitmapFactory.decodeStream(buf);
            mListener.imageAvailable(map);
            Log.d(TAG, "onPostExecute: done");
        }catch(FileNotFoundException e){
            Log.e(TAG, "onPostExecute: file not found");
            return;
        }


                
    }
}
