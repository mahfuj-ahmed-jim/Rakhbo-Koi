package com.premiernoobs.rakhbokoi.Fragment.MainActivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.premiernoobs.rakhbokoi.Activity.PageActivity;
import com.premiernoobs.rakhbokoi.R;
import com.premiernoobs.rakhbokoi.Room.MainDatabase;

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

    // layout as button
    private ConstraintLayout logOutButton;

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

        // con click listeners
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
        // con click listeners

        return view;
    }

    // next page
    private void welcomePage(){

        // go to the main activity after splash screen
        Intent intent = new Intent(getContext(), PageActivity.class);
        intent.putExtra("Fragment", String.valueOf(R.string.LOGIN_FRAGMENT));
        startActivity(intent);
        getActivity().finish();

    }
    // next page

    // room database
    private void logOut() {

        // ROOM DATABASE
        try{
            MainDatabase mainDatabase;
            mainDatabase = MainDatabase.getInstance(getContext());
            mainDatabase.userDao().deleteUser(mainDatabase.userDao().getUserList().get(0));

            welcomePage();
        }catch (Exception e){
            Log.d("Verify", e.getMessage());
        }

    }
    // room database

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

        // layout as button
        logOutButton = view.findViewById(R.id.constraintLayoutId_logOut);

    }
    // init

}