package development.alberto.com.slindenerdasynctaskexercisedownloading;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.res.Configuration.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText selectionText;
    private ListView chooseImagesList;
    private String listOfImages[];
    private ProgressBar progressBar;
    private LinearLayout loadingSection = null;
    private int calculatedProgress;
    private int contentLength;
    private NonUIFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectionText = (EditText)findViewById(R.id.downloadURL);
        chooseImagesList = (ListView)findViewById(R.id.urlList);
        listOfImages = getResources().getStringArray(R.array.imageUrls);
        progressBar = (ProgressBar) findViewById(R.id.downloadProgress);
        chooseImagesList.setOnItemClickListener(this);
        //Activity started for the first time
        if(savedInstanceState==null){
            fragment = new NonUIFragment();
            getSupportFragmentManager().beginTransaction().add(fragment, "Task").commit();
        } else {
            fragment = (NonUIFragment) getSupportFragmentManager().findFragmentByTag("Task");
        }
        if(fragment!=null){
            if(fragment.myTask!=null && fragment.myTask.getStatus() == AsyncTask.Status.RUNNING){
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
    public void downloadImage(View view){
        if(selectionText.getText()!=null && selectionText.length()>0){
//            MyTask myTask= new MyTask();
//            myTask.execute(selectionText.getText().toString());
            fragment.beginTask(selectionText.getText().toString());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String imgUrl = listOfImages[position];
        selectionText.setText(imgUrl);
    }
    public  void updateProgress(int progress){
        progressBar.setProgress(progress);
    }
    public void showProgressBarBeforeDownloading(){
        if(fragment.myTask!=null){
            if((progressBar.getVisibility() != View.VISIBLE) && progressBar.getProgress()!=progressBar.getMax()){
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideProgressBarAfterDownloading(){
        if(fragment.myTask!=null){
            if(progressBar.getVisibility()==View.VISIBLE){
                progressBar.setVisibility(View.GONE);
            }
        }
    }

//    class MyTask extends AsyncTask<String, Integer, Boolean>{
//        private int counter = 0;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//            //Lock the screen to rotation
////            if(MainActivity.this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
////                MainActivity.this.setRequestedOrientation(ORIENTATION_PORTRAIT);
////            } else {
////                MainActivity.this.setRequestedOrientation(ORIENTATION_LANDSCAPE);
////            }
//        }
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            boolean successfull = false;
//            URL downloadURL = null;
//            HttpURLConnection connection= null;
//            InputStream inputStream = null;
//            FileOutputStream fileOutputStream = null; //saving data in the sd card
//            File f = null;
//            try {
//                downloadURL = new URL(params[0]);
//                connection = (HttpURLConnection) downloadURL.openConnection();
//                contentLength = connection.getContentLength();
//                inputStream = connection.getInputStream();
//                f = new File(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + //this will give me the path to storage the pictures in the sd card
//                        "/" + Uri.parse(params[0]).getLastPathSegment() ); //this gives me the last path segment of my url like /image.jpg
//                Log.i("path: " , "" + f.getAbsolutePath());
////                fileOutputStream =  new FileOutputStream(f);
//                int read = -1;
//                byte [] buffer = new byte[1024];
//                while ((read = inputStream.read(buffer) ) != -1){ //Check if it is reading
//                    Log.i("Read: ", ""+ read);
//                    //fileOutputStream.write(buffer, 0, read);
//                    counter = counter + read;
//                    publishProgress(counter); //Integer
//                }
//                successfull = true;
//
//            }catch (IOException e){
//                e.printStackTrace();
//            } finally {
//                if(connection!=null) {
//                    connection.disconnect();
//                }
//                if(inputStream!=null){
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                    }
//                }
//                if(fileOutputStream!=null){
//                    try {
//                        fileOutputStream.close();
//                    } catch (IOException e) {
//                    }
//                }
//            }
//
//            return successfull;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            calculatedProgress = (int)(((double)values[0]/contentLength)*100);
//            progressBar.setProgress(calculatedProgress);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            progressBar.setVisibility(View.INVISIBLE);
//            //Lock the screen to rotation
////            MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//        }
//    }
}
