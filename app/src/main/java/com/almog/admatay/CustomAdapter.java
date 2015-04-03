package com.almog.admatay;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> arrayList = new ArrayList<>();
    private static boolean[] mItemChecked;

    public CustomAdapter(Context context, ArrayList<String> arrayList, boolean[] mItemChecked) {
        this.context = context;
        this.arrayList = arrayList;
        this.mItemChecked = mItemChecked;
        /*mItemChecked = new boolean[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            mItemChecked[i] = true;
        }*/
        /*for (int i = 0; i < arrayList.size(); i++) {
            mItemChecked[i] = true;
        }*/
    }

    private static class ViewHolder {
        TextView tv;
        CheckBox cb;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public boolean itemIsChecked(int position){
        return mItemChecked[position];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list, parent, false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.nameTV);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.cb.setTag(Integer.valueOf(position) ); // set the tag so we can identify the correct row in the listener
        //holder.cb.setChecked(mItemChecked[position]); // set the status as we stored it
        holder.tv.setText(arrayList.get(position));
        holder.cb.setChecked(mItemChecked[position]);
        holder.cb.setTag(position);
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItemChecked[position] = isChecked;
            }
        });
        return convertView;
    }
}