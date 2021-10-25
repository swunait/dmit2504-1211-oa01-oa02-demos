package ca.nait.dmit.dmit2504;

import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract() {
    }

    public static class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category_table";
        public static final String COLUMN_NAME_CATEGORYNAME = "category_name";
    }

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "product_table";
        public static final String COLUMN_NAME_PRODUCTNAME = "product_name";
        public static final String COLUMN_NAME_UNITPRICE = "unit_price";
        public static final String COLUMN_NAME_CATEGORYID = "category_id";
    }

}
