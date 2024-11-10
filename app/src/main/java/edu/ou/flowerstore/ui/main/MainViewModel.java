package edu.ou.flowerstore.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ou.flowerstore.db.entities.UserEntity;
import edu.ou.flowerstore.db.repositories.UserRepository;

public class MainViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final LiveData<List<UserEntity>> users;

    public MainViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getAllUsers();
    }

    public LiveData<List<UserEntity>> getUsers() {
        return users;
    }

    public void insert(UserEntity user) {
        userRepository.insertUser(user);
    }
}
