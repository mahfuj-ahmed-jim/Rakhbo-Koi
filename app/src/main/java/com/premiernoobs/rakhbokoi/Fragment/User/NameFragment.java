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

public class NameFragment extends Fragment {

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
    private TextInputEditText nameEditText;

    // button
    private Button nextButton;

    // layout as button
    private ConstraintLayout backButton;

    // values
    private Boolean register = false;
    private User user;

    // dialogs
    private ProgressBar progressBar;

    public NameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NameFragment newInstance(String param1, String param2) {
        NameFragment fragment = new NameFragment();
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
        View view = inflater.inflate(R.layout.fragment_name, container, false);

        // initialize
        init(view);

        // editText on text change
        nameEditText.addTextChangedListener(new TextWatcher() {
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
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(nameEditText, isFocus);
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
                    carNumberPage();
                }

            }
        });
        // button on click listeners

        return view;
    }

    // next pages
    private void carNumberPage() {

        // user
        user.setName(nameEditText.getText().toString().trim());

        // set value to set next page for register
        Bundle bundle = new Bundle();
        bundle.putString("REGISTER", register?"YES":"NO");
        bundle.putParcelable("USER", user);

        CarNumberFragment carNumberFragment = new CarNumberFragment();
        carNumberFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_id, carNumberFragment)
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

        if(nameEditText.getText().toString().trim().isEmpty()){
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
        nameEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string_red));
    }
    // view change

    // initialize
    private void getPreviousValues(){

        Bundle bundle = this.getArguments();

        if(bundle != null) {

            user = bundle.getParcelable("USER");

            if(!bundle.getString("REGISTER").equals("YES")){
                messageTextView.setVisibility(View.GONE);
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
        nameEditText = view.findViewById(R.id.editTextId_email);

        // button
        nextButton = view.findViewById(R.id.buttonId_next);
        backButton = view.findViewById(R.id.constraintLayoutId_backButton);
        // dialogs
        progressBar = view.findViewById(R.id.spin_kit);

    }
    // initialize

}