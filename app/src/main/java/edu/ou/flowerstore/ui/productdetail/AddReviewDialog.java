package edu.ou.flowerstore.ui.productdetail;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import edu.ou.flowerstore.R;

public class AddReviewDialog extends DialogFragment {

    private final AddReviewListener listener;

    public interface AddReviewListener {
        void onReviewAdded(String reviewText, int rate);
    }

    public AddReviewDialog(AddReviewListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.view.LayoutInflater inflater = requireActivity().getLayoutInflater();
        android.view.View view = inflater.inflate(R.layout.dialog_add_review, null);

        TextInputEditText reviewEditText = view.findViewById(R.id.reviewEditText);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar); // Lấy RatingBar
        MaterialButton submitReviewButton = view.findViewById(R.id.submitReviewButton);
        MaterialButton cancelButton = view.findViewById(R.id.cancelButton);

        submitReviewButton.setOnClickListener(v -> {
            String reviewText = reviewEditText.getText().toString().trim();
            int rate = (int) ratingBar.getRating();

            if (reviewText.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đánh giá!", Toast.LENGTH_SHORT).show();
                return;
            }

            listener.onReviewAdded(reviewText, rate);
            dismiss();
        });

        cancelButton.setOnClickListener(v -> dismiss());

        return new android.app.AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }
}