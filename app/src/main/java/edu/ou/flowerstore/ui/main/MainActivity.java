package edu.ou.flowerstore.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityMainBinding;
import edu.ou.flowerstore.db.entities.UserEntity;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FlowerStoreApplication application;
    MainViewModel viewModel;
    private final ArrayList<String> usersList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = FlowerStoreApplication.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersList);

        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.addBtn.setOnClickListener(v -> onAddUser());

        binding.listView.setAdapter(adapter);
        viewModel.getUsers().observe(this, list -> {
            binding.amount.setText(String.valueOf(list.size()));
            usersList.clear();
            list.forEach(i -> {
                usersList.add(i.name);
            });
            adapter.notifyDataSetChanged();
        });
    }

    private void onAddUser() {
        UserEntity user = new UserEntity();
        Editable nameText = binding.nameInput.getText();
        Editable emailText = binding.emailInput.getText();
        Editable passwordText = binding.passwordInput.getText();
        Editable saltText = binding.saltInput.getText();
        Editable phoneText = binding.phoneInput.getText();

        if (nameText == null || emailText == null || passwordText == null || saltText == null || phoneText == null)
            return;
        if (emailText.toString().isBlank() || passwordText.toString().isBlank() || saltText.toString().isBlank() || phoneText.toString().isBlank() || nameText.toString().isBlank())
            return;

        user.name = nameText.toString();
        user.email = emailText.toString();
        user.password = passwordText.toString();
        user.salt = saltText.toString();
        user.phone = phoneText.toString();

        viewModel.insert(user);
    }
}