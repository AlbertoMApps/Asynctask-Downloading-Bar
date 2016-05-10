package development.alberto.com.slindenerdasynctaskexercisedownloading;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class MyTask extends AsyncTask<String, Integer, Boolean> {
    private int counter = 0;
    private int calculatedProgress;
    private int contentLength;
    private Activity activity;

    public MyTask(Activity activity){
        onAttach(activity);
    }

    //Similar methods to the fragment
    public void onAttach(Activity activity) {
        this.activity = activity;
    }
    public void onDetach(){
        activity = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(activity!=null) {
            ((MainActivity) activity).showProgressBarBeforeDownloading();
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {
        boolean successfull = false;
        URL downloadURL = null;
        HttpURLConnection connection= null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null; //saving data in the sd card
        File f = null;
        try {
            downloadURL = new URL(params[0]);
            connection = (HttpURLConnection) downloadURL.openConnection();
            contentLength = connection.getContentLength();
            inputStream = connection.getInputStream();
            f = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).getAbsolutePath() + //this will give me the path to storage the pictures in the sd card
                    "/" + Uri.parse(params[0]).getLastPathSegment() ); //this gives me the last path segment of my url like /image.jpg
            Log.i("path: " , "" + f.getAbsolutePath());
//                fileOutputStream =  new FileOutputStream(f);
            int read = -1;
            byte [] buffer = new byte[1024];
            while ((read = inputStream.read(buffer) ) != -1){ //Check if it is reading
                Log.i("Read: ", ""+ read);
                //fileOutputStream.write(buffer, 0, read);
                counter = counter + read;
                publishProgress(counter); //Integer
            }
            successfull = true;

        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if(connection!=null) {
                connection.disconnect();
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
        }

        return successfull;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(activity!=null){
            calculatedProgress = (int)(((double)values[0]/contentLength)*100);
            ((MainActivity)activity).updateProgress(calculatedProgress + 1000000);
        } else {
            Toast.makeText(activity.getApplicationContext(), "Skipping progress", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(activity!=null) {
            ((MainActivity) activity).hideProgressBarAfterDownloading();
        }
    }
}
