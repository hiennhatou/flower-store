package edu.ou.flowerstore.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.ou.flowerstore.db.DAOs.CartDAO;
import edu.ou.flowerstore.db.entities.CartEntity;

@Database(entities = {CartEntity.class}, views = {}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    public abstract CartDAO cartDAO();
}
