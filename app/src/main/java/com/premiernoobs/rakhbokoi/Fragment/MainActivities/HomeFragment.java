package com.premiernoobs.rakhbokoi.Fragment.MainActivities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.premiernoobs.rakhbokoi.Class.Class.User;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseDatabaseClass;
import com.premiernoobs.rakhbokoi.R;
import com.premiernoobs.rakhbokoi.Room.LocalUser;
import com.premiernoobs.rakhbokoi.Room.MainDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // textView
    private TextView nameTextView, carNumberTextView;

    // imageView
    private CircleImageView profileImage;

    // editText
    private TextInputEditText locationEditText;

    // firebase
    private DatabaseReference userReference;

    // map call back
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(23.742096, 90.427698);

            try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                getContext(), R.raw.mapstyle));

                if (!success) {
                }
            } catch (Exception e) {

            }

            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment\
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // initialize
        init(view);

        // editText on focus change
        locationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                changeEditTextString(locationEditText, isFocus);
            }
        });
        // editText on focus change

        // on click listeners
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // on click listeners

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    // firebase
    private void getUserInformation() {

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if(dataSnapshot.getKey().equals(getDataFromRoomDatabase().getNumber())){

                        // set otp value
                        User user = dataSnapshot.getValue(User.class);

                        // name textView
                        String name = "";
                        try{
                            name = user.getName().substring(0, user.getName().indexOf(' '));
                        }catch (Exception e){
                            name = user.getName();
                        }
                        nameTextView.setText("Hey "+name+"!");
                        carNumberTextView.setText("Car Number : "+user.getCarNumber()); // card number

                        // set image
                        profileImage.setBackground(null);
                        Glide.with(getContext())
                                .load(R.drawable.b2)
                                .into(profileImage);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    // firebase

    // change view
    private void changeEditTextString(View view, boolean hasFocus){

        if(hasFocus){
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string));
        }else{
            view.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edittext_string2));
        }

    }
    // change view

    // room database
    private LocalUser getDataFromRoomDatabase(){

        // ROOM DATABASE
        MainDatabase mainDatabase;
        mainDatabase = MainDatabase.getInstance(getContext());
        return mainDatabase.userDao().getUserList().get(0);

    }
    // room database

    // initialize
    private void init(View view) {
        initializeViews(view);
        getUserInformation();
    }

    private void initializeViews(View view) {

        // textView
        nameTextView = view.findViewById(R.id.textViewId_name);
        carNumberTextView = view.findViewById(R.id.textViewId_carNumber);

        // imageView
        profileImage = view.findViewById(R.id.circleImageView);

        // editText
        locationEditText = view.findViewById(R.id.editTextId_location);

        // firebase
        userReference = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getUser());

    }
    // initialize

}