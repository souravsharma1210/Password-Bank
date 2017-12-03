package souravsharma.com.passwordbank.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.activity.MainActivity;
import souravsharma.com.passwordbank.bean.Password;
import souravsharma.com.passwordbank.data.PasswordContract;
import souravsharma.com.passwordbank.util.FragmentStack;

import static souravsharma.com.passwordbank.data.PasswordContract.PasswordEntry.CONTENT_URI;

/**
 * Created by sourav sharma on 19-11-2017.
 */

public class PasswordDetailFragment extends Fragment {
    private View view;
    private EditText passwordName;
    private EditText userId;
    private EditText password;
    private Button updateBank;
    private MainActivity mContext;
    private EditText url;
    private EditText notes;
    private Button deleteBtn;

    public static PasswordDetailFragment getInstance(Password password)
    {
         PasswordDetailFragment passwordDetailFragment=new PasswordDetailFragment();
         Bundle bundle=new Bundle();
         bundle.putSerializable("password",password);
         passwordDetailFragment.setArguments(bundle);
         return passwordDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_password_detail,container,false);
        passwordName=view.findViewById(R.id.password_name);
        password=view.findViewById(R.id.password);
        url=view.findViewById(R.id.url);
        notes=view.findViewById(R.id.notes);
        userId=view.findViewById(R.id.user_id);
        updateBank=view.findViewById(R.id.update_button);
        deleteBtn=view.findViewById(R.id.delete);
        setData();
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
        updateBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput())
                {
                    update();
                }
            }
        });
        return view;
    }

    private void setData() {
        Bundle bundle=getArguments();
        Password passwordObj= (Password) bundle.getSerializable("password");
        passwordName.setText(passwordObj.getPasswordName());
        password.setText(passwordObj.getPasswordText());
        url.setText(passwordObj.getUrl());
        userId.setText(passwordObj.getUserId());
        notes.setText(passwordObj.getNotes());
    }

    private void update() {
        ContentResolver contentResolver=mContext.getContentResolver();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD_NAME,passwordName.getText().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_USER_ID,userId.getText().toString().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD,password.getText().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_URL,url.getText().toString());
        contentValues.put(PasswordContract.PasswordEntry.COLUMN_NOTES,notes.getText().toString());
        //Uri uri=contentResolver.insert(CONTENT_URI,contentValues);
        int id=mContext.getContentResolver().update(CONTENT_URI.buildUpon().appendPath(passwordName.getText().toString()).build(),contentValues,null,null);

        if(id>0)
        {mContext.onBackPressed();
         //   FragmentStack.getInstance(mContext).pushFragment(mContext,new PasswordsFragment());
            Toast.makeText(mContext, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext,"Cannot update",Toast.LENGTH_SHORT).show();
        }

    }
    private void delete()
    {
        int id=mContext.getContentResolver().delete(CONTENT_URI.buildUpon().appendPath(passwordName.getText().toString()).build(),null,null);
        if(id>0)
        {    mContext.onBackPressed();
            //FragmentStack.getInstance(mContext).pushFragment(mContext,new PasswordsFragment());
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=(MainActivity)context;
    }
}
