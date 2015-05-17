package com.almog.admatay;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {


    private Context context;

    protected List<CustomList> customList;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<CustomList> list) {
        this.context = context;
        this.customList = list;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return customList.size();
    }

    @Override
    public Object getItem(int position) {
        return customList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.custom_list, parent, false);

            holder.start = (EditText) convertView.findViewById(R.id.start);
            holder.end = (EditText) convertView.findViewById(R.id.end);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        CustomList list = customList.get(position);
        holder.start.setText(list.getStart()+" ");
        holder.end.setText(list.getEnd()+" ");
        holder.name.setText(list.getName());
        holder.time.setText(list.getTime()+" ");

        return convertView;
    }

    private class ViewHolder {
        EditText start;
        EditText end;
        TextView name;
        TextView time;
    }
}