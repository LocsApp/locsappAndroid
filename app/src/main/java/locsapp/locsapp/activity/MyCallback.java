package locsapp.locsapp.activity;

import locsapp.locsapp.models.User;

/**
 * Created by Damien on 3/22/2016.
 */
public interface MyCallback {
    void successCallback(String tag, Object val);
    void errorCallback(String tag, Object val);
}
