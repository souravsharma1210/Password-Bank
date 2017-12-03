package souravsharma.com.passwordbank.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;

import souravsharma.com.passwordbank.R;
import souravsharma.com.passwordbank.bean.Password;
import souravsharma.com.passwordbank.fragment.PasswordDetailFragment;
import souravsharma.com.passwordbank.util.FragmentStack;
import souravsharma.com.passwordbank.util.onMoveAndSwipedListener;


/**
 * Created by sourav sharma on 01-07-2017.
 */

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.Holder>{
    private static final String DEBUG_TAG =PasswordAdapter.class.getSimpleName() ;
    private Context mContext;
    private Cursor cursor;
    public PasswordAdapter(Context context, Cursor cursor) {
        this.mContext=context;
        this.cursor=cursor;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recyclerview_item, parent, false);
        return new Holder(view);
    }
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Log.d(DEBUG_TAG,"position in on bind"+position);
        cursor.moveToPosition(position);
        holder.passwordName.setText(cursor.getString(0));
        /*holder.password.setText(cursor.getString(2));
        holder.userId.setText(cursor.getString(1));*/
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                Password password=new Password();
                password.setPasswordName(cursor.getString(0));
                password.setPasswordText(cursor.getString(2));
                password.setUserId(cursor.getString(1));
                password.setUrl(cursor.getString(3));
                password.setNotes(cursor.getString(4));
                FragmentStack.getInstance(mContext).pushFragment((AppCompatActivity) mContext,PasswordDetailFragment.getInstance(password));
            }
        });
        /*holder.layout.setVisibility(View.GONE);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.layout.getVisibility()==View.GONE)
                {
                    holder.layout.setVisibility(View.VISIBLE);
                    holder.layout.setAlpha(0.0f);
                    holder.layout.animate().translationY(holder.layout.getHeight()).alpha(1.0f);
                }
                else
                {
                    holder.layout.setVisibility(View.GONE);
                    holder.layout.setAlpha(1.0f);
                    holder.layout.animate().translationY(0).alpha(1.0f);
                }

            }
        });*/
    }
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView passwordName,userId,password;
        LinearLayout linearLayout;

        public Holder(View itemView) {
            super(itemView);
            passwordName= (TextView) itemView.findViewById(R.id.password_name);
           /* userId=(TextView)itemView.findViewById(R.id.user_id);
            password=(TextView)itemView.findViewById(R.id.password);
           */ linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_item1);


        }
    }
}
