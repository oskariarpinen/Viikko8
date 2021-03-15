package com.example.viikko8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String output;
    TextView toUser;
    TextView funds;
    TextView seekbarMoney;
    int state;
    SeekBar seekBar;
    BottleDispenser automaatti = BottleDispenser.getInstance();
    Spinner spinnerBottle;
    String[] bottles;
    int x;
    int spinnerPos;
    Bottle lastBottle;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
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
            bottles[x] = i.getName()+" "+i.getSize()+" "+i.getPrice()+" €";
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
        output = String.format("Money came out! You got %.2f € back",returned);
        toUser.setText(output);
        updateAmount();
    }

    public void updateAmount(){
        funds = (TextView) findViewById(R.id.moneyIn);
        double tempmoney = automaatti.getMoney();
        output = String.format("%.2f €",tempmoney);
        funds.setText(output);
    }
    @SuppressLint("DefaultLocale")
    public void writeReceipt(View v){
        try {
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput("kuitti.txt",Context.MODE_PRIVATE));
            String s = "";
            lastBottle = automaatti.getLatest();

            s = String.format("Kuitti ostoksesta.\n" +
                    "Ostettu tuote: %s %.2f\n" +
                    "Hinta: %.2f",lastBottle.getName(),lastBottle.getSize(),lastBottle.getPrice());
            out.write(s);
            out.close();
            output = "Kuitti tulostettu";
            toUser.setText(output);
        }   catch (IOException e){
            Log.e("IOExeption","Kirjoitus_virhe :-D");
        }


    }
}
