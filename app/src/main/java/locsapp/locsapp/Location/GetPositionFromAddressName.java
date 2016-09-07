package locsapp.locsapp.Location;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import locsapp.locsapp.App;

/**
 * Created by Damien on 16/08/2016.
 */

public class GetPositionFromAddressName extends AsyncTask<String, Void, Address> {

    public interface LocationCallback {
        void location(Address address);
    }

    public LocationCallback callback = null;
    List<Address> address;

    public GetPositionFromAddressName(LocationCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Address doInBackground(String... strings) {
        try {
            Geocoder geoCoder = new Geocoder(App.getContext());
            address = geoCoder.getFromLocationName(strings[0], 5);
            if (address==null) {
                return null;
            }
            return (address.get(0));
        }
        catch (IOException e){

        }
        return null;
    }

    @Override
    protected void onPostExecute(Address s) {
        this.callback.location(s);
        super.onPostExecute(s);
    }
}
