package souravsharma.com.passwordbank.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.activity.MainActivity;
import souravsharma.com.passwordbank.util.FragmentStack;
import souravsharma.com.passwordbank.util.Utility;


/**
 * Created by sourav sharma on 01-07-2017.
 */

public class LoginFragment extends Fragment {
    private static final String DEBUG_TAG =LoginFragment.class.getSimpleName() ;
    private View view;
    private EditText passwordText;
    private Button loginButton;
    private MainActivity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view =inflater.inflate(R.layout.fragment_login,container,false);
        passwordText=(EditText)view.findViewById(R.id.first_password);
        loginButton=(Button)view.findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordText.getText()==null ||passwordText.getText().length()==0)
                {
                    passwordText.setError("enter password");
                }
                else
                {
                    if(passwordText.getText().toString().equals(Utility.getPassword(mContext)))
                    {
                        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        FragmentStack.getInstance(mContext).pushFragment(mContext,new PasswordsFragment());
                    }
                    else
                    {
                        Toast.makeText(mContext,getString(R.string.unsuccessfull_login),Toast.LENGTH_SHORT).show();
                    }
                    Log.d(DEBUG_TAG,"Password"+Utility.getPassword(mContext));
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mContext=(MainActivity)context;
    }
}
