package com.premiernoobs.rakhbokoi.Fragment.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.premiernoobs.rakhbokoi.Class.Class.User;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseDatabaseClass;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseOtp;
import com.premiernoobs.rakhbokoi.Class.Static.EmailStatic;
import com.premiernoobs.rakhbokoi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class EmailFragment extends Fragment {

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
    private TextInputEditText emailEditText;

    // button
    private Button nextButton;

    // layout as button
    private ConstraintLayout backButton, logInButton;

    // layout
    private LinearLayout logInLayout;

    // firebase
    private DatabaseReference emailReference;

    // values
    private Boolean register = false;
    private EmailStatic emailStatic = new EmailStatic();

    // dialogs
    private ProgressBar progressBar;

    public EmailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EmailFragment newInstance(String param1, String param2) {
        EmailFragment fragment = new EmailFragment();
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
        View view = inflater.inflate(R.layout.fragment_email, container, false);

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
        // editText on text change

        // editText on focus change
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(emailEditText, isFocus);
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

        logInButton.setOnClickListener(new View.OnClickListener() {
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
                    new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            checkEmail();
                        }
                    }.start();
                }

            }
        });
        // button on click listeners

        return view;
    }

    // firebase
    private void checkEmail() {

        emailReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean isUnique = true;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    // set otp value
                    User user = dataSnapshot.getValue(User.class);
                    if(user.getEmail().equals(emailEditText.getText().toString().trim())){
                        setWarning("Email Address is Already Used");
                        isUnique = false;
                        setLoader(false);
                        break;
                    }

                }

                if(isUnique){
                    sendEmail();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    // firebase

    // functionality
    private void strictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void sendEmail(){

        //String someHtmlMessage = "Hello this is test message <b style='color:blue;'>bold color</b>";
        // set message
        Random random = new Random();
        int otp = 0;

        while(otp<=99999){
            otp = random.nextInt(999999);
        }

        String someHtmlMessage = emailStatic.getEmailOtpText()+"<b >"+otp+"</b><br><br>Thank You.";

        if(emailStatic.sendEmail(emailEditText.getText().toString().trim(), emailStatic.getEmailOtpSubject(), someHtmlMessage)){

            // firebase
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getOtp());
            String key = firebaseDatabase.push().getKey();
            firebaseDatabase.child(key).setValue(new FirebaseOtp(emailEditText.getText().toString().trim(), otp, getTime()));

            otpPage();
        }else{
            setLoader(false);
        }

    }

    private String getTime(){

        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        String[] parts = currentDateandTime.split("_");
        String tmp = parts[1];
        String[] parts2 = tmp.split("-");

        int hour = Integer.parseInt(parts2[0]);
        int min = Integer.parseInt(parts2[1]);

        String minute;

        if(min >= 10){
            minute = min+"";
        }else{
            minute = "0"+min;
        }

        if(hour >= 12){

            if(hour%12 >= 10){
                time = hour%12+":"+minute+"PM";
            }else{
                time = "0"+hour%12+":"+minute+"PM";
            }

        }else{

            if(hour > 10){
                time = hour+":"+minute+"AM";
            }else{
                time = "0"+hour+":"+minute+"AM";
            }

        }

        return time;

    }
    // functionality

    // next pages
    private void otpPage() {

        // set value to set next page for register
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL", emailEditText.getText().toString().trim());
        bundle.putString("REGISTER", register?"YES":"NO");

        OtpFragment otpFragment = new OtpFragment();
        otpFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_id, otpFragment)
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

        if(emailEditText.getText().toString().trim().isEmpty()){
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
        emailEditText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string_red));
    }
    // view change

    // initialize
    private void getPreviousValues(){

        Bundle bundle = this.getArguments();

        if(bundle != null) {

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
        strictMode();
    }

    private void initializeViews(View view) {

        // textView
        warningTextView = view.findViewById(R.id.textView_warning);
        messageTextView = view.findViewById(R.id.textViewId_message);

        // textInputEditText
        emailEditText = view.findViewById(R.id.editTextId_email);

        // button
        nextButton = view.findViewById(R.id.buttonId_next);
        backButton = view.findViewById(R.id.constraintLayoutId_backButton);
        logInButton = view.findViewById(R.id.constraintLayoutId_logIn);

        // layout
        logInLayout = view.findViewById(R.id.linearLayoutId_logIn);

        // dialogs
        progressBar = view.findViewById(R.id.spin_kit);

        // firebase
        emailReference = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getUser());

    }
    // initialize

}