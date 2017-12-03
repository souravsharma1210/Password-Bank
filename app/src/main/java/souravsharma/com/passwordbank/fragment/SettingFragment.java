package souravsharma.com.passwordbank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.activity.MainActivity;

/**
 * Created by sourav sharma on 19-11-2017.
 */

public class SettingFragment extends Fragment {

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_settings,container,false);
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext= context;
    }
}
