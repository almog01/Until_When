package com.almog.admatay.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.almog.admatay.Mishmeret;
import com.almog.admatay.Picker;
import com.almog.admatay.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    private EditText dateView, dayStartView, dayEndView, nightStartView, nightEndView, dayLengthView, nightLengthView, dayCyclesView, nightCyclesView;
    private RadioGroup dayGroup, nightGroup;
    private Calendar calendar = Calendar.getInstance();
    private int mYear = calendar.get(Calendar.YEAR);
    private int mMonth = calendar.get(Calendar.MONTH);
    private int mDay = calendar.get(Calendar.DAY_OF_MONTH) +1;

    private static int mDayStartHour = 6;
    private static int mDayStartMinute = 00;
    private static int mDayEndHour = 18;
    private static int mDayEndMinute = 00;
    private static int mNightStartHour = 18;
    private static int mNightStartMinute = 00;
    private static int mNightEndHour = 6;
    private static int mNightEndMinute = 00;
    private static int mDayHourLength = 3;
    private static int mDayMinuteLength = 00;
    private static int mNightHourLength = 3;
    private static int mNightMinuteLength = 00;
    private static boolean mIsDayEqually = true;
    private static boolean mIsNightEqually = true;

    private int mDayCycles = 1;
    private int mNightCycles = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dateView = (EditText) getActivity().findViewById(R.id.dateView);
        dateView.setOnClickListener(this);
        dayStartView = (EditText) getActivity().findViewById(R.id.dayStartView);
        dayStartView.setOnClickListener(this);
        dayEndView = (EditText) getActivity().findViewById(R.id.dayEndView);
        dayEndView.setOnClickListener(this);
        nightStartView = (EditText) getActivity().findViewById(R.id.nightStartView);
        nightStartView.setOnClickListener(this);
        nightEndView = (EditText) getActivity().findViewById(R.id.nightEndView);
        nightEndView.setOnClickListener(this);
        dayLengthView = (EditText) getActivity().findViewById(R.id.dayLengthView);
        dayLengthView.setOnClickListener(this);
        nightLengthView = (EditText) getActivity().findViewById(R.id.nightLengthView);
        nightLengthView.setOnClickListener(this);
        dayCyclesView = (EditText) getActivity().findViewById(R.id.dayCyclesView);
        dayCyclesView.setOnClickListener(this);
        nightCyclesView = (EditText) getActivity().findViewById(R.id.nightCyclesView);
        nightCyclesView.setOnClickListener(this);
        dayGroup = (RadioGroup) getActivity().findViewById(R.id.dayGroup);
        dayGroup.setOnCheckedChangeListener(this);
        nightGroup = (RadioGroup) getActivity().findViewById(R.id.nightGroup);
        nightGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dateView:
                Picker.datePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        mYear = selectedYear;
                        mMonth = selectedMonth;
                        mDay = selectedDay;
                        dateView.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                        dateView.setTextColor(Color.BLACK);
                    }
                }, mYear, mMonth, mDay);
                break;
            case R.id.dayStartView:
                Picker.timePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        mDayStartHour = selectedHour;
                        mDayStartMinute = selectedMinute;
                        dayStartView.setText(mDayStartHour+":"+mDayStartMinute);
                        dayStartView.setTextColor(Color.BLACK);
                    }
                }, mDayStartHour, mDayStartMinute);
                break;
            case R.id.dayEndView:
                Picker.timePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        mDayEndHour = selectedHour;
                        mDayEndMinute = selectedMinute;
                        dayEndView.setText(mDayEndHour + ":" + mDayEndMinute);
                        dayEndView.setTextColor(Color.BLACK);
                    }
                }, mDayEndHour, mDayEndMinute);
                break;
            case R.id.nightStartView:
                Picker.timePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        mNightStartHour = selectedHour;
                        mNightStartMinute = selectedMinute;
                        nightStartView.setText(mNightStartHour + ":" + mNightStartMinute);
                        nightStartView.setTextColor(Color.BLACK);
                    }
                }, mNightStartHour, mNightStartMinute);
                break;
            case R.id.nightEndView:
                Picker.timePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        mNightEndHour = selectedHour;
                        mNightEndMinute = selectedMinute;
                        nightEndView.setText(mNightEndHour + ":" + mNightEndMinute);
                        nightEndView.setTextColor(Color.BLACK);
                    }
                }, mNightEndHour, mNightEndMinute);
                break;
            case R.id.dayLengthView:
                final Picker dayLengthPicker = new Picker();
                dayLengthPicker.NumberPickerDialog(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDayHourLength = dayLengthPicker.hourPicker.getValue();
                        mDayMinuteLength = dayLengthPicker.minutePicker.getValue();
                        dayLengthView.setText(mDayHourLength+" "+getString(R.string.hours)+" "+mDayMinuteLength+" "+getString(R.string.minutes));
                        dayLengthView.setTextColor(Color.BLACK);
                        dayLengthPicker.numberPickerDialog.dismiss();
                    }
                }, R.string.select_day_length, mDayHourLength, mDayMinuteLength);
                break;
            case R.id.nightLengthView:
                final Picker nightLengthPicker = new Picker();
                nightLengthPicker.NumberPickerDialog(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNightHourLength = nightLengthPicker.hourPicker.getValue();
                        mNightMinuteLength = nightLengthPicker.minutePicker.getValue();
                        nightLengthView.setText(mNightHourLength+" "+getString(R.string.hours)+" "+mNightMinuteLength+" "+getString(R.string.minutes));
                        nightLengthView.setTextColor(Color.BLACK);
                        nightLengthPicker.numberPickerDialog.dismiss();
                    }
                }, R.string.select_night_length, mNightHourLength, mNightMinuteLength);
                break;
            case R.id.dayCyclesView:
                final Picker dayCyclesPicker = new Picker();
                dayCyclesPicker.CyclesPickerDialog(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDayCycles = dayCyclesPicker.cyclesPicker.getValue();
                        dayCyclesView.setText(getString(R.string.cycles) + " " + mDayCycles);
                        dayCyclesView.setTextColor(Color.BLACK);
                        dayCyclesPicker.numberPickerDialog.dismiss();
                    }
                }, mDayCycles);
                break;
            case R.id.nightCyclesView:
                final Picker nightCyclesPicker = new Picker();
                nightCyclesPicker.CyclesPickerDialog(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNightCycles = nightCyclesPicker.cyclesPicker.getValue();
                        nightCyclesView.setText(getString(R.string.cycles)+" "+mNightCycles);
                        nightCyclesView.setTextColor(Color.BLACK);
                        nightCyclesPicker.numberPickerDialog.dismiss();
                    }
                }, mNightCycles);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.dayEqually:
                dayCyclesView.setVisibility(View.VISIBLE);
                dayLengthView.setVisibility(View.INVISIBLE);
                mIsDayEqually = true;
                break;
            case R.id.dayCustom:
                dayCyclesView.setVisibility(View.INVISIBLE);
                dayLengthView.setVisibility(View.VISIBLE);
                mIsDayEqually = false;
                break;
            case R.id.nightEqually:
                nightCyclesView.setVisibility(View.VISIBLE);
                nightLengthView.setVisibility(View.INVISIBLE);
                mIsNightEqually = true;
                break;
            case R.id.nightCustom:
                nightCyclesView.setVisibility(View.INVISIBLE);
                nightLengthView.setVisibility(View.VISIBLE);
                mIsNightEqually = false;
                break;
        }
    }

    public void CalculateList(Context context){
        //---------------------Day:--------------------------------
        DateTime dayStartTime = new DateTime(2015, 1, 1, mDayStartHour, mDayStartMinute);
        DateTime dayEndTime = new DateTime(2015, 1, 1, mDayEndHour, mDayEndMinute);
        Duration dayLength = new Duration(dayStartTime, dayEndTime);
        ArrayList<String> tempDayList = new ArrayList<>(DayListFragment.dayList);
        long mishmeretDay;
        if (mIsDayEqually){
            mishmeretDay = dayLength.getMillis() / tempDayList.size();
        }else{
            mishmeretDay = (DateTimeConstants.MILLIS_PER_HOUR*mDayHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*mDayMinuteLength);
        }
        long dividedDay = dayLength.getMillis() / mishmeretDay;
        long leftOfDay = dayLength.getMillis() % mishmeretDay;
        List<Mishmeret> mishmarotDay = new ArrayList<>();

        for (int i = 1; i <= dividedDay; i++){
            mishmarotDay.add(new Mishmeret(
                            dayStartTime.plus(mishmeretDay * i - mishmeretDay),
                            dayStartTime.plus(mishmeretDay * i)
                    )
            );
        }
        if (leftOfDay >= DateTimeConstants.MILLIS_PER_HOUR){
            int left = mishmarotDay.size() - tempDayList.size();
            int d = tempDayList.size() + left;
            mishmarotDay.add(new Mishmeret(
                    dayStartTime.plus(mishmeretDay * d),
                    dayStartTime.plus(mishmeretDay * d + leftOfDay)
            ));
        }
        //---------------------Night:--------------------------------
        DateTime nightStartTime = new DateTime(2015, 1, 1, mNightStartHour, mNightStartMinute);
        DateTime nightEndTime = new DateTime(2015, 1, 1, mNightEndHour, mNightEndMinute);
        Duration nightLength = new Duration(nightStartTime, nightEndTime);
        ArrayList<String> tempNightList = new ArrayList<>(NightListFragment.nightList);
        long mishmeretNight;
        if (mIsNightEqually){
            mishmeretNight = nightLength.getMillis() / tempNightList.size();
        }else{
            mishmeretNight = (DateTimeConstants.MILLIS_PER_HOUR*mNightHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*mNightMinuteLength);
        }
        long dividedNight = nightLength.getMillis() / mishmeretNight;
        long leftOfNight = nightLength.getMillis() % mishmeretNight;
        List<Mishmeret> mishmarotNight = new ArrayList<>();

        for (int i = 1; i <= dividedNight; i++){
            mishmarotNight.add(new Mishmeret(
                            nightStartTime.plus(mishmeretNight * i - mishmeretNight),
                            nightStartTime.plus(mishmeretNight * i)
                    )
            );
        }
        if (leftOfNight >= DateTimeConstants.MILLIS_PER_HOUR){
            int left = mishmarotNight.size() - tempNightList.size();
            int d = tempNightList.size() + left;
            mishmarotNight.add(new Mishmeret(
                    nightStartTime.plus(mishmeretNight * d),
                    nightStartTime.plus(mishmeretNight * d + leftOfNight)
            ));
        }
        //---------------------Completed List:--------------------------------
        final StringBuilder sb = new StringBuilder();
        sb.append(mDay+"/"+mMonth+"/"+mYear+ "\n \n");
        sb.append(R.string.day).append("\n");
        for(int i = 0; i < mishmarotDay.size(); i++){
            sb.append(mishmarotDay.get(i).startTime+" - "+mishmarotDay.get(i).endTime+" "+tempDayList.get(i)).append("\n");
        }
        sb.append(R.string.night).append("\n");
        for(int i = 0; i < mishmarotNight.size(); i++){
            sb.append(mishmarotNight.get(i).startTime+" - "+mishmarotNight.get(i).endTime+" "+tempNightList.get(i)).append("\n");
        }
        AlertDialog.Builder completedList = new AlertDialog.Builder(context);
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.append(sb);
        completedList.setView(input);

        completedList.setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent ();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                sb.replace(0, sb.length(), input.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, (CharSequence)sb);
                startActivity(Intent.createChooser(intent, getString(R.string.share_via))
                );
            }
        });
        completedList.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        completedList.show();



        /*for (int i = 1; i < tempDayList.size(); i++) {
            Mishmeret mishmeret = new Mishmeret();
            mishmeret.StartTime = dayStartTime.plus((dividedDay*i)-dividedDay);
            mishmeret.EndTime = dayStartTime.plus(dividedDay*i);
            dayTimeList.add(mishmeret);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tempDayList.size(); i++) {
            sb.append(dayTimeList.get(i).StartTime).append(" - ").append(dayTimeList.get(i).EndTime).append(" ").append(tempDayList.get(i)).append("/n");
        }

        AlertDialog.Builder completedList = new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        input.setFocusableInTouchMode(true);
        completedList.setView(input);
        completedList.setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        completedList.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        completedList.show();*/
    }
}
