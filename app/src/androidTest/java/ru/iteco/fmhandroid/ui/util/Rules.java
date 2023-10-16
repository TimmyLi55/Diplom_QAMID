package ru.iteco.fmhandroid.ui.util;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;

import java.util.Locale;

import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.AuthorizationsTests;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;

public class Rules {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
}

//    public static void xiaomi(ActivityScenarioRule<AppActivity> mActivityScenarioRule) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                if ("xiaomi".equals(Build.MANUFACTURER.toLowerCase(Locale.ROOT))) {
//                    final Intent intent =new Intent("miui.intent.action.APP_PERM_EDITOR");
//                    intent.setClassName("com.miui.securitycenter",
//                            "com.miui.permcenter.permissions.PermissionsEditorActivity");
//                    intent.putExtra("extra_pkgname", getPackageName());
//                    new AlertDialog.Builder(this)
//                            .setTitle("Please Enable the additional permissions")
//                            .setMessage("You will not receive notifications while the app is in background if you disable these permissions")
//                            .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startActivity(intent);
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_info)
//                            .setCancelable(false)
//                            .show();
//                }else {
//                    Intent overlaySettings = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(overlaySettings, OVERLAY_REQUEST_CODE);
//                }
//            }
//        }
//        });
//    }
//}


