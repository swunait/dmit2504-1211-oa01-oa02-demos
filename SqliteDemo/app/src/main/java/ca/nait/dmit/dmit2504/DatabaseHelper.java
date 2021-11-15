package ca.nait.dmit.dmit2504;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "SqliteDemo.db";

    private static final String SQL_CREATE_CATEGORY_ENTRIES =
            "CREATE TABLE " + DatabaseContract.CategoryEntry.TABLE_NAME + "("
                + DatabaseContract.CategoryEntry._ID + " INTEGER PRIMARY KEY, "
                + DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME + " TEXT"
                + ")";

    private static final String SQL_CREATE_PRODUCT_ENTRIES =
            "CREATE TABLE " + DatabaseContract.ProductEntry.TABLE_NAME + "("
                + DatabaseContract.ProductEntry._ID + " INTEGER PRIMARY KEY, "
                + DatabaseContract.ProductEntry.COLUMN_NAME_PRODUCTNAME + " TEXT, "
                + DatabaseContract.ProductEntry.COLUMN_NAME_UNITPRICE + " REAL,"
                + DatabaseContract.ProductEntry.COLUMN_NAME_CATEGORYID + " INTEGER, "
                + "FOREIGN KEY (" + DatabaseContract.ProductEntry.COLUMN_NAME_CATEGORYID + ")"
                    + " REFERENCES " + DatabaseContract.CategoryEntry.TABLE_NAME + "(" + DatabaseContract.CategoryEntry._ID + ")"
            + ")";

    private static final String SQL_DELETE_CATEGORY_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.CategoryEntry.TABLE_NAME;
    private static final String SQL_DELETE_PRODUCT_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.ProductEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT_ENTRIES);

        final String insertCat1 = "INSERT INTO " + DatabaseContract.CategoryEntry.TABLE_NAME
                + "(" + DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME + ")"
                + "VALUES('Category 1')";
        final String insertCat2 = "INSERT INTO " + DatabaseContract.CategoryEntry.TABLE_NAME
                + "(" + DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME + ")"
                + "VALUES('Category 2')";
        final String insertCat3 = "INSERT INTO " + DatabaseContract.CategoryEntry.TABLE_NAME
                + "(" + DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME + ")"
                + "VALUES('Category 3')";
        final String insertCat4 = "INSERT INTO category_table(category_name) VALUES('Category 4')";
        final String insertCat5 = "INSERT INTO category_table(category_name) VALUES('Category 5')";
        sqLiteDatabase.execSQL(insertCat1);
        sqLiteDatabase.execSQL(insertCat2);
        sqLiteDatabase.execSQL(insertCat3);
        sqLiteDatabase.execSQL(insertCat4);
        sqLiteDatabase.execSQL(insertCat5);

        final String cat1Prod1 = "INSERT INTO product_table(product_name, unit_price, category_id) VALUES('Cat 1 Product 1', 1.23, 1)";
        final String cat1Prod2 = "INSERT INTO product_table(product_name, unit_price, category_id) VALUES('Cat 1 Product 2', 4.56, 1)";
        final String cat1Prod3 = "INSERT INTO product_table(product_name, unit_price, category_id) VALUES('Cat 1 Product 3', 7.89, 1)";
        final String cat3Prod1 = "INSERT INTO product_table(product_name, unit_price, category_id) VALUES('Cat 3 Product 1', 9.87, 3)";
        final String cat3Prod2 = "INSERT INTO product_table(product_name, unit_price, category_id) VALUES('Cat 3 Product 2', 6.54, 3)";
        final String cat3Prod3 = "INSERT INTO product_table(product_name, unit_price, category_id) VALUES('Cat 3 Product 3', 3.21, 3)";
        sqLiteDatabase.execSQL(cat1Prod1);
        sqLiteDatabase.execSQL(cat1Prod2);
        sqLiteDatabase.execSQL(cat1Prod3);
        sqLiteDatabase.execSQL(cat3Prod1);
        sqLiteDatabase.execSQL(cat3Prod2);
        sqLiteDatabase.execSQL(cat3Prod3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_PRODUCT_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_CATEGORY_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public long addCategory(Category newCategory) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME, newCategory.getCategoryName());
        return db.insert(DatabaseContract.CategoryEntry.TABLE_NAME, null, values);
    }

    public Cursor getCategoriesCursor() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                DatabaseContract.CategoryEntry._ID,
                DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME
        };
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME + " ASC";

        return db.query(
                DatabaseContract.CategoryEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }

    public Cursor getProductsByCategoryId(int categoryId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                DatabaseContract.ProductEntry._ID,
                DatabaseContract.ProductEntry.COLUMN_NAME_PRODUCTNAME,
                DatabaseContract.ProductEntry.COLUMN_NAME_UNITPRICE,
                DatabaseContract.ProductEntry.COLUMN_NAME_CATEGORYID
        };
        String selection = DatabaseContract.ProductEntry.COLUMN_NAME_CATEGORYID + " = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};
        String groupBy = null;
        String having = null;
        String orderBy = DatabaseContract.ProductEntry.COLUMN_NAME_PRODUCTNAME + " ASC";

        return db.query(
                DatabaseContract.ProductEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }

    public Cursor findOneCategoryCursorById(int categoryId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                DatabaseContract.CategoryEntry._ID,
                DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME
        };
        String selection = DatabaseContract.CategoryEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        return db.query(
                DatabaseContract.CategoryEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }

    private Category mapCursorToCategory(Cursor queryResultCursor) {
        Category currentCategory = new Category();

//        currentCategory.setCategoryId(queryResultCursor.getInt(0));
//        currentCategory.setCategoryName(queryResultCursor.getString(1));

        int columnIndexCategoryId = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.CategoryEntry._ID);
        currentCategory.setCategoryId(queryResultCursor.getInt(columnIndexCategoryId));
        int columnIndexCategoryName = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME);
        currentCategory.setCategoryName(queryResultCursor.getString(columnIndexCategoryName));

        return currentCategory;
    }

    public List<Product> getProductsListByCategoryId(int categoryId) {
        Cursor queryResultCursor = getProductsByCategoryId(categoryId);
        List<Product> products = new ArrayList<>();
        while (queryResultCursor.moveToNext()) {
            Product currentProduct = mapCursorToProduct(queryResultCursor);
            products.add(currentProduct);
        }
        return products;
    }

    private Product mapCursorToProduct(Cursor queryResultCursor) {
        Product currentProduct = new Product();

        int columnIndexProductId = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.ProductEntry._ID);
        currentProduct.setProductId(queryResultCursor.getInt(columnIndexProductId));
        int columnIndexProductName = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_NAME_PRODUCTNAME);
        currentProduct.setProductName(queryResultCursor.getString(columnIndexProductName));
        int columnIndexUnitPrice = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_NAME_UNITPRICE);
        currentProduct.setUnitPrice(queryResultCursor.getDouble(columnIndexUnitPrice));
        int columnIndexCategoryId = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_NAME_CATEGORYID);
        currentProduct.setCategoryId(queryResultCursor.getInt(columnIndexCategoryId));

        return currentProduct;
    }

    public List<Category> getCategoriesList() {
        Cursor queryResultCursor = getCategoriesCursor();
        List<Category> categories = new ArrayList<>();
        while (queryResultCursor.moveToNext()) {
//            Category currentCategory = new Category();
//            currentCategory.setCategoryId(queryResultCursor.getInt(0));
//            currentCategory.setCategoryName(queryResultCursor.getString(1));

            Category currentCategory = mapCursorToCategory(queryResultCursor);

            categories.add(currentCategory);
        }
        return categories;
    }

    public Category findOneCategoryById(int categoryId) {
        Category existingCategory = null;

        Cursor queryResultCursor = findOneCategoryCursorById(categoryId);
        if (queryResultCursor.moveToNext()) {
            existingCategory = mapCursorToCategory(queryResultCursor);
        }

        return existingCategory;
    }

    public int updateCategory(int categoryId, Category updatedCategory) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME, updatedCategory.getCategoryName());
        final String whereClause = DatabaseContract.CategoryEntry._ID + " = ?";
        final String[] whereArgs = {String.valueOf(categoryId)};
        return db.update(DatabaseContract.CategoryEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public int deleteCategory(int categoryId) {
        SQLiteDatabase db = getWritableDatabase();
        final String whereClause = DatabaseContract.CategoryEntry._ID + " = ?";
        final String[] whereArgs = {String.valueOf(categoryId)};
        return db.delete(DatabaseContract.CategoryEntry.TABLE_NAME, whereClause, whereArgs);
    }

}
