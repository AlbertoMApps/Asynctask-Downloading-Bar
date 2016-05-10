package development.alberto.com.slindenerdasynctaskexercisedownloading;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alber on 09/05/2016.
 */
public class NonUIFragment extends Fragment {

    public MyTask myTask;
    private Activity activity;

    public void beginTask(String params){
        myTask = new MyTask(activity);
        myTask.execute(params);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        if(myTask!=null) {
            myTask.onAttach(this.activity);
        }
        Log.i("TAG", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("TAG","onCreateView");
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TAG","onActivityCreated");
        setRetainInstance(true); //Dont destroy the fragment
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("TAG","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG","onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("TAG","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myTask.onDetach();
        Log.i("TAG","onDetach");
    }
}
