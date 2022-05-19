package com.premiernoobs.rakhbokoi.Fragment.User;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.premiernoobs.rakhbokoi.R;

public class LogInFragment extends Fragment {

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
    private TextInputEditText emailEditText, passwordEditText;

    // button
    private Button logInButton, registerButton, forgetPasswordButton;

    // fragments
    private EmailFragment emailFragment = new EmailFragment();

    public LogInFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
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
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        // initialize
        init(view);

        // editText on text change
        emailEditText.addTextChangedListener(new TextWatcher() {
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
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(emailEditText, isFocus);
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(passwordEditText, isFocus);
            }
        });
        // editText on focus change

        // button on click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailPage("YES");
            }
        });

        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailPage("NO");
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeButton(true)){

                }
            }
        });
        // button on click listener

        return view;
    }

    // next pages
    private void emailPage(String register) {

        // set value to set next page for register
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL", emailEditText.getText().toString().trim());
        bundle.putString("REGISTER", register);
        emailFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_id, emailFragment)
                .addToBackStack(null)
                .commit();

    }
    // next pages

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

        if(emailEditText.getText().toString().trim().isEmpty() || passwordEditText.getText().toString().trim().isEmpty()){
            logInButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button2));
            isChanged = false;

            if(isClicked){
                setWarning();
            }

        }else{
            logInButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button));
            isChanged = true;
        }

        return isChanged;

    }

    private void setWarning() {

        if(emailEditText.getText().toString().trim().isEmpty() && passwordEditText.getText().toString().trim().isEmpty()){

            warningTextView.setText("*WARNING : Enter the text fields");
            emailEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),
                    R.drawable.edittext_string_red));
            passwordEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),
                    R.drawable.edittext_string_red));

        }else if(emailEditText.getText().toString().trim().isEmpty()){

            warningTextView.setText("*WARNING : Enter Your Email Address");
            emailEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),
                    R.drawable.edittext_string_red));

        }else if(passwordEditText.getText().toString().trim().isEmpty()){

            warningTextView.setText("*WARNING : Enter Your Password");
            passwordEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),
                    R.drawable.edittext_string_red));

        }

    }
    // view change

    // initialize
    private void init(View view) {
        initializeViews(view); // views
    }

    private void initializeViews(View view) {

        // textView
        warningTextView = view.findViewById(R.id.textView_warning);

        // textInputEditText
        emailEditText = view.findViewById(R.id.editTextId_email);
        passwordEditText = view.findViewById(R.id.editTextId_password);

        // button
        logInButton = view.findViewById(R.id.buttonId_logIn);
        registerButton = view.findViewById(R.id.buttonId_register);
        forgetPasswordButton = view.findViewById(R.id.buttonId_forgetPassword);

    }
    // initialize

}