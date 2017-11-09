package com.example.michaellevy.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        Button myButton = (Button) view.findViewById(R.id.button1);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View)v.getParent();
                EditText myEditText = (EditText) parent.findViewById(R.id.plain_text_input);
                String EditTextText = myEditText.getText().toString();
                Toast toast = Toast.makeText(v.getContext(), EditTextText , Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return view;
    }


}
