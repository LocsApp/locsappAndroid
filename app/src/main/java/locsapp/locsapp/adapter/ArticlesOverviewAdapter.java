package locsapp.locsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.interfaces.ArticleOverviewAdapterCallback;
import locsapp.locsapp.models.Article;

/**
 * Created by Damien on 1/18/2016.
 */
public class ArticlesOverviewAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    int i = 0;
    Article tempValues=null;
    private Activity activity;
    private List<Article> data;
    private ArticleOverviewAdapterCallback callback;

    public ArticlesOverviewAdapter(Activity a, List<Article> d, ArticleOverviewAdapterCallback c){
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        callback = c;
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
            vi = inflater.inflate(R.layout.articles_overview_list, null);

            holder = new ViewHolder();
            holder.title = (TextView) vi.findViewById(R.id.title);
            holder.price = (TextView) vi.findViewById(R.id.price);
            holder.description= (TextView) vi.findViewById(R.id.description);
            holder.image = (ImageView) vi.findViewById(R.id.image);
            holder.clickZone = (RelativeLayout) vi.findViewById(R.id.clickzone);
            holder.favorite = (ImageView) vi.findViewById(R.id.favorite);

            vi.setTag(holder);
        }

        else {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0) {
            holder.title.setText("No Datas");
        }
        else {
            tempValues = data.get(position);
//            Log.i("ADD ARTICLE: ", tempValues.getTitle() + " " + tempValues.getId());
            holder.title.setText(tempValues.getTitle());
            holder.description.setText(tempValues.getDescription());
            holder.price.setText(tempValues.getPrice().toString() + " €");
            Picasso.with(activity)
                    .load(activity.getResources().getString(R.string.api_url) + tempValues.getThumbnail())
                    .placeholder(R.drawable.blank2)
                    .error(R.drawable.blank2)
                    .resize(150, 150)
                    .into(holder.image);
            holder.clickZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parentRow = (View) v.getParent();
                    ListView listView = (ListView) parentRow.getParent();
                    final int position = listView.getPositionForView(parentRow);
                    callback.showArticle(data.get(position));
                }
            });
            boolean isFav = false;
            for (int i = 0; i < ((HomeActivity) activity).mFavorites.articles.size(); i++) {
                if (((HomeActivity) activity).mFavorites.articles.get(i).getId_article().equals(tempValues.getId())) {
                    isFav = true;
                }
            }
            if (isFav) {
                holder.favorite.setVisibility(View.GONE);
            }
            else {
                holder.favorite.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                holder.favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View parentRow = (View) v.getParent();
                        ListView listView = (ListView) parentRow.getParent();
                        final int position = listView.getPositionForView(parentRow);
                        callback.addFavorite(data.get(position).getId());
                    }
                });
            }
        }
        return vi;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView price;
        public TextView description;
        public ImageView image;
        public RelativeLayout clickZone;
        public ImageView favorite;
    }
}
