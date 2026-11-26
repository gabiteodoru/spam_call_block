package com.gabi.phoneblock;

import android.net.Uri;
import android.telecom.Call;
import android.telecom.CallScreeningService;
import android.telephony.TelephonyManager;

/** Started by Android only when there is a call to screen. */
public class BlockService extends CallScreeningService {
    @Override
    public void onScreenCall(Call.Details details) {
        CallResponse.Builder response = new CallResponse.Builder();
        Uri handle = details.getHandle();
        if (details.getCallDirection() == Call.Details.DIRECTION_INCOMING
                && handle != null
                && getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE)
                        .getBoolean(MainActivity.ENABLED, false)
                && NumberFilter.isBlocked(handle.getSchemeSpecificPart(),
                        getSystemService(TelephonyManager.class).getNetworkCountryIso())) {
            // Rejected calls stay in the call log, so they can be checked later.
            response.setDisallowCall(true)
                    .setRejectCall(true)
                    .setSkipNotification(true);
        }
        respondToCall(details, response.build());
    }
}
