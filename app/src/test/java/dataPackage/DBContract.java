package dataPackage;

import android.provider.BaseColumns;

/**
 * Created by Afnan A. A. Abed on 9/2/2018.
 */

public final class DBContract {

    public static abstract class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "Tasks";
        public static final String _ID = BaseColumns._ID;
        public static final String NAME_COLUMN = "task_name";
        public static final String TIME_COLUMN = "task_time";
        public static final String PRIORITY_COLUMN = "task_priority";

    }
}
