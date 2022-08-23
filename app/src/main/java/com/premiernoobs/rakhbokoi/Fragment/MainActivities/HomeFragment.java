package com.premiernoobs.rakhbokoi.Fragment.MainActivities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.premiernoobs.rakhbokoi.Class.Class.Parking;
import com.premiernoobs.rakhbokoi.Class.Class.User;
import com.premiernoobs.rakhbokoi.Class.Firebase.FirebaseDatabaseClass;
import com.premiernoobs.rakhbokoi.Dialog.SearchDialog;
import com.premiernoobs.rakhbokoi.Fragment.User.OtpFragment;
import com.premiernoobs.rakhbokoi.R;
import com.premiernoobs.rakhbokoi.Room.LocalUser;
import com.premiernoobs.rakhbokoi.Room.MainDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // for permission
    private int LOCATION_PERMISSION_CODE = 1;

    // for location
    private double longitude, latitude;

    // textView
    private TextView nameTextView, carNumberTextView, locationTextView;

    // button
    private Button searchButton;

    // layout as button
    private ConstraintLayout settingsButton;

    // imageView
    private CircleImageView profileImage;

    // editText
    private TextInputEditText locationEditText;

    // firebase
    private DatabaseReference userReference, parkingReference;
    private String name, number;
    private boolean search = false;

    // dialog
    private SearchDialog searchDialog;

    // map call back
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng bashabo = new LatLng(latitude, longitude);

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

            //googleMap.addMarker(new MarkerOptions().position(bashabo).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(bashabo));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bashabo, 16.0f));

            //getParkingList(googleMap);
        }
    };

    private void getParkingList(GoogleMap googleMap) {

        parkingReference = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getParking());
        parkingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Parking parking = dataSnapshot.getValue(Parking.class);

                    /*if(parking.isAvailable()){

                    }*/

                    googleMap.addMarker(new MarkerOptions().position(
                            new LatLng(parking.getLatitude(),
                                    parking.getLongitude())).
                            title(parking.getAddress()+"\nLatitude: "+parking.getLatitude()
                                    +"\nLongitude: "+parking.getLongitude()));

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
                            return false;
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

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

        // on click listeners
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPage();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = true;
                searchDialog.show();
            }
        });

        searchDialog.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = false;
                searchDialog.dismiss();
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
                        String subName = "";
                        try{
                            name = user.getName();
                            number = user.getCarNumber();
                            subName = name.substring(0, user.getName().indexOf(' '));
                        }catch (Exception e){
                            subName = user.getName();
                        }
                        nameTextView.setText("Hey "+subName+"!");
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

    // location
    private void getLocationName() {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            //String city = addresses.get(0).getLocality();
            //String state = addresses.get(0).getAdminArea();
            //String country = addresses.get(0).getCountryName();
            //String postalCode = addresses.get(0).getPostalCode();
            //String knownName = addresses.get(0).getFeatureName();

            locationTextView.setText("Current Location : "+address);

        } catch (IOException e) {
            Log.d("Verify", e.getMessage());
        }

    }

    private void getLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // check permission
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // get location
        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } else {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        // set location name
        try{
            getLocationName();
        }catch (Exception e){
            Log.d("Verify", e.getMessage());
        }

    }

    private void permissionForLocation(boolean isClicked) {

        // permission for storage
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
        ){

            // if permission is granted fetch pic from gallery
            try{
                getLocation();
            }catch (Exception e){
                Log.d("Verify", e.getMessage());
            }

        }else{

            if(isClicked){
                // permission request
                requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        LOCATION_PERMISSION_CODE);
            }

        }

    }
    // location

    // next page
    private void settingsPage() {

        // set value to set next page for register
        Bundle bundle = new Bundle();
        bundle.putString("NAME", name);
        bundle.putString("NUMBER", number);

        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_id, settingsFragment)
                .addToBackStack(null)
                .commit();

    }
    // next page

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
        permissionForLocation(true); // location
    }

    private void initializeViews(View view) {

        // textView
        nameTextView = view.findViewById(R.id.textViewId_name);
        carNumberTextView = view.findViewById(R.id.textViewId_carNumber);
        locationTextView = view.findViewById(R.id.textView_location);

        // button
        searchButton = view.findViewById(R.id.buttonId_search);

        // imageView
        profileImage = view.findViewById(R.id.circleImageView);

        // firebase
        userReference = FirebaseDatabase.getInstance().getReference(new FirebaseDatabaseClass().getUser());

        // layout as button
        settingsButton = view.findViewById(R.id.constraintLayout3);

        // dialog
        searchDialog = new SearchDialog(getActivity());

    }
    // initialize

}