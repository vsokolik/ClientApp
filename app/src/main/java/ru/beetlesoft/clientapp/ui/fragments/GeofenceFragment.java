package ru.beetlesoft.clientapp.ui.fragments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.serializes.MyGeofence;
import ru.beetlesoft.clientapp.services.GeofenceTransitionsIntentService;

import static com.google.android.gms.location.GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE;

public class GeofenceFragment extends BaseFragment {

    @BindView(R.id.btn_subscribe)
    Button btnSubscribe;
    @BindView(R.id.btn_unsubscribe)
    Button btnUnsubscribe;
    @BindView(R.id.et_latitude)
    EditText etLatitude;
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    @BindView(R.id.et_radius)
    EditText etRadius;

    private FragmentActivity context;
    private int geoId = 0;
    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList = new ArrayList<>();
    private PendingIntent geofencePendingIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_geofence, container, false);
        ButterKnife.bind(this, rootView);
        this.context = this.getActivity();

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = etLatitude.getText().toString();
                String longitude = etLongitude.getText().toString();
                String radius = etRadius.getText().toString();

//                String latitude = "50";
//                String longitude = "47";
//                String radius = "100";
                if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(radius)) {
                    addGeo(Double.parseDouble(latitude), Double.parseDouble(longitude), Float.parseFloat(radius), Geofence.GEOFENCE_TRANSITION_ENTER);
                } else {
                    Toast.makeText(context, getString(R.string.error_empty_geo_data), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeGeo();
            }
        });

        geofenceList = new ArrayList<>();
        geofencingClient = LocationServices.getGeofencingClient(context);

        return rootView;
    }

    private void addGeo(double latitude, double longitude, float radius, int transitionType) {

        MyGeofence myGeofence = new MyGeofence(geoId, latitude, longitude, radius, transitionType);
        geofenceList.add(myGeofence.toGeofence());

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnSuccessListener(context, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("GEO", "add success");
                        }
                    })
                    .addOnFailureListener(context, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("GEO", "add fail");
                            if(((ApiException) e).getStatusCode() == GEOFENCE_NOT_AVAILABLE){
                                showToast("geofence not available");
                            } else {
                                showToast("");
                            }
                            e.printStackTrace();
                        }
                    })
            ;

        }

        geoId ++;
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    private void removeGeo() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            geofencingClient.removeGeofences(getGeofencePendingIntent())
                    .addOnSuccessListener(context, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("GEO", "remove success");
                        }
                    })
                    .addOnFailureListener(context, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("GEO", "remove fail");
                            e.printStackTrace();
                        }
                    })
            ;

        }

    }

}
