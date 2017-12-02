package souravsharma.com.passwordbank.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sourav sharma on 01-07-2017.
 */

public class PasswordContract {
    public static final String CONTENT_AUTHORITY = "souravsharma.com.passwordbank";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PASSWORD = "password";
    public static final class PasswordEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PASSWORD)
                .build();
        public static final String TABLE_NAME = "Password";
        public static final String COLUMN_PASSWORD_NAME = "password_name";
        public static final String COLUMN_USER_ID = "userId";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_URL="Url";
        public static final String COLUMN_NOTES="notes";
    }
}
