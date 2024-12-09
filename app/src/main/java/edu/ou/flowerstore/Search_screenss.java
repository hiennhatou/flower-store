package edu.ou.flowerstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Search_screenss extends AppCompatActivity {

    private EditText searchEditText;
    private ListView historyListView;
    private ArrayAdapter<String> adapter;
    private List<String> searchHistory;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_screenss);
        
        // Khởi tạo các View
        searchEditText = findViewById(R.id.searchEditText);
        historyListView = findViewById(R.id.historyListView);
        TextView clearAllTextView = findViewById(R.id.clearAllTextView);
        View mainLayout = findViewById(R.id.main); // Khởi tạo mainLayout

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("search_history", Context.MODE_PRIVATE);

        // Tải lịch sử tìm kiếm
        loadSearchHistory();

        // Khởi tạo adapter và gán vào ListView
        adapter = new ArrayAdapter<String>(this, R.layout.items_search, searchHistory) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Sử dụng layout tùy chỉnh cho từng mục trong lịch sử
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.items_search, parent, false);
                }

                // Cập nhật TextView với từ khóa tìm kiếm
                TextView itemName = convertView.findViewById(R.id.item_name);
                itemName.setText(getItem(position));

                // Thiết lập nút xóa
                ImageButton deleteButton = convertView.findViewById(R.id.deletebtn);
                deleteButton.setOnClickListener(v -> {
                    searchHistory.remove(position);
                    adapter.notifyDataSetChanged();
                    saveSearchHistory(); // Cập nhật lại SharedPreferences
                });
                return convertView;
            }
        };
        historyListView.setAdapter(adapter);

        // Cài đặt sự kiện nhấn phím cho ô tìm kiếm
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = searchEditText.getText().toString();
                if (!query.isEmpty()) {
                    saveSearchHistory(query);
                    searchEditText.setText(""); // Xóa nội dung ô nhập
                    searchEditText.requestFocus();
                }
                return true;
            }
            return false;
        });

        // Xử lý sự kiện xóa toàn bộ lịch sử
        clearAllTextView.setOnClickListener(v -> clearSearchHistory());
    }

    private void saveSearchHistory(String query) {
        // Thêm từ khóa vào lịch sử và cập nhật ListView
        if (!searchHistory.contains(query)) {
            searchHistory.add(query);
        }
        adapter.notifyDataSetChanged();
        saveSearchHistory(); // Cập nhật SharedPreferences
    }

    private void saveSearchHistory() {
        // Lưu danh sách vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(searchHistory);
        editor.putStringSet("history", set);
        editor.apply();
    }

    private void loadSearchHistory() {
        // Tải dữ liệu từ SharedPreferences
        Set<String> set = sharedPreferences.getStringSet("history", new HashSet<>());
        searchHistory = new ArrayList<>(set);
    }

    private void clearSearchHistory() {
        // Xóa toàn bộ lịch sử tìm kiếm và cập nhật ListView
        searchHistory.clear();
        adapter.notifyDataSetChanged();

        // Xóa dữ liệu khỏi SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
