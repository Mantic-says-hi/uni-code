package curtin.edu.assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import curtin.edu.assignment.DataSchema.GameTable;
import curtin.edu.assignment.DataSchema.SettingTable;
import curtin.edu.assignment.DataSchema.MapTable;
import curtin.edu.assignment.DataSchema.StructureTable;

//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Part of incomplete database
public class DataDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "data.db";

    public DataDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + GameTable.NAME + "(" +
                GameTable.Cols.MONEY + " INTEGER, " +
                GameTable.Cols.TIME + " INTEGER)");

        db.execSQL("CREATE TABLE " + SettingTable.NAME + "(" +
                SettingTable.Cols.INMONEY + " INTEGER, " +
                SettingTable.Cols.WIDTH + " INTEGER, " +
                SettingTable.Cols.HEIGHT + " INTEGER)");

        db.execSQL("CREATE TABLE " + MapTable.NAME + "(" +
                MapTable.Cols.TILE + " INTEGER)");

        db.execSQL("CREATE TABLE " + StructureTable.NAME + "(" +
                StructureTable.Cols.IMAGE + " INTEGER, " +
                StructureTable.Cols.DESC + " TEXT)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {

    }
}

