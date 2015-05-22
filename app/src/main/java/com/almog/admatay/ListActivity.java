package com.almog.admatay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.almog.admatay.fragments.DataFragment;
import com.almog.admatay.fragments.DayListFragment;
import com.almog.admatay.fragments.NightListFragment;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends ActionBarActivity {

    Context context = this;
    Button continueBtn;
    public static EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DataFragment df = new DataFragment();

        final ArrayList<String> tempDayList = new ArrayList<>(DayListFragment.dayList);
        final ArrayList<CustomList> dayList = new ArrayList<>();
        final List<Shift> shiftsDay = new ArrayList<>();

        final ArrayList<String> tempNightList = new ArrayList<>(NightListFragment.nightList);
        final ArrayList<CustomList> nightList = new ArrayList<>();
        final List<Shift> shiftsNight = new ArrayList<>();

        if (!tempDayList.isEmpty() && tempNightList.isEmpty()){
            setContentView(R.layout.only_day_list);

            if (tempDayList.size() != 0){
                int dayRepair = 1;
                if (df.mDayEndHour < df.mDayStartHour){
                    dayRepair = 2;
                }else if (df.mDayEndHour == df.mDayStartHour && df.mDayEndMinute <= df.mDayStartMinute){
                    dayRepair = 2;
                }
                DateTime dayStartTime = new DateTime(2015, 1, 1, df.mDayStartHour, df.mDayStartMinute);
                DateTime dayEndTime = new DateTime(2015, 1, dayRepair, df.mDayEndHour, df.mDayEndMinute);
                Duration dayLength = new Duration(dayStartTime, dayEndTime);
                int shiftDay;
                if (df.mIsDayEqually){
                    shiftDay = (int) ((dayLength.getMillis() / tempDayList.size()) / df.mDayCycles);
                }else{
                    shiftDay = (DateTimeConstants.MILLIS_PER_HOUR*df.mDayHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*df.mDayMinuteLength);
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

            int day = 0;
            for (Shift shift : shiftsDay){
                int startHour = shift.startTime.getHourOfDay();
                int startMinute = shift.startTime.getMinuteOfHour();
                int endHour = shift.endTime.getHourOfDay();
                int endMinute = shift.endTime.getMinuteOfHour();

                //dayList.add(new CustomList(String.format("%02d:%02d",startHour, startMinute), String.format("%02d:%02d", endHour, endMinute), tempDayList.get(day), length));
                dayList.add(new CustomList(startHour, startMinute, endHour, endMinute, tempDayList.get(day), shift.startTime, shift.endTime));
                day++;
                if (day >= tempDayList.size()){
                    day = 0;
                }
            }

            ListView dayLV = (ListView) findViewById(R.id.dayLV);
            CustomAdapter dayAdapter = new CustomAdapter(this, dayList);
            dayLV.setAdapter(dayAdapter);
        }
        else if (tempDayList.isEmpty() && !tempNightList.isEmpty()){
            setContentView(R.layout.only_night_list);

            if (tempNightList.size() != 0){
                int nightRepair = 1;
                if (df.mNightEndHour < df.mNightStartHour){
                    nightRepair = 2;
                }else if (df.mNightEndHour == df.mNightStartHour && df.mNightEndMinute <= df.mNightStartMinute){
                    nightRepair = 2;
                }
                DateTime nightStartTime = new DateTime(2015, 1, 1, df.mNightStartHour, df.mNightStartMinute);
                DateTime nightEndTime = new DateTime(2015, 1, nightRepair, df.mNightEndHour, df.mNightEndMinute);
                Duration nightLength = new Duration(nightStartTime, nightEndTime);
                int shiftNight;
                if (df.mIsNightEqually){
                    shiftNight = (int) ((nightLength.getMillis() / tempNightList.size()) / df.mNightCycles);
                }else{
                    shiftNight = (DateTimeConstants.MILLIS_PER_HOUR*df.mNightHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*df.mNightMinuteLength);
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

            int night = 0;
            for (Shift shift : shiftsNight){
                int startHour = shift.startTime.getHourOfDay();
                int startMinute = shift.startTime.getMinuteOfHour();
                int endHour = shift.endTime.getHourOfDay();
                int endMinute = shift.endTime.getMinuteOfHour();

                //nightList.add(new CustomList(String.format("%02d:%02d",startHour, startMinute), String.format("%02d:%02d",endHour, endMinute), tempNightList.get(night), length));
                nightList.add(new CustomList(startHour, startMinute, endHour, endMinute, tempNightList.get(night), shift.startTime, shift.endTime));
                night++;
                if (night >= tempNightList.size()){
                    night = 0;
                }
            }

            ListView nightLV = (ListView) findViewById(R.id.nightLV);
            CustomAdapter nightAdapter = new CustomAdapter(this, nightList);
            nightLV.setAdapter(nightAdapter);
        }
        else{
            setContentView(R.layout.two_lists);
            //---------------------Day:--------------------------------

            if (tempDayList.size() != 0){
                int dayRepair = 1;
                if (df.mDayEndHour < df.mDayStartHour){
                    dayRepair = 2;
                }else if (df.mDayEndHour == df.mDayStartHour && df.mDayEndMinute <= df.mDayStartMinute){
                    dayRepair = 2;
                }
                DateTime dayStartTime = new DateTime(2015, 1, 1, df.mDayStartHour, df.mDayStartMinute);
                DateTime dayEndTime = new DateTime(2015, 1, dayRepair, df.mDayEndHour, df.mDayEndMinute);
                Duration dayLength = new Duration(dayStartTime, dayEndTime);
                int shiftDay;
                if (df.mIsDayEqually){
                    shiftDay = (int) ((dayLength.getMillis() / tempDayList.size()) / df.mDayCycles);
                }else{
                    shiftDay = (DateTimeConstants.MILLIS_PER_HOUR*df.mDayHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*df.mDayMinuteLength);
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

            int day = 0;
            for (Shift shift : shiftsDay){
                int startHour = shift.startTime.getHourOfDay();
                int startMinute = shift.startTime.getMinuteOfHour();
                int endHour = shift.endTime.getHourOfDay();
                int endMinute = shift.endTime.getMinuteOfHour();

                //dayList.add(new CustomList(String.format("%02d:%02d",startHour, startMinute), String.format("%02d:%02d", endHour, endMinute), tempDayList.get(day), length));
                dayList.add(new CustomList(startHour, startMinute, endHour, endMinute, tempDayList.get(day), shift.startTime, shift.endTime));
                day++;
                if (day >= tempDayList.size()){
                    day = 0;
                }
            }

            ListView dayLV = (ListView) findViewById(R.id.dayLV);
            CustomAdapter dayAdapter = new CustomAdapter(this, dayList);
            dayLV.setAdapter(dayAdapter);
            //---------------------Night:--------------------------------

            if (tempNightList.size() != 0){
                int nightRepair = 1;
                if (df.mNightEndHour < df.mNightStartHour){
                    nightRepair = 2;
                }else if (df.mNightEndHour == df.mNightStartHour && df.mNightEndMinute <= df.mNightStartMinute){
                    nightRepair = 2;
                }
                DateTime nightStartTime = new DateTime(2015, 1, 1, df.mNightStartHour, df.mNightStartMinute);
                DateTime nightEndTime = new DateTime(2015, 1, nightRepair, df.mNightEndHour, df.mNightEndMinute);
                Duration nightLength = new Duration(nightStartTime, nightEndTime);
                int shiftNight;
                if (df.mIsNightEqually){
                    shiftNight = (int) ((nightLength.getMillis() / tempNightList.size()) / df.mNightCycles);
                }else{
                    shiftNight = (DateTimeConstants.MILLIS_PER_HOUR*df.mNightHourLength) + (DateTimeConstants.MILLIS_PER_MINUTE*df.mNightMinuteLength);
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

            int night = 0;
            for (Shift shift : shiftsNight){
                int startHour = shift.startTime.getHourOfDay();
                int startMinute = shift.startTime.getMinuteOfHour();
                int endHour = shift.endTime.getHourOfDay();
                int endMinute = shift.endTime.getMinuteOfHour();

                //nightList.add(new CustomList(String.format("%02d:%02d",startHour, startMinute), String.format("%02d:%02d",endHour, endMinute), tempNightList.get(night), length));
                nightList.add(new CustomList(startHour, startMinute, endHour, endMinute, tempNightList.get(night), shift.startTime, shift.endTime));
                night++;
                if (night >= tempNightList.size()){
                    night = 0;
                }
            }

            ListView nightLV = (ListView) findViewById(R.id.nightLV);
            CustomAdapter nightAdapter = new CustomAdapter(this, nightList);
            nightLV.setAdapter(nightAdapter);
        }

        continueBtn = (Button) findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---------------------Completed List:--------------------------------
                final StringBuilder sb = new StringBuilder();
                sb.append(df.mDay + "/" + (df.mMonth + 1) + "/" + (df.mYear  % 100));
                if (tempDayList.size() != 0){
                    sb.append("\n");
                    int day = 0;
                    /*for (Shift shift : shiftsDay){
                        for(int i = 0; i < shiftsDay.size(); i++){
                            sb.append("\n"+ String.format("%02d", shiftsDay.get(i).startTime.getHourOfDay()) + ":" + String.format("%02d", shiftsDay.get(i).startTime.getMinuteOfHour()) + " - " + String.format("%02d", shiftsDay.get(i).endTime.getHourOfDay()) + ":" + String.format("%02d", shiftsDay.get(i).endTime.getMinuteOfHour()) + " " + tempDayList.get(day));
                            day++;
                            if (day >= tempDayList.size()){
                                day = 0;
                            }
                        }
                    }*/
                    for (CustomList shift : dayList){
                        sb.append("\n"+ String.format("%02d", shift.getStartHour()) + ":" + String.format("%02d", shift.getStartMinute()) + " - " + String.format("%02d", shift.getEndHour()) + ":" + String.format("%02d", shift.getEndMinute()) + " " + tempDayList.get(day));
                        day++;
                        if (day >= tempDayList.size()){
                            day = 0;
                        }
                    }
                }

                if (tempNightList.size() != 0){
                    sb.append("\n");
                    int night = 0;
                    /*for (Shift shift : shiftsNight){
                        for(int i = 0; i < shiftsNight.size(); i++){
                            sb.append("\n"+ String.format("%02d", shiftsNight.get(i).startTime.getHourOfDay()) + ":" + String.format("%02d", shiftsNight.get(i).startTime.getMinuteOfHour()) + " - " + String.format("%02d", shiftsNight.get(i).endTime.getHourOfDay()) + ":" + String.format("%02d", shiftsNight.get(i).endTime.getMinuteOfHour()) + " " + tempNightList.get(night));
                            night++;
                            if (night >= tempNightList.size()){
                                night = 0;
                            }
                        }
                    }*/
                    for (CustomList shift : nightList){
                        sb.append("\n"+ String.format("%02d", shift.getStartHour()) + ":" + String.format("%02d", shift.getStartMinute()) + " - " + String.format("%02d", shift.getEndHour()) + ":" + String.format("%02d", shift.getEndMinute()) + " " + tempNightList.get(night));
                        night++;
                        if (night >= tempNightList.size()){
                            night = 0;
                        }
                    }
                }

                AlertDialog.Builder completedList = new AlertDialog.Builder(context);
                input = new EditText(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.append(sb);
                completedList.setView(input);

                completedList.setPositiveButton(context.getString(R.string.share), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        sb.replace(0, sb.length(), input.getText().toString());
                        intent.putExtra(Intent.EXTRA_TEXT, (CharSequence) sb);
                        startActivity(Intent.createChooser(intent, getString(R.string.share_via))
                        );
                    }
                });

                completedList.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                completedList.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
