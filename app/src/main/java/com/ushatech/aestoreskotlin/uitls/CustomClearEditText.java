package com.ushatech.aestoreskotlin.uitls;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ushatech.aestoreskotlin.R;

// GITHUB : REDEYESNCODE

public class CustomClearEditText  extends LinearLayout implements View.OnClickListener {
    private EditText editText;
    private ImageButton clearButton;

    public CustomClearEditText(Context context) {
        super(context);
        init();
    }

    public CustomClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        editText = new EditText(getContext());
        editText.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        addView(editText);

        clearButton = new ImageButton(getContext());
        clearButton.setImageResource(R.drawable.ic_plus);
        clearButton.setOnClickListener(this);
        addView(clearButton);

        setOrientation(HORIZONTAL);
    }
    public EditText getEditText() {
        return editText;
    }
    @Override
    public void onClick(View v) {
        if (v == clearButton) {
            editText.setText("");
        }
    }
}
