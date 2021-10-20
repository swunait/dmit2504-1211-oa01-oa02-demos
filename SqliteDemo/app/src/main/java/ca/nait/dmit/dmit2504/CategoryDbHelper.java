package ca.nait.dmit.dmit2504;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "CategoryDbHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SqliteDemo.db";

    private static final String SQL_CREATE_CATEGORY_ENTRIES =
            "CREATE TABLE " + DatabaseContract.CategoryEntry.TABLE_NAME + "("
                + DatabaseContract.CategoryEntry._ID + " INTEGER PRIMARY KEY, "
                + DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME + " TEXT"
                + ")";

    private static final String SQL_DELETE_CATEGORY_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.CategoryEntry.TABLE_NAME;

    public CategoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
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

    public List<Category> getCategoriesList() {
        Cursor queryResultCursor = getCategoriesCursor();
        List<Category> categories = new ArrayList<>();
        while (queryResultCursor.moveToNext()) {
            Category currentCategory = new Category();
            currentCategory.setCategoryId(queryResultCursor.getInt(0));
            currentCategory.setCategoryName(queryResultCursor.getString(1));
            categories.add(currentCategory);
        }
        return categories;
    }
}
