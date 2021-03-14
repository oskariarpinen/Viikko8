package com.example.viikko8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int n = 10;
    String output;
    TextView toUser;
    TextView funds;
    TextView seekbarMoney;
    int state;
    SeekBar seekBar;
    int money;
    BottleDispenser automaatti = BottleDispenser.getInstance();
    Spinner spinnerBottle;
    String[] bottles;
    ArrayList temp_array;
    int x;
    int spinnerPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerBottle = findViewById(R.id.spinner);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        populateSpinner();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
            }
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbarMoney = (TextView) findViewById(R.id.textView2);
                seekbarMoney.setText(Integer.toString(progressValue)+" €");
            }

        });
    }

    private void populateSpinner() {
        ArrayList <Bottle> temp_array = automaatti.bottle_array;
        x = 0;
        bottles = new String[temp_array.size()];
        for (Bottle i : temp_array) {
            bottles[x] = i.getName()+" "+i.getSize();
            System.out.println(bottles[x]);
            x++;
        }
        ArrayAdapter<String> bottleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bottles);
        bottleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBottle.setAdapter(bottleAdapter);
    }

    public void addMoney(View v) {
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        int money = seekBar.getProgress();
        automaatti.addMoney(money);
        output = "Klink! Added "+ Integer.toString(money) +" €";
        toUser = (TextView) findViewById(R.id.userOutput);
        funds = (TextView) findViewById(R.id.moneyIn);
        toUser.setText(output);
        updateAmount();
    }
    public void buyBottle(View v) {
        spinnerBottle = findViewById(R.id.spinner);
        spinnerPos = spinnerBottle.getSelectedItemPosition();
        state = automaatti.buyBottle(spinnerPos);
        if (state == 0) {
            output = "No more bottles :(";
        }
        else if (state == 1){
            output = "Add money first!";
        }
        else{ output = "Bottle came out."; }
        toUser = (TextView) findViewById(R.id.userOutput);
        toUser.setText(output);
        updateAmount();
        populateSpinner();
    }

    public void returnMoney(View v){
        double returned = automaatti.returnMoney();
        output =  "Money came out! You got "+Double.toString(returned) + " € back";
        toUser.setText(output);
        updateAmount();
    }

    public void updateAmount(){
        funds = (TextView) findViewById(R.id.moneyIn);
        double tempmoney = automaatti.getMoney();
        output = String.format("%.2f €",tempmoney);
        funds.setText(output);
    }
}
