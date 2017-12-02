package souravsharma.com.passwordbank.util;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.bean.Password;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sourav sharma on 01-07-2017.
 */

public class Utility {
    private static final String MY_PREFS_NAME = "data_base";

    public static void setPasswordInSharedPreferences(Context mContext, Password password)
    {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("password", password.getPasswordText());
        editor.commit();
    }

    public static void pushFragmentWithOutIncreamentingStack(AppCompatActivity context, Fragment fragment) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);//R.id.content_frame is the layout you want to replace
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
        fragmentTransaction.commit();
    }
    public static String getPassword(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("password", null);
        return name;
    }
}
