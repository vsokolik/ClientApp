package ru.beetlesoft.clientapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.app.ClientApp;
import ru.beetlesoft.clientapp.app.ClientService;

public class GeofenceTransitionsIntentService extends IntentService {

    @Inject
    ClientService clientService;

    public static final String TRANSITION_INTENT_SERVICE = "TransitionsService";

    public GeofenceTransitionsIntentService() {
        super(TRANSITION_INTENT_SERVICE);
        ClientApp.getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                Log.e("GEO", String.valueOf(geofencingEvent.getErrorCode()));
                return;
            }

            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER||geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
                String geofenceTransitionDetails = getGeofenceTransitionDetails(
                        geofenceTransition,
                        triggeringGeofences
                );

                sendInfo(geofenceTransitionDetails);

            } else {
                Log.e("GEO", "geofence transition invalid type: " + geofenceTransition);
            }
        }
    }

    private void sendInfo(String description) {
        try {
            clientService.wallPost(description).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Вход в ";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "Выход из ";
            default:
                return "unknown geofence transition";
        }
    }

    private String getGeofenceTransitionDetails(
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
            geofence.getRequestId();
        }
        //TODO: получать longitude, latitude и radius и их добавлять в строку
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);
        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }
}
