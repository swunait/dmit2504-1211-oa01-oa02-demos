package ca.nait.dmit.dmit2504;

import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract() {
    }

    public static class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category_table";
        public static final String COLUMN_NAME_CATEGORYNAME = "category_name";
    }

}
