package edu.ou.flowerstore.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.db.DAOs.UserDAO;
import edu.ou.flowerstore.db.RoomDB;
import edu.ou.flowerstore.db.entities.UserEntity;

public class UserRepository {
    private final UserDAO userDAO;
    private LiveData<List<UserEntity>> users;

    public UserRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        userDAO = db.getUserDAO();
    }

    public LiveData<List<UserEntity>> getAllUsers() {
        if (users == null)
            users = userDAO.getAll();
        return users;
    }

    public void insertUser(UserEntity user) {
        RoomDB.dbWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }
}
