package com.almog.admatay.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.almog.admatay.Picker;
import com.almog.admatay.R;

import java.util.ArrayList;
import java.util.Collections;

public class NightListFragment extends ListFragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static ArrayList<String> nightList = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.night_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nightList);
        //adapter = new CustomAdapter(getActivity(), dayList);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
        getListView().setAdapter(adapter);
        Button addNight = (Button) getActivity().findViewById(R.id.addNight);
        addNight.setOnClickListener(this);
        Button removeNight = (Button) getActivity().findViewById(R.id.removeNight);
        removeNight.setOnClickListener(this);
        Button shuffleNight = (Button) getActivity().findViewById(R.id.shuffleNight);
        shuffleNight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.addNight:
                final EditText input = new EditText(getActivity());
                Picker nightNamePicker = new Picker();
                nightNamePicker.NamePickerDialog(getActivity(), input, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
                        if (name.length() == 0){
                            Toast.makeText(getActivity(), R.string.empty_name, Toast.LENGTH_SHORT).show();
                        }else{
                            nightList.add(name);
                            /*mDayListChecked = new boolean[dayList.size()];
                            for (int i = 0; i < mDayListChecked.length; i++) {
                                mDayListChecked[i] = true;
                            }*/
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
            case R.id.removeNight:
                if (getListView().isSelected()){
                    int selectedItem = getListView().getCheckedItemPosition();
                    nightList.remove(selectedItem);
                    getListView().setSelector(R.color.transparent);
                    getListView().setSelected(false);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(), R.string.no_selection, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shuffleNight:
                Collections.shuffle(nightList);
                Collections.shuffle(nightList);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getListView().setItemChecked(position, true);
        getListView().setSelected(true);
        getListView().setSelector(R.color.colorPrimary100);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final EditText input = new EditText(getActivity());
        Picker changeNamePicker = new Picker();
        changeNamePicker.NamePickerDialog(getActivity(), input, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                if (name.length() == 0){
                    Toast.makeText(getActivity(), R.string.empty_name, Toast.LENGTH_SHORT).show();
                }else{
                    nightList.set(position, name);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return false;
    }
}