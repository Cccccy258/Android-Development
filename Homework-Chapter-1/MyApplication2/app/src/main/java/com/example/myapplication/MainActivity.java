package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv1 = findViewById(R.id.text);
                tv1.setText(R.string.app_name);
                Log.d("button", "button pressed");
            }
        });

        final RadioButton radioButton1 = findViewById(R.id.radio_button1);
        final RadioButton radioButton2 = findViewById(R.id.radio_button2);
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    disableOthers(buttonView.getId());
                    Log.d("radioButton", "您最喜欢的职业是: "+buttonView.getText().toString() );
                    buttonView.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    buttonView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
            public void disableOthers(int viewId) {
                if(R.id.radio_button1!=viewId && radioButton1.isChecked()){
                    radioButton1.setChecked(false);
                }
                if(R.id.radio_button2!=viewId && radioButton2.isChecked()){
                    radioButton2.setChecked(false);
                }
            }
        });

        EditText editText = findViewById(R.id.editText);
        Log.d("editText", editText.getText().toString());
    }
}
