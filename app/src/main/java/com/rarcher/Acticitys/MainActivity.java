package com.rarcher.Acticitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.rarcher.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout bar;
    TextView bartext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.ly_retrieve_bar);
        bartext = bar.findViewById(R.id.bar_text);
        bartext.setText("主界面");
    }
}
