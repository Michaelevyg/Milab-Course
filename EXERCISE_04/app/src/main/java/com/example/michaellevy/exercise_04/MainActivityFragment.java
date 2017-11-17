package com.example.michaellevy.exercise_04;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    Button btnSet;
    EditText etVal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        btnSet = (Button)view.findViewById(R.id.btnSetAlarm);
        etVal = (EditText)view.findViewById(R.id.etVal);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int interval = 5;
                String textVal = etVal.getText().toString();

                if (isNumeric(textVal)) {
                    interval = Integer.parseInt(textVal);
                } else {
                    Toast.makeText(getActivity(), "invalid input! interval is set to 5 minutes",
                            Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent (v.getContext(), Alarm.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),0, intent,0);
                AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService((Context.ALARM_SERVICE));
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),interval * 60000, pendingIntent);
            }
        });

        return view;
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
