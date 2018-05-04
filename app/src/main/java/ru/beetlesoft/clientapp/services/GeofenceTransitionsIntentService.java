package ru.beetlesoft.clientapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceTransitionsIntentService extends IntentService {

    public static final String TRANSITION_INTENT_SERVICE = "TransitionsService";

    public GeofenceTransitionsIntentService() {
        super(TRANSITION_INTENT_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
//        if (geofencingEvent.hasError()) {
//            String errorMessage = GeofenceErrorMessages.getErrorString(this,
//                    geofencingEvent.getErrorCode());
//            Log.e(TAG, errorMessage);
//            return;
//        }
//
//        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
//
//        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
//
//            // Get the geofences that were triggered. A single event can trigger
//            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
//
//            // Get the transition details as a String.
//            String geofenceTransitionDetails = getGeofenceTransitionDetails(
//                    this,
//                    geofenceTransition,
//                    triggeringGeofences
//            );
//
//            // Send notification and log the transition details.
//            sendNotification(geofenceTransitionDetails);
//            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e("GEO", "geofence_transition_invalid_type: " + geofenceTransition);
        }
    }

//    private void processGeofence(Geofence geofence, int transitionType) {
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "");
//        PendingIntent openActivityIntetnt = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        int id = Integer.parseInt(geofence.getRequestId());
//        String transitionTypeString = getTransitionTypeString(transitionType);
//        notificationBuilder
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Geofence id: " + id)
//                .setContentText("Transition type: " + transitionTypeString)
//                .setVibrate(new long[]{500, 500})
//                .setContentIntent(openActivityIntetnt)
//                .setAutoCancel(true);
//
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        nm.notify(transitionType * 100 + id, notificationBuilder.build());
//
//        Log.d("GEO", String.format("notification built:%d %s", id, transitionTypeString));
//    }
//
//    private String getTransitionTypeString(int transitionType) {
//        switch (transitionType) {
//            case Geofence.GEOFENCE_TRANSITION_ENTER:
//                return "enter";
//            case Geofence.GEOFENCE_TRANSITION_EXIT:
//                return "exit";
//            case Geofence.GEOFENCE_TRANSITION_DWELL:
//                return "dwell";
//            default:
//                return "unknown";
//        }
//    }
}
