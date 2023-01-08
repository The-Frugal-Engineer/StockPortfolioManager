package com.sarmale.stockportfoliomanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sarmale.stockportfoliomanager.auxelements.MyApplication;
import com.sarmale.stockportfoliomanager.auxelements.SaveAppPreferences;
import com.sarmale.stockportfoliomanager.external.FileManager;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Button saveCrentials = findViewById(R.id.saveCrentials);
        Button deleteAllPositions = findViewById(R.id.deleteAllPositions);
        saveCrentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                SaveAppPreferences saveAppPreferences = new SaveAppPreferences(getApplicationContext());
                EditText apiHost = findViewById(R.id.apiHost);
                EditText apiKey = findViewById(R.id.apiKey);
                saveAppPreferences.saveAPICredentials(apiHost.getText().toString(),apiKey.getText().toString());
                Toast.makeText(view.getContext(), "API Credentials saved", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Options.this, MainActivity.class));

            }
        });
        deleteAllPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                //Delete the file with all positions
                FileManager fileManager = new FileManager(getApplicationContext());
                fileManager.deleteFile();
                MyApplication.getApplication().removeAllUserStockPosition();
            }
        });




    }
}