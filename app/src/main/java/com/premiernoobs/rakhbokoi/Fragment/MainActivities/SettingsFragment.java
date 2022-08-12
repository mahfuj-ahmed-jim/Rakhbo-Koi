package com.premiernoobs.rakhbokoi.Fragment.MainActivities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.premiernoobs.rakhbokoi.R;

public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // textView
    private TextView nameTextView, numberTextView;

    public SettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // initialize
        init(view);

        return view;
    }

    // init
    private void getPreviousValues(){

        Bundle bundle = this.getArguments();

        if(bundle != null) {

            nameTextView.setText(bundle.getString("NAME"));
            numberTextView.setText(bundle.getString("NUMBER"));

        }else{

        }

    }

    private void init(View view) {
        initializeView(view);
        getPreviousValues();
    }

    private void initializeView(View view) {

        // textView
        nameTextView = view.findViewById(R.id.textView_name);
        numberTextView = view.findViewById(R.id.textView_phoneNumber);

    }
    // init

}