package com.almog.admatay;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.almog.admatay.fragments.DataFragment;
import com.almog.admatay.fragments.DayListFragment;
import com.almog.admatay.fragments.NightListFragment;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        DataFragment df = new DataFragment();

        //---------------------Day:--------------------------------
        ArrayList dayList = new ArrayList<CustomList>();
        ArrayList<String> tempDayList = new ArrayList<>(DayListFragment.dayList);
        List<Shift> shiftsDay = new ArrayList<>();

        if (tempDayList.size() != 0){
            int dayRepair = 1;
            if (df.mDayEndHour <= df.mDayStartHour){
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
            String length = getLenght(shift.startTime, shift.endTime);

            dayList.add(new CustomList(String.format("%02d:%02d",startHour, startMinute), String.format("%02d:%02d", endHour, endMinute), tempDayList.get(day), length));
            day++;
            if (day >= tempDayList.size()){
                day = 0;
            }
        }

        ListView dayLV = (ListView) findViewById(R.id.dayLV);
        CustomAdapter dayAdapter = new CustomAdapter(this, dayList);
        dayLV.setAdapter(dayAdapter);
        //---------------------Night:--------------------------------
        ArrayList nightList = new ArrayList<CustomList>();
        ArrayList<String> tempNightList = new ArrayList<>(NightListFragment.nightList);
        List<Shift> shiftsNight = new ArrayList<>();

        if (tempNightList.size() != 0){
            int nightRepair = 1;
            if (df.mNightEndHour <= df.mNightStartHour){
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
            String length = getLenght(shift.startTime, shift.endTime);

            nightList.add(new CustomList(String.format("%02d:%02d",startHour, startMinute), String.format("%02d:%02d",endHour, endMinute), tempNightList.get(night), length));
            night++;
            if (night >= tempNightList.size()){
                night = 0;
            }
        }

        ListView nightLV = (ListView) findViewById(R.id.nightLV);
        CustomAdapter nightAdapter = new CustomAdapter(this, nightList);
        nightLV.setAdapter(nightAdapter);
    }

    public String getLenght(DateTime start, DateTime end){
        Duration duration = new Duration(start, end);
        Period p = duration.toPeriod();
        PeriodFormatter pf = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().toFormatter();
        return pf.print(p);
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
