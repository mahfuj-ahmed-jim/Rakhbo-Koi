package com.premiernoobs.rakhbokoi.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {LocalUser.class}, version = 2, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {

    private static MainDatabase INSTANCE;

    public static synchronized MainDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MainDatabase.class,
                    "User_Table").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE Version_table (versionNumber INT, PRIMARY KEY(userid))");
        }
    };

    public abstract UserDao userDao();

}