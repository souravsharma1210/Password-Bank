package souravsharma.com.passwordbank.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import souravsharma.com.passwordbank.R;

/**
 * Created by sourav sharma on 18-11-2017.
 */

public class FragmentStack {
    private String TAG=getClass().getSimpleName();
    private final int size;
    private static  FragmentStack fragmentStackObj=null;
    private int top;
    static Context mContext;
    private Fragment[] elements;
    private FragmentStack(int s) {
        size = s;
        top = -1;
        elements =  new Fragment[size]; // create array
    }
    public static FragmentStack getInstance(Context mCntext){
        final int DEFAULT_STACK_SIZE=100;
        mContext=mCntext;
        if(fragmentStackObj==null) {
            fragmentStackObj = new FragmentStack(DEFAULT_STACK_SIZE);
        }
        return  fragmentStackObj;
    }

    public Boolean isEmpty(){
        return (top<=-1);
    }
    public Boolean isFull(){
        return (top == size - 1);
    }
    public  void pushFragment(AppCompatActivity context, Fragment fragment) {


        elements[++top] = fragment; // place pushValue on FragmentStack
        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);//R.id.content_frame is the layout you want to replace
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
        fragmentTransaction.commit();
    }
    public void popFragment(AppCompatActivity activity) throws Exception {

           elements[top]=null;
            top--;

            if (isEmpty()) // if stack is empty
                throw new Exception("Fragment Stack is Empty");
            Log.d(TAG,"in pop");
            Fragment fragment=elements[top];
            FragmentManager fragmentManager=activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            //fragmentTransaction.setCustomAnimations(R.anim.right_left_in, R.anim.right_left_out);
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
            //((HomeActivity)activity).setNavigationDrawerAction();


    }
    public Fragment getTopFragment(){
        return elements[top];

    }

    //it won't clear the home fragment
    public void clear(){
        top=0;
    }
    //it will clear every fragment from stack
    public void clearAll(){
        top=-1;
    }
    public int size(){
        return  top+1;
    }
}