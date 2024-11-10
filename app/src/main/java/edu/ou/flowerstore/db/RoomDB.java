package edu.ou.flowerstore.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ou.flowerstore.db.DAOs.ProductDAO;
import edu.ou.flowerstore.db.DAOs.UserDAO;
import edu.ou.flowerstore.db.entities.CategoryEntity;
import edu.ou.flowerstore.db.entities.ProductEntity;
import edu.ou.flowerstore.db.entities.UserEntity;

@Database(entities = {UserEntity.class, ProductEntity.class, CategoryEntity.class}, views = {}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    public abstract UserDAO getUserDAO();
    public abstract ProductDAO getProductDAO();

    private static volatile RoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static public final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static public RoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RoomDB.class, "flower_store")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
