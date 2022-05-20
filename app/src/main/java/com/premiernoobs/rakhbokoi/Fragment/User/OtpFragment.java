package com.premiernoobs.rakhbokoi.Fragment.User;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseDatabaseClass;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseOtp;
import com.premiernoobs.rakhbokoi.R;

public class OtpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // textView
    private TextView warningTextView;

    // pinView
    private PinView pinView;

    // button
    private Button nextButton;

    // layout as button
    private ConstraintLayout backButton;

    // firebase
    private DatabaseReference otpReference;

    // values
    private String email, register;
    private int otp;

    public OtpFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OtpFragment newInstance(String param1, String param2) {
        OtpFragment fragment = new OtpFragment();
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
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        // initialize
        init(view);

        // editText on text change
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeButton(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // editText on text change

        // editText on focus change
        pinView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if(isFocus){
                    pinView.setLineColor(ContextCompat.getColor(getContext(), R.color.white));
                }else{
                    pinView.setLineColor(ContextCompat.getColor(getContext(), R.color.black_custom2));
                }
            }
        });
        // editText on focus change

        // button on click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeButton(true)){
                    matchOtp();
                }
            }
        });
        // button on click listeners

        return view;
    }

    // functionality
    private void matchOtp(){

        otpReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    // set otp value
                    FirebaseOtp firebaseOtp = dataSnapshot.getValue(FirebaseOtp.class);
                    if(firebaseOtp.getEmail().equals(email)){
                        otp = firebaseOtp.getOtp();
                    }

                }

                // check the otp
                if(otp == Integer.parseInt(pinView.getText().toString())){

                    if(register.equals("YES")){
                        namePage();
                    }else{
                        passwordPage();
                    }

                }else{
                    warningTextView.setText("*Warning : Verification Code Not Matched");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    // functionality

    // next pages
    private void passwordPage() {

        // set value to set next page for register
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL", email);
        bundle.putString("REGISTER", register);

        PasswordFragment passwordFragment = new PasswordFragment();
        passwordFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_id, passwordFragment)
                .addToBackStack(null)
                .commit();

    }

    private void namePage() {

        // set value to set next page for register
        Bundle bundle = new Bundle();
        bundle.putString("REGISTER", register);

        NameFragment nameFragment = new NameFragment();
        nameFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_id, nameFragment)
                .commit();

    }
    // next pages

    // change view
    private boolean changeButton(boolean isClicked){

        boolean isChanged = false;

        if(pinView.getText().toString().trim().isEmpty()){
            nextButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button2));
            isChanged = false;

            if(isClicked){
                setWarning("Warning : Fill Up the Code");
            }

        }else{
            nextButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button));
            isChanged = true;
        }

        return isChanged;

    }

    private void setWarning(String warning) {
        warningTextView.setText(warning);
    }
    // change view

    // initialization
    private void getPreviousValues(){

        Bundle bundle = this.getArguments();

        if(bundle != null) {

            email = bundle.getString("EMAIL");
            register = bundle.getString("REGISTER");

        }

    }

    private void init(View view) {
        initializeViews(view);
        getPreviousValues();
    }

    private void initializeViews(View view) {

        // textView
        warningTextView = view.findViewById(R.id.textView_warning);

        // pinView
        pinView = view.findViewById(R.id.pinViewId);

        // button
        nextButton = view.findViewById(R.id.buttonId_next);
        backButton = view.findViewById(R.id.constraintLayoutId_backButton);

        // firebase
        otpReference = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getOtp());

    }
    // initialization

}