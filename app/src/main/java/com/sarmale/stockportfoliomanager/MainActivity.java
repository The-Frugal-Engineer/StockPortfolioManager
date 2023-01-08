package com.sarmale.stockportfoliomanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sarmale.stockportfoliomanager.auxelements.AuxClass;
import com.sarmale.stockportfoliomanager.auxelements.MyApplication;
import com.sarmale.stockportfoliomanager.external.FileManager;
import com.sarmale.stockportfoliomanager.stocks.UserStockPosition;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = AuxClass.TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button options = findViewById(R.id.options);
        Button checkPortfolio = findViewById(R.id.checkPortfolio);

        FileManager fileManager = new FileManager(getApplicationContext());
        ArrayList<UserStockPosition> listUserStockPosition = new ArrayList<UserStockPosition>();
        listUserStockPosition=fileManager.readUserPositionsFromFile();
        MyApplication.getApplication().setAllUserStockPosition(listUserStockPosition);

        checkPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                startActivity(new Intent(MainActivity.this, ListOfStocks.class));
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                startActivity(new Intent(MainActivity.this, Options.class));
            }
        });
    }


}