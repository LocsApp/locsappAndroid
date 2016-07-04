package locsapp.locsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import locsapp.locsapp.R;
import locsapp.locsapp.interfaces.DeleteAddress;
import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.LivingAddress;

/**
 * Created by Damien on 5/18/2016.
 */

public class ShowAdrAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    int i = 0;
    ArrayList<String> tempValues=null;
    private Activity activity;
    private DeleteAddress callback;
    private String list;
    private ArrayList<ArrayList<String>> data;

    public ShowAdrAdapter(Activity a, ArrayList<ArrayList<String>> d, DeleteAddress callback, String list){
        activity = a;
        this.callback = callback;
        this.list = list;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data.size() <= 0){
            return 1;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.show_address_list, null);

            holder = new ViewHolder();
            holder.alias = (TextView) vi.findViewById(R.id.alias);
            holder.name = (TextView) vi.findViewById(R.id.name);
            holder.address = (TextView) vi.findViewById(R.id.address);
            holder.delete = (ImageView) vi.findViewById(R.id.delete);

            vi.setTag(holder);
        }

        else {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0) {
            holder.name.setText("Nothing to show");
        }
        else {
            tempValues = data.get(position);
            final String alias = tempValues.get(0);
            holder.alias.setText(alias);
            String json = tempValues.get(1);
            try {
                JSONObject jsonObj = new JSONObject(json);
                final String firstname = jsonObj.getString("first_name");
                final String lastname = jsonObj.getString("last_name");
                final String address = jsonObj.getString("address");
                final String code = jsonObj.getString("postal_code");
                final String city = jsonObj.getString("city");
                holder.name.setText((firstname + " " + lastname));
                holder.address.setText((address + "\n" + code + ", " + city));
                if (list != null) {
                    holder.delete.setVisibility(View.VISIBLE);
                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (list.equals("living_address")) {
                                callback.deleteLivingAddress(new LivingAddress(alias, address, lastname, firstname, code, city));
                            }
                            else if (list.equals("billing_address")) {
                                callback.deleteBillingAddress(new BillingAddress(alias, address, lastname, firstname, code, city));
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return vi;
    }

    public static class ViewHolder {
        public TextView alias;
        public TextView name;
        public TextView address;
        public ImageView delete;
    }
}
