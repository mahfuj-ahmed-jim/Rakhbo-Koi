package com.premiernoobs.rakhbokoi.Fragment.User;

import android.content.Intent;
import android.os.Bundle;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.premiernoobs.rakhbokoi.Activity.MainActivity;
import com.premiernoobs.rakhbokoi.Class.Class.User;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseDatabaseClass;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseOtp;
import com.premiernoobs.rakhbokoi.R;
import com.premiernoobs.rakhbokoi.Room.LocalUser;
import com.premiernoobs.rakhbokoi.Room.MainDatabase;

public class PasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // textView
    private TextView warningTextView;

    // textInputEditText
    private TextInputEditText passwordEditText;

    // button
    private Button nextButton;

    // layout as button
    private ConstraintLayout backButton;

    // values
    private Boolean register = false;
    private User user;

    public PasswordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        // initialize
        init(view);

        // editText on text change
        passwordEditText.addTextChangedListener(new TextWatcher() {
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
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(passwordEditText, isFocus);
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

                    if(register){
                        registerUser();
                    }else{
                        updateUser();
                    }

                }
            }
        });
        // button on click listeners

        return view;
    }

    // firebase
    private void registerUser() {

        // user
        user.setPassword(passwordEditText.getText().toString().trim());

        // firebase
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getUser());
        String key = firebaseDatabase.push().getKey();
        firebaseDatabase.child(key).setValue(user);
        saveToRoomDatabase(key);

        Toast.makeText(getContext(), "You have registered successfully", Toast.LENGTH_LONG).show();

        homePage(); // next page

    }

    private void updateUser() {
    }
    // firebase

    // next page
    private void homePage() {

        // go to the page activity after splash screen
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("Fragment", String.valueOf(R.string.HOME_FRAGMENT));
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0); //intent soft animation
        getActivity().finish();

    }
    // next page

    // view change
    private void changeEditTextString(View view, boolean hasFocus){

        if(hasFocus){
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string));
        }else{
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string2));
        }

    }

    private boolean changeButton(boolean isClicked){

        boolean isChanged = false;

        if(passwordEditText.getText().toString().trim().isEmpty()){
            nextButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button2));
            isChanged = false;
            if(isClicked){
                setWarning("Enter your password");
            }
        }else{
            nextButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button));
            isChanged = true;
        }

        return isChanged;

    }

    private void setWarning(String warning) {
        warningTextView.setText("*WARNING : "+warning);
        passwordEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string_red));
    }
    // view change

    // room database
    private void saveToRoomDatabase(String userId){

        // ROOM DATABASE
        MainDatabase mainDatabase;
        mainDatabase = MainDatabase.getInstance(getContext());

        // save value
        LocalUser user = new LocalUser(userId);
        mainDatabase.userDao().insertUser(user);

    }
    // room database

    // initialize
    private void getPreviousValues(){

        Bundle bundle = this.getArguments();

        if(bundle != null) {

            // user
            user = bundle.getParcelable("USER");

            if(bundle.getString("REGISTER").equals("YES")){
                register = true;
            }

        }else{

        }

    }

    private void init(View view) {
        initializeViews(view);
        getPreviousValues();
    }

    private void initializeViews(View view) {

        // textView
        warningTextView = view.findViewById(R.id.textView_warning);

        // textInputEditText
        passwordEditText = view.findViewById(R.id.editTextId_password);

        // button
        nextButton = view.findViewById(R.id.buttonId_next);
        backButton = view.findViewById(R.id.constraintLayoutId_backButton);

    }
    // initialize

}