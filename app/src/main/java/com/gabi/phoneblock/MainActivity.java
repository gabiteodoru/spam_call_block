package com.gabi.phoneblock;

import android.app.Activity;
import android.app.role.RoleManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Switch;

public class MainActivity extends Activity {
    static final String PREFS = "settings";
    static final String ENABLED = "enabled";

    private Switch toggle;
    private SharedPreferences prefs;
    private RoleManager roles;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        roles = getSystemService(RoleManager.class);

        toggle = new Switch(this);
        toggle.setText("Block +346  ");
        toggle.setTextSize(24);
        toggle.setOnClickListener(v -> {
            boolean on = toggle.isChecked();
            prefs.edit().putBoolean(ENABLED, on).apply();
            if (on && !roles.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
                startActivityForResult(
                        roles.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING), 1);
            }
        });

        FrameLayout layout = new FrameLayout(this);
        layout.addView(toggle, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        setContentView(layout);
    }

    /** Shows "on" only if filtering is enabled and Android will actually send us calls. */
    @Override
    protected void onResume() {
        super.onResume();
        toggle.setChecked(prefs.getBoolean(ENABLED, false)
                && roles.isRoleHeld(RoleManager.ROLE_CALL_SCREENING));
    }
}
