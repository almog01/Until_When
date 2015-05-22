package com.almog.admatay;


import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    /*DateTime shiftStart;
    DateTime shiftEnd;*/

    private Context context;
    List<CustomList> customList;
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
        final ViewHolder holder;
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

        final CustomList list = customList.get(position);
        //final int[] startHour = {list.getStartHour()};
        //final int[] startMinute = {list.getStartMinute()};
        //final int[] endHour = {list.getEndHour()};
        //final int[] endMinute = {list.getEndMinute()};
        holder.start.setText(String.format("%02d:%02d", list.getStartHour(), list.getStartMinute()));
        holder.end.setText(String.format("%02d:%02d", list.getEndHour(), list.getEndMinute()));
        holder.name.setText(list.getName());
        String length = getLength(list.getShiftStart(), list.getShiftEnd());
        holder.time.setText(length);
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picker.timePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        list.setStartHour(selectedHour);
                        list.setStartMinute(selectedMinute);
                        holder.start.setText(String.format("%02d:%02d", list.getStartHour(), list.getStartMinute()));
                        int repair = 1;
                        if (list.getEndHour() < list.getStartHour()){
                            repair = 2;
                        }else if (list.getStartHour() == list.getEndHour() && list.getEndMinute() <= list.getStartMinute()){
                            repair = 2;
                        }
                        DateTime shiftStart = new DateTime(2015, 1, 1, list.getStartHour(), list.getStartMinute());
                        DateTime shiftEnd = new DateTime(2015, 1, repair, list.getEndHour(), list.getEndMinute());
                        list.setShiftStart(shiftStart);
                        list.setShiftEnd(shiftEnd);
                        String length = getLength(list.getShiftStart(), list.getShiftEnd());
                        holder.time.setText(length);
                    }
                }, list.getStartHour(), list.getStartMinute());
            }
        });
        holder.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picker.timePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        list.setEndHour(selectedHour);
                        list.setEndMinute(selectedMinute);
                        holder.end.setText(String.format("%02d:%02d", list.getEndHour(), list.getEndMinute()));
                        int repair = 1;
                        if (list.getEndHour() < list.getStartHour()){
                            repair = 2;
                        }else if (list.getStartHour() == list.getEndHour() && list.getEndMinute() <= list.getStartMinute()){
                            repair = 2;
                        }
                        DateTime shiftStart = new DateTime(2015, 1, 1, list.getStartHour(), list.getStartMinute());
                        DateTime shiftEnd = new DateTime(2015, 1, repair, list.getEndHour(), list.getEndMinute());
                        list.setShiftStart(shiftStart);
                        list.setShiftEnd(shiftEnd);
                        String length = getLength(list.getShiftStart(), list.getShiftEnd());
                        holder.time.setText(length);
                    }
                }, list.getEndHour(), list.getEndMinute());
            }
        });
        return convertView;
    }

    private class ViewHolder {
        EditText start;
        EditText end;
        TextView name;
        TextView time;
    }

    public String getLength(DateTime start, DateTime end){
        Duration duration = new Duration(start, end);
        Period p = duration.toPeriod();
        PeriodFormatter pf = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().toFormatter();
        return pf.print(p);
    }
}