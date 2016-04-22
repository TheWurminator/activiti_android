package rendezvous.activiti;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by amona_000 on 4/21/2016.
 */
public class ActivitiAdapter extends ArrayAdapter<ActivitiListModel>  {

    private Activity activity;
    private ArrayList<ActivitiListModel> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ActivitiListModel tempValues = null;

    public ActivitiAdapter(Activity a, ArrayList<ActivitiListModel> data){
        super(a, R.layout.activiti_list_item, data);
        activity = a;
        this.data = data;
    }

    public int getCount() {
        return (data.size()==0)?1:data.size();
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activiti_list_item, parent, false);
        if(data.size()==0)return rowView;
        TextView txtTitle = (TextView) rowView.findViewById(R.id.text_title);
        TextView textDate = (TextView) rowView.findViewById(R.id.text_date);
        TextView description = (TextView) rowView.findViewById(R.id.text_description);

        txtTitle.setText(data.get(position).title);
        textDate.setText(data.get(position).dateStart.formatedDateTime());
        description.setText(data.get(position).description);
        return rowView;
    }
}
