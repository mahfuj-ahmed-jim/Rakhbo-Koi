package com.premiernoobs.rakhbokoi.Fragment.User;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.premiernoobs.rakhbokoi.Class.Class.Parking;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseDatabaseClass;
import com.premiernoobs.rakhbokoi.R;

public class ParkingRegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // editText
    private TextInputEditText addressEditText, latitudeEditText, longitudeEditText;

    // button
    private Button registerButton;

    public ParkingRegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParkingRegisterFragment newInstance(String param1, String param2) {
        ParkingRegisterFragment fragment = new ParkingRegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_parking_register, container, false);

        // init
        init(view);

        // editText on focus change
        addressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(addressEditText, isFocus);
            }
        });

        latitudeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(latitudeEditText, isFocus);
            }
        });

        longitudeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(longitudeEditText, isFocus);
            }
        });
        // editText on focus change

        // on click listeners
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getParking());
                String key = firebaseDatabase.push().getKey();
                firebaseDatabase.child(key).setValue(new Parking(addressEditText.getText().toString().trim(),
                        Double.parseDouble(longitudeEditText.getText().toString()),
                        Double.parseDouble(latitudeEditText.getText().toString())));

                Toast.makeText(getContext(), "You have registered successfully", Toast.LENGTH_LONG).show();

                getActivity().onBackPressed();

            }
        });
        // on click listeners

        return view;
    }

    // view change
    private void changeEditTextString(View view, boolean hasFocus){

        if(hasFocus){
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string));
        }else{
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string2));
        }

    }
    // view change

    // initialize
    private void init(View view) {
        initializeView(view);
    }

    private void initializeView(View view) {

        // editText
        addressEditText = view.findViewById(R.id.editTextId_email);
        latitudeEditText = view.findViewById(R.id.editTextId_password);
        longitudeEditText = view.findViewById(R.id.longtitude);

        // button
        registerButton = view.findViewById(R.id.buttonId_logIn);

    }
    // initialize

}