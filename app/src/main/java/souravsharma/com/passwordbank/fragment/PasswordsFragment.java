package souravsharma.com.passwordbank.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.activity.MainActivity;
import souravsharma.com.passwordbank.adapter.PasswordAdapter;
import souravsharma.com.passwordbank.util.FragmentStack;
import souravsharma.com.passwordbank.util.ItemTouchHelperCallback;
import souravsharma.com.passwordbank.util.onMoveAndSwipedListener;

import static souravsharma.com.passwordbank.data.PasswordContract.PasswordEntry.CONTENT_URI;

/**
 * Created by sourav sharma on 01-07-2017.
 */

public class PasswordsFragment extends Fragment {
    private static final String DEBUG_TAG =PasswordsFragment.class.getSimpleName() ;
    private View view;
    private RecyclerView recyclerView;
    private TextView noPasswordText;
    private FloatingActionButton addNewPassword;
    private MainActivity mContext;
    private PasswordAdapter passwordAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_password,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        noPasswordText=(TextView)view.findViewById(R.id.no_password_text);
        addNewPassword=(FloatingActionButton)view.findViewById(R.id.new_password_button);
        Cursor cursor=getAllPassword();
        if(!(cursor==null ||cursor.getCount()==0)) {
            Log.d(DEBUG_TAG,"Cursor");
            setUpAdapter(cursor);
            ItemTouchHelper.Callback callback = new ItemTouchHelperCallback((onMoveAndSwipedListener) passwordAdapter);
            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerView);
        }
        else
        {
            noPasswordText.setVisibility(View.VISIBLE);
        }
        addNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentStack.getInstance(mContext).pushFragment(mContext,new AddNewPasswordFragment());
            }
        });

        return view;
    }

    private Cursor getAllPassword() {
        ContentResolver contentResolver=mContext.getContentResolver();
        Cursor cursor=contentResolver.query(CONTENT_URI,null,null,null,null,null);
        //Log.d(DEBUG_TAG,"size of cursor"+cursor.getCount());
        return cursor;
    }

    private void setUpAdapter(Cursor cursor) {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down );
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        passwordAdapter=new PasswordAdapter(mContext,cursor);
        recyclerView.setAdapter(passwordAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=(MainActivity) activity;
    }
}
