package locsapp.locsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import locsapp.locsapp.R;
import locsapp.locsapp.models.Article;

/**
 * Created by Damien on 1/18/2016.
 */
public class ArticlesOverviewAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Article> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    int i = 0;

    Article tempValues=null;

    public ArticlesOverviewAdapter(Activity a, ArrayList<Article> d, Resources resLocal){
        activity = a;
        data = d;
        res = resLocal;
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

    public static class ViewHolder {
        public TextView title;
        public TextView price;
        public TextView description;
        public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.articles_overview_list, null);

            holder = new ViewHolder();
            holder.title = (TextView) vi.findViewById(R.id.title);
            holder.price = (TextView) vi.findViewById(R.id.price);
            holder.description= (TextView) vi.findViewById(R.id.description);
            holder.image = (ImageView) vi.findViewById(R.id.image);

            vi.setTag(holder);
        }

        else {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0) {
            holder.title.setText("No Datas");
        }
        else {
            holder.title.setText(tempValues.getTitle());
            holder.description.setText(tempValues.getDescription());
            holder.price.setText(tempValues.getPrice());
            Picasso.with(activity)
                    .load(tempValues.getImage())
                    .resize(50, 50)
                    .into(holder.image);
        }
        return vi;
    }
}
