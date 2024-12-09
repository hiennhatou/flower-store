package edu.ou.flowerstore;

import android.app.Application;

import androidx.room.Room;

import edu.ou.flowerstore.db.RoomDB;

public class FlowerStoreApplication extends Application {
    static private FlowerStoreApplication instance;

    private RoomDB roomDB;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowerStoreApplication.instance = this;
        roomDB = Room.databaseBuilder(this.getApplicationContext(), RoomDB.class, "littleflower").allowMainThreadQueries().build();
    }

    public static FlowerStoreApplication getInstance() {
        return instance;
    }

    public RoomDB getRoomDB() {
        return roomDB;
    }
}
