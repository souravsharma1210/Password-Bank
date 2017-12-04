package souravsharma.com.passwordbank.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.fragment.LoginFragment;
import souravsharma.com.passwordbank.fragment.SetPasswordPinFragment;
import souravsharma.com.passwordbank.fragment.SettingFragment;
import souravsharma.com.passwordbank.util.FragmentStack;
import souravsharma.com.passwordbank.util.Utility;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =MainActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Utility.getPassword(MainActivity.this)==null) {
            Utility.pushFragmentWithOutIncreamentingStack(MainActivity.this, new SetPasswordPinFragment());
        }
        else
        {
             Utility.pushFragmentWithOutIncreamentingStack(MainActivity.this, new LoginFragment());
            //Toast.makeText(MainActivity.this,"Password Set",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"MainActivity OnBackPressed"+FragmentStack.getInstance(MainActivity.this).size());
        if(FragmentStack.getInstance(MainActivity.this).size()==1)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Log out");
            alertDialog.setMessage("Are you sure you want to Logout?");
            //alertDialog.setIcon(R.drawable.ic_signout);
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    finish();
                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
        else
        {
            try {
                FragmentStack.getInstance(MainActivity.this).popFragment((AppCompatActivity)MainActivity.this);
                Log.d(TAG, " no Exception");
            } catch (Exception e) {
                e.printStackTrace();
                super.onBackPressed();
                Log.d(TAG, "Exception");
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(!(FragmentStack.getInstance(this).getTopFragment() instanceof SettingFragment)) {

                FragmentStack.getInstance(MainActivity.this).pushFragment(MainActivity.this, new SettingFragment());
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
