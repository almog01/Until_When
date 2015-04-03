package com.almog.admatay;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class Picker {

    public Dialog numberPickerDialog;
    public NumberPicker hourPicker, minutePicker, cyclesPicker;

    public static DatePickerDialog datePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener, int year, int month, int day){
        DatePickerDialog datePicker = new DatePickerDialog(context, onDateSetListener, year, month, day);
        datePicker.setTitle(R.string.select_date);
        datePicker.show();

        return datePicker;
    }

    public static TimePickerDialog timePickerDialog(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener, int hour, int minute){
        TimePickerDialog timePicker = new TimePickerDialog(context, onTimeSetListener, hour, minute, true);
        timePicker.setTitle(R.string.select_time);
        timePicker.show();

        return timePicker;
    }

    public void NumberPickerDialog (Context context, View.OnClickListener onOkClickListener, int title, int hour, int minute){
        numberPickerDialog = new Dialog(context);
        numberPickerDialog.setContentView(R.layout.number_picker_dialog);
        numberPickerDialog.setTitle(title);

        hourPicker = (NumberPicker) numberPickerDialog.findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(1);
        hourPicker.setWrapSelectorWheel(false);
        hourPicker.setValue(hour);

        minutePicker = (NumberPicker) numberPickerDialog.findViewById(R.id.minutePicker);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        minutePicker.setWrapSelectorWheel(false);
        minutePicker.setValue(minute);

        Button cancelBtn = (Button) numberPickerDialog.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPickerDialog.dismiss();
            }
        });

        Button okBtn = (Button) numberPickerDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(onOkClickListener);

        numberPickerDialog.show();
    }

    public void CyclesPickerDialog (Context context, View.OnClickListener onOkClickListener, int cycles){
        numberPickerDialog = new Dialog(context);
        numberPickerDialog.setContentView(R.layout.cycles_picker_dialog);
        numberPickerDialog.setTitle(R.string.select_cycles);

        cyclesPicker = (NumberPicker) numberPickerDialog.findViewById(R.id.cyclesPicker);
        cyclesPicker.setMaxValue(99);
        cyclesPicker.setMinValue(1);
        cyclesPicker.setWrapSelectorWheel(false);
        cyclesPicker.setValue(cycles);

        Button cancelBtn = (Button) numberPickerDialog.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPickerDialog.dismiss();
            }
        });

        Button okBtn = (Button) numberPickerDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(onOkClickListener);

        numberPickerDialog.show();
    }

    public void NamePickerDialog(final Context context, EditText input, DialogInterface.OnClickListener onOkClickListener){
        AlertDialog.Builder nameDialog = new AlertDialog.Builder(context);
        nameDialog.setTitle(R.string.type_name);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        input.setFocusableInTouchMode(true);
        nameDialog.setView(input);
        nameDialog.setPositiveButton(R.string.ok, onOkClickListener);
        nameDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        nameDialog.show();
    }


}