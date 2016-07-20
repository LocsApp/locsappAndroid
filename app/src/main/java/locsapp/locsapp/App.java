package locsapp.locsapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Damien on 7/20/2016 for LocsApp.
 */
public class App extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
