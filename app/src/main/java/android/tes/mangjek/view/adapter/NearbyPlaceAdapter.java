package android.tes.mangjek.view.adapter;

import android.content.Context;
import android.tes.mangjek.model.PlaceData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class NearbyPlaceAdapter extends BaseAdapter {
    ArrayList<PlaceData> placeDataList;
    Context context;
    public NearbyPlaceAdapter(Context context, ArrayList<PlaceData> placeDataList) {
        this.context=context;
        this.placeDataList=placeDataList;
    }

    @Override
    public int getCount() {
        return placeDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return placeDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        PlaceData placeData = placeDataList.get(i);
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(
                   android.R.layout.simple_spinner_dropdown_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView)convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        }
        viewHolder.textView.setText(placeData.getPlaceName());
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
    }
}
