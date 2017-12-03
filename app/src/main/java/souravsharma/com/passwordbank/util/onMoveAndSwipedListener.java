package souravsharma.com.passwordbank.util;

/**
 * Created by sourav sharma on 22-11-2017.
 */

public interface onMoveAndSwipedListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
