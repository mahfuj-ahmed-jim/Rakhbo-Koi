package com.premiernoobs.rakhbokoi.Fragment.User;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.textfield.TextInputEditText;
import com.premiernoobs.rakhbokoi.Class.Class.User;
import com.premiernoobs.rakhbokoi.Class.Static.EmailStatic;
import com.premiernoobs.rakhbokoi.R;

public class CarNumberFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // textView
    private TextView warningTextView, messageTextView;

    // textInputEditText
    private TextInputEditText carNumberEditText;

    // button
    private Button nextButton;

    // layout as button
    private ConstraintLayout backButton;

    // layout
    private LinearLayout logInLayout;
    private ConstraintLayout loaderLayout;

    // values
    private Boolean register = false;
    private User user;

    // dialogs
    private ProgressBar progressBar;

    public CarNumberFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CarNumberFragment newInstance(String param1, String param2) {
        CarNumberFragment fragment = new CarNumberFragment();
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
        View view = inflater.inflate(R.layout.fragment_car_number, container, false);

        // initialize
        init(view);

        // editText on text change
        carNumberEditText.addTextChangedListener(new TextWatcher() {
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
        carNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(carNumberEditText, isFocus);
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
                    setLoader(true);
                    passwordPage();
                }

            }
        });
        // button on click listeners

        return view;
    }

    // next pages
    private void passwordPage() {

        // user
        user.setCarNumber(carNumberEditText.getText().toString().trim());

        // set value to set next page for register
        Bundle bundle = new Bundle();
        bundle.putString("REGISTER", register?"YES":"NO");
        bundle.putParcelable("USER", user);

        PasswordFragment passwordFragment = new PasswordFragment();
        passwordFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_id, passwordFragment)
                .commit();

    }
    // next pages

    // view change
    private void setLoader(boolean load){

        if(load){
            nextButton.setVisibility(View.GONE);
            Sprite doubleBounce = new FadingCircle();
            progressBar.setIndeterminateDrawable(doubleBounce);
        }else{
            nextButton.setVisibility(View.VISIBLE);
        }

    }

    private void changeEditTextString(View view, boolean hasFocus){

        if(hasFocus){
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string));
        }else{
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string2));
        }

    }

    private boolean changeButton(boolean isClicked){

        boolean isChanged = false;

        if(carNumberEditText.getText().toString().trim().isEmpty()){
            nextButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button2));
            isChanged = false;
            if(isClicked){
                setWarning("Enter your email address");
            }
        }else{
            nextButton.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.button));
            isChanged = true;
        }

        return isChanged;

    }

    private void setWarning(String warning) {
        warningTextView.setText("*WARNING : "+warning);
        carNumberEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string_red));
    }
    // view change

    // initialize
    private void getPreviousValues(){

        Bundle bundle = this.getArguments();

        if(bundle != null) {

            // user
            user = bundle.getParcelable("USER");

            if(!bundle.getString("REGISTER").equals("YES")){
                messageTextView.setVisibility(View.GONE);
                logInLayout.setVisibility(View.GONE);
            }else{
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
        messageTextView = view.findViewById(R.id.textViewId_message);

        // textInputEditText
        carNumberEditText = view.findViewById(R.id.editTextId_email);

        // button
        nextButton = view.findViewById(R.id.buttonId_next);
        backButton = view.findViewById(R.id.constraintLayoutId_backButton);

        // layout
        logInLayout = view.findViewById(R.id.linearLayoutId_logIn);
        loaderLayout = view.findViewById(R.id.constraintLayoutId_loader);

        // dialogs
        progressBar = view.findViewById(R.id.spin_kit);

    }
    // initialize

}