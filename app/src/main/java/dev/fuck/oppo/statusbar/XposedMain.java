package dev.fuck.oppo.statusbar;

import android.content.ContentResolver;
import android.provider.Settings.Global;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public final class XposedMain implements IXposedHookLoadPackage {

    private static final String TAG = "Fuck OPPO";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!"com.android.systemui".equals(lpparam.packageName)) {
            return;
        }

        Log.i(TAG, "Fuck Dirty StatusBar Begin");

        try {
            XposedHelpers.findAndHookMethod(Global.class, "getInt",
                    ContentResolver.class, String.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            String name = (String) param.args[1];
                            if (!"development_settings_enabled".equals(name)) {
                                return;
                            }

                            Log.i(TAG, "Fuck Development Notification successful");
                            param.setResult(0);
                        }
                    });
        } catch (Throwable throwable) {
            Log.e(TAG, "Fuck Development Notification failure", throwable);
        }

        try {
            XposedHelpers.findAndHookMethod("com.oppo.rutils.RUtils", lpparam.classLoader,
                    "OppoRUtilsCompareSystemMD5", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Log.i(TAG, "Fuck Rooted Notification successful");
                            param.setResult(0);
                        }
                    });
        } catch (Throwable throwable) {
            Log.e(TAG, "Fuck Rooted Notification failure", throwable);
        }


        Log.i(TAG, "Fuck Dirty StatusBar End");
    }
}
