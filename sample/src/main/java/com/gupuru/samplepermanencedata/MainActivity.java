package com.gupuru.samplepermanencedata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gupuru.permanencedata.PermanenceData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private TextView statusTextView;
    private PermanenceData permanenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //マシュマロ判別
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //マシュマロ
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                //未許可
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }

        permanenceData = new PermanenceData();

        Button writeBtn = (Button) findViewById(R.id.write_btn);
        writeBtn.setOnClickListener(this);
        Button readBtn = (Button) findViewById(R.id.read_btn);
        readBtn.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.edit_text);
        statusTextView = (TextView)findViewById(R.id.status_text_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write_btn:
                String text = editText.getText().toString();
                if (!text.equals("")) {
                    statusTextView.setText("write...");
                    permanenceData.save(editText.getText().toString());
                }
                break;
            case R.id.read_btn:
                statusTextView.setText("read...");
                statusTextView.setText(permanenceData.read());
                break;
            default:
                break;
        }
    }
}
