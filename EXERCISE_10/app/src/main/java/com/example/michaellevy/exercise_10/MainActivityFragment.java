package com.example.michaellevy.exercise_10;

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
        final String url = "https://milabex10.herokuapp.com/?stock=";

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
                        lable.setText(response);
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
}


