package souravsharma.com.passwordbank.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.activity.MainActivity;
import souravsharma.com.passwordbank.data.PasswordContract;
import souravsharma.com.passwordbank.util.FragmentStack;
import souravsharma.com.passwordbank.util.Utility;

import static souravsharma.com.passwordbank.data.PasswordContract.PasswordEntry.CONTENT_URI;


/**
 * Created by sourav sharma on 01-07-2017.
 */

public class AddNewPasswordFragment extends android.support.v4.app.Fragment {
    private View view;
    private EditText passwordName;
    private EditText userId;
    private EditText password;
    private Button addToBank;
    private MainActivity mContext;
    private EditText url;
    private EditText notes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.new_password_layout,container,false);
        passwordName=(EditText)view.findViewById(R.id.password_name);
        userId=(EditText)view.findViewById(R.id.user_id);
        password=(EditText)view.findViewById(R.id.password);
        addToBank=(Button)view.findViewById(R.id.add_to_bank_button);
        url=(EditText)view.findViewById(R.id.url);
        notes=(EditText)view.findViewById(R.id.notes);
        addToBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isValidInput()) {
                   insetInDataBase();

               }
            }
        });
        return  view;

    }

    private boolean isValidInput(){
         if(passwordName.getText()==null || passwordName.getText().length()==0)
        {
            passwordName.setError("PASSWORD NAME IS MADATORY");
            return  false;
        }
        else if(userId.getText()==null || userId.getText().length()==0)
        {
            userId.setError("USERID IS MADATORY");
            return false;
        }

        else if(password.getText()==null || password.getText().length()==0)
        {
            password.setError("PASSWORD IS MADATORY");
            return false;
        }
        else
        {
            return true;
        }
    }

    private void insetInDataBase() {
        ContentResolver contentResolver=mContext.getContentResolver();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD_NAME,passwordName.getText().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_USER_ID,userId.getText().toString().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD,password.getText().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_URL,url.getText().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_NOTES,notes.getText().toString());
        Uri uri=contentResolver.insert(CONTENT_URI,contentValues);
        if(uri!=null)
        {
            mContext.onBackPressed();
            //FragmentStack.getInstance(mContext).pushFragment(mContext,new PasswordsFragment());
            Toast.makeText(mContext, "Inserted successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext,"Password Name already exist",Toast.LENGTH_LONG).show();
        }

}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=(MainActivity)activity;
    }

}
