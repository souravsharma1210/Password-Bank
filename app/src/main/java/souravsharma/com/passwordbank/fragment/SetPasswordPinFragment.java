package souravsharma.com.passwordbank.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.activity.MainActivity;
import souravsharma.com.passwordbank.bean.Password;
import souravsharma.com.passwordbank.util.FragmentStack;
import souravsharma.com.passwordbank.util.Utility;


public class SetPasswordPinFragment extends Fragment {
    private static final String DEBUG_TAG =SetPasswordPinFragment.class.getSimpleName() ;
    private View view;
    private EditText firstPasswordText;
    private EditText secondPasswordText;
    private Button buttonSubmit;
    private MainActivity mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_set_password_pin, container, false);
        firstPasswordText=(EditText) view.findViewById(R.id.first_password);
        secondPasswordText=(EditText)view.findViewById(R.id.second_password);
        buttonSubmit=(Button)view.findViewById(R.id.set_password_button);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstPasswordText.getText().toString().equals(secondPasswordText.getText().toString()))
                {
                    Password password=new Password();
                    password.setPasswordText(firstPasswordText.getText().toString());
                    Utility.setPasswordInSharedPreferences(mContext,password);
                    Toast.makeText(mContext,"Sign Up successfull" ,Toast.LENGTH_SHORT).show();
                    Log.d(DEBUG_TAG,"successfull Sign up");
                    FragmentStack.getInstance(mContext).pushFragment(mContext,new PasswordsFragment());
                    Log.d(DEBUG_TAG,"successfull Sign up");
                }
                else
                {
                    Log.d(DEBUG_TAG,"Unsuccessfull Sign up");
                    //Toast.makeText(mContext,mContext.getString(R.string.unsuccessfull_signUp),Toast.LENGTH_SHORT).show();
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
