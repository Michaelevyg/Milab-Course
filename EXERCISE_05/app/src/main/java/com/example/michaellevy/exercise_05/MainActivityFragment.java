package com.example.michaellevy.exercise_05;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url = "https://www.google.co.il/search?q=";

        Button button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)getView().findViewById(R.id.etVal);
                String etVal = editText.getText().toString();

                if (etVal.equals("")) {
                    Toast toast = Toast.makeText(getContext(), "please enter a valid input", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                final TextView lable = (TextView)getView().findViewById(R.id.label);

                StringRequest req = new StringRequest(Request.Method.GET, url + etVal, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String firstGoogleHeader = returnFirstGoogleHeader(response);
                        lable.setText(firstGoogleHeader);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivityFragment", "Encountered error - " + error);
                    }
                });
                queue.add(req);
            }
        });

        return view;
    }

    private String returnFirstGoogleHeader (String response) {
        int tempIndex;

        tempIndex = response.indexOf("<body class");
        response = response.substring(tempIndex);
        tempIndex = response.indexOf("class=\"srg");
        response = response.substring(tempIndex);
        tempIndex = response.indexOf("<span dir=");
        response = response.substring(tempIndex);
        tempIndex = response.indexOf("</span>");
        response = response.substring(16, tempIndex);
        return response;
    }
}


