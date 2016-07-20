package locsapp.locsapp.adapter;

import android.app.Activity;
import android.content.Context;
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

import java.util.List;

import locsapp.locsapp.R;
import locsapp.locsapp.activity.HomeActivity;
import locsapp.locsapp.interfaces.ArticleOverviewAdapterCallback;
import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.Question;

/**
 * Created by Damien on 1/18/2016.
 */
public class QuestionsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    int i = 0;
    Question tempValues=null;
    private Activity activity;
    private List<Question> data;

    public QuestionsAdapter(Activity a, List<Question> d){
        activity = a;
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
            vi = inflater.inflate(R.layout.questions_list, null);

            holder = new ViewHolder();
            holder.author = (TextView) vi.findViewById(R.id.question_author);
            holder.content = (TextView) vi.findViewById(R.id.question_content);

            vi.setTag(holder);
        }

        else {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0) {
        }
        else {
            tempValues = data.get(position);
            holder.author.setText("By " + tempValues.authorName);
            holder.content.setText(tempValues.content);
            Log.i("Question content", tempValues.content);
        }
        return vi;
    }

    public static class ViewHolder {
        public TextView author;
        public TextView content;
    }
}
