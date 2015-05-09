package com.almog.admatay.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.almog.admatay.MainActivity;
import com.almog.admatay.Picker;
import com.almog.admatay.R;
import com.almog.admatay.Shift;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    public StringBuilder sb = new StringBuilder();

    private static EditText dateView, dayStartView, dayEndView, nightStartView, nightEndView, dayLengthView, nightLengthView, dayCyclesView, nightCyclesView;
    private static RadioGroup dayGroup, nightGroup;
    private static Calendar calendar = Calendar.getInstance();
    private static int mYear = calendar.get(Calendar.YEAR);
    private static int mMonth = calendar.get(Calendar.MONTH);
    private static int mDay = calendar.get(Calendar.DAY_OF_MONTH) +1;

    private static DateTime time = new DateTime();
    private static int mDayStartHour = time.getHourOfDay();
    private static int mDayStartMinute = time.getMinuteOfHour();
    private static int mDayEndHour = time.getHourOfDay();
    private static int mDayEndMinute = time.getMinuteOfHour();
    private static int mNightStartHour = time.getHourOfDay();
    private static int mNightStartMinute = time.getMinuteOfHour();
    private static int mNightEndHour = time.getHourOfDay();
    private static int mNightEndMinute = time.getMinuteOfHour();
    private static int mDayHourLength = 3;
    private static int mDayMinuteLength = 0;
    private static int mNightHourLength = 3;
    private static int mNightMinuteLength = 0;
    private static boolean mIsDayEqually = true;
    private static boolean mIsNightEqually = true;

    private static int mDayCycles = 1;
    private static int mNightCycles = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data, container, false);
        if (savedInstanceState != null){
            mYear = savedInstanceState.getInt("Year");
            mMonth = savedInstanceState.getInt("Month");
            mDay = savedInstanceState.getInt("Day");
            EditText dateView = (EditText) view.findViewById(R.id.dateView);
            dateView.setText(savedInstanceState.getString("DateText"));
            mDayStartHour = savedInstanceState.getInt("DayStartHour");
            mDayStartMinute = savedInstanceState.getInt("DayStartMinute");
        }else{

        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Year", mYear);
        outState.putInt("Month", mYear);
        outState.putInt("Day", mDay);
        outState.putCharSequence("DateText", dateView.getText());
        outState.putInt("DayStartHour", mDayStartHour);
        outState.putInt("DayStartMinute", mDayStartMinute);
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

        /*if (savedInstanceState != null){
            mYear = savedInstanceState.getInt("year");
            mMonth = savedInstanceState.getInt("month");
            mDay = savedInstanceState.getInt("day");
            dateTV.setText(savedInstanceState.getCharSequence("dateTextView"));
        }else{

        }*/
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
                        dateView.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                        //dateTextView.setTextColor(Color.BLACK);
                    }
                }, mYear, mMonth, mDay);
                break;
            case R.id.dayStartView:
                Picker.timePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        mDayStartHour = selectedHour;
                        mDayStartMinute = selectedMinute;
                        dayStartView.setText(mDayStartHour + ":" + mDayStartMinute);
                        //dayStartView.setTextColor(Color.BLACK);
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
                        //dayEndView.setTextColor(Color.BLACK);
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
                        //nightStartView.setTextColor(Color.BLACK);
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
                        //nightEndView.setTextColor(Color.BLACK);
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
                        dayLengthView.setText(mDayHourLength + " " + getString(R.string.hours) + " " + mDayMinuteLength + " " + getString(R.string.minutes));
                        //dayLengthView.setTextColor(Color.BLACK);
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
                        nightLengthView.setText(mNightHourLength + " " + getString(R.string.hours) + " " + mNightMinuteLength + " " + getString(R.string.minutes));
                        //nightLengthView.setTextColor(Color.BLACK);
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
                        dayCyclesView.setText(mDayCycles + " " + getString(R.string.cycles));
                        //dayCyclesView.setTextColor(Color.BLACK);
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
                        nightCyclesView.setText(mNightCycles + " " + getString(R.string.cycles));
                        //nightCyclesView.setTextColor(Color.BLACK);
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
                dayLengthView.setVisibility(View.GONE);
                mIsDayEqually = true;
                break;
            case R.id.dayCustom:
                dayCyclesView.setVisibility(View.GONE);
                dayLengthView.setVisibility(View.VISIBLE);
                mIsDayEqually = false;
                break;
            case R.id.nightEqually:
                nightCyclesView.setVisibility(View.VISIBLE);
                nightLengthView.setVisibility(View.GONE);
                mIsNightEqually = true;
                break;
            case R.id.nightCustom:
                nightCyclesView.setVisibility(View.GONE);
                nightLengthView.setVisibility(View.VISIBLE);
                mIsNightEqually = false;
                break;
        }
    }

    public void CalculateList(Context context, DialogInterface.OnClickListener onShareClick, DialogInterface.OnClickListener onCancelClick){
        //---------------------Day:--------------------------------
        ArrayList<String> tempDayList = new ArrayList<>(DayListFragment.dayList);
        List<Shift> shiftsDay = new ArrayList<>();

        if (tempDayList.size() != 0){
            int dayRepair = 1;
            if (mDayEndHour <= mDayStartHour){
                dayRepair = 2;
            }
            DateTime dayStartTime = new DateTime(2015, 1, 1, mDayStartHour, mDayStartMinute);
            DateTime dayEndTime = new DateTime(2015, 1, dayRepair, mDayEndHour, mDayEndMinute);
            Duration dayLength = new Duration(dayStartTime, dayEndTime);
            int shiftDay;
            if (mIsDayEqually){
                shiftDay = (int) ((dayLength.getMillis() / tempDayList.size()) / mDayCycles);
            }else{
                shiftDay = (DateTimeConstants.MILLIS_PER_HOUR*mDayHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*mDayMinuteLength);
            }
            int dividedDay = (int) (dayLength.getMillis() / shiftDay);
            int leftOfDay = (int) (dayLength.getMillis() % shiftDay);

            for (int i = 1; i <= dividedDay; i++){
                shiftsDay.add(new Shift(
                                dayStartTime.plus(shiftDay * i - shiftDay),
                                dayStartTime.plus(shiftDay * i)
                        )
                );
            }

            int leftDay = shiftsDay.size() - tempDayList.size();
            int f = tempDayList.size() + leftDay;
            if (leftOfDay >= DateTimeConstants.MILLIS_PER_HOUR){
                shiftsDay.add(new Shift(
                        dayStartTime.plus(shiftDay * f),
                        dayStartTime.plus(shiftDay * f + leftOfDay)
                ));
            }else if (leftOfDay != 0){
                shiftsDay.set(shiftsDay.size()-1, new Shift(
                        dayStartTime.plus(shiftDay * f - shiftDay),
                        dayStartTime.plus(shiftDay * f + leftOfDay)));
            }
        }
        //---------------------Night:--------------------------------
        ArrayList<String> tempNightList = new ArrayList<>(NightListFragment.nightList);
        List<Shift> shiftsNight = new ArrayList<>();

        if (tempNightList.size() != 0){
            int nightRepair = 1;
            if (mNightEndHour <= mNightStartHour){
                nightRepair = 2;
            }
            DateTime nightStartTime = new DateTime(2015, 1, 1, mNightStartHour, mNightStartMinute);
            DateTime nightEndTime = new DateTime(2015, 1, nightRepair, mNightEndHour, mNightEndMinute);
            Duration nightLength = new Duration(nightStartTime, nightEndTime);
            int shiftNight;
            if (mIsNightEqually){
                shiftNight = (int) ((nightLength.getMillis() / tempNightList.size()) / mNightCycles);
            }else{
                shiftNight = (DateTimeConstants.MILLIS_PER_HOUR*mNightHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*mNightMinuteLength);
            }
            int dividedNight = (int) (nightLength.getMillis() / shiftNight);
            int leftOfNight = (int) (nightLength.getMillis() % shiftNight);

            for (int i = 1; i <= dividedNight; i++){
                shiftsNight.add(new Shift(
                                nightStartTime.plus(shiftNight * i - shiftNight),
                                nightStartTime.plus(shiftNight * i)
                        )
                );
            }

            int leftNight = shiftsNight.size() - tempNightList.size();
            int d = tempNightList.size() + leftNight;
            if (leftOfNight >= DateTimeConstants.MILLIS_PER_HOUR){
                shiftsNight.add(new Shift(
                        nightStartTime.plus(shiftNight * d),
                        nightStartTime.plus(shiftNight * d + leftOfNight)
                ));
            }else if (leftOfNight != 0){
                shiftsNight.set(shiftsNight.size()-1,new Shift(
                        nightStartTime.plus(shiftNight * d - shiftNight),
                        nightStartTime.plus(shiftNight * d + leftOfNight)));
            }
        }
        //---------------------Completed List:--------------------------------
        sb.append(mDay + "/" + (mMonth + 1) + "/" + mYear);
        if (tempDayList.size() != 0){
            sb.append("\n");
            int day = 0;
            /*for (Shift shift : shiftsDay){
                if (shift.startTime.getMinuteOfHour() != 00 || shift.endTime.getMinuteOfHour() != 00){*/
                    for(int i = 0; i < shiftsDay.size(); i++){
                        sb.append("\n").append(String.format("%02d", shiftsDay.get(i).startTime.getHourOfDay()) + ":" + String.format("%02d", shiftsDay.get(i).startTime.getMinuteOfHour()) + " - " + String.format("%02d", shiftsDay.get(i).endTime.getHourOfDay()) + ":" + String.format("%02d", shiftsDay.get(i).endTime.getMinuteOfHour()) + " " + tempDayList.get(day));
                        day++;
                        if (day >= tempDayList.size()){
                            day = 0;
                        }
                    }
                    //break;
                //}
            //}
        }

        if (tempNightList.size() != 0){
            sb.append("\n");
            int night = 0;
            /*for (Shift shift : shiftsNight){
                if (shift.startTime.getMinuteOfHour() != 00 || shift.endTime.getMinuteOfHour() != 00){*/
                    for(int i = 0; i < shiftsNight.size(); i++){
                        sb.append("\n").append(String.format("%02d", shiftsNight.get(i).startTime.getHourOfDay()) + ":" + String.format("%02d", shiftsNight.get(i).startTime.getMinuteOfHour()) + " - " + String.format("%02d", shiftsNight.get(i).endTime.getHourOfDay()) + ":" + String.format("%02d", shiftsNight.get(i).endTime.getMinuteOfHour()) + " " + tempNightList.get(night));
                        night++;
                        if (night >= tempNightList.size()){
                            night = 0;
                        }
                    }
                    /*break;
                }else{
                    for(int i = 0; i < shiftsNight.size(); i++){
                        sb.append("\n").append(String.format("%02d", shiftsNight.get(i).startTime.getHourOfDay()) + " - " + String.format("%02d", shiftsNight.get(i).endTime.getHourOfDay()) + " " + tempNightList.get(night));
                        night++;
                        if (night >= tempNightList.size()){
                            night = 0;
                        }
                    }
                    break;
                }
            }*/
        }
        AlertDialog.Builder completedList = new AlertDialog.Builder(context);
        MainActivity.input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        MainActivity.input.setLayoutParams(lp);
        MainActivity.input.append(sb);
        completedList.setView(MainActivity.input);

        completedList.setPositiveButton(context.getString(R.string.share), onShareClick);

        completedList.setNegativeButton(context.getString(R.string.cancel), onCancelClick);

        completedList.show();
    }
}
