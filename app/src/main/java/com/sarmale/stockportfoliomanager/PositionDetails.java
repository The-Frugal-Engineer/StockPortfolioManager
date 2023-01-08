package com.sarmale.stockportfoliomanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sarmale.stockportfoliomanager.auxelements.AuxClass;
import com.sarmale.stockportfoliomanager.auxelements.MyApplication;
import com.sarmale.stockportfoliomanager.stocks.StockOperations;
import com.sarmale.stockportfoliomanager.stocks.UserStockPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

public class PositionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        int index = b.getInt(AuxClass.INTENT_TAG_STOCK);

        UserStockPosition userStockPositionByIndex = MyApplication.getApplication().getUserStockPositionByIndex(index);

        setContentView(R.layout.activity_position_details);
        TextView stockSymbol, stockName, stocksNumber, purchasePrice,purchaseDate, lastUpdated, gainsLosses, lastMarketPrice;
        Button returnButton;
        stockSymbol = findViewById(R.id.stock_symbol);
        stockName = findViewById(R.id.stock_name);
        stocksNumber = findViewById(R.id.stocks_number);
        purchasePrice = findViewById(R.id.purchase_price);
        purchaseDate = findViewById(R.id.purchase_date);
        lastUpdated = findViewById(R.id.last_updated);
        gainsLosses = findViewById(R.id.gains_losses);
        returnButton = findViewById(R.id.return_button);
        lastMarketPrice = findViewById( R.id.last_market_price);

        stockSymbol.setText(userStockPositionByIndex.getStockSymbol());
        stockName.setText(userStockPositionByIndex.getStockName());
        stocksNumber.setText(String.valueOf(userStockPositionByIndex.getNumberOfStocks()));
        purchasePrice.setText(String.valueOf(userStockPositionByIndex.getAcquisitionPrice()));
        //Format dates
        SimpleDateFormat curFormater = new SimpleDateFormat("DD/MM/yyyy");
        purchaseDate.setText(curFormater.format(userStockPositionByIndex.getAcquisitionDate()));
        lastUpdated.setText(curFormater.format(userStockPositionByIndex.getLastUpdatedDate()));

        //Round to just 2 decimals
        Double auxStockGains = StockOperations.CalculateCurrentOperationPosition(userStockPositionByIndex);
        BigDecimal stockGainsBD = new BigDecimal(auxStockGains).setScale(2, RoundingMode.HALF_UP);
        double stockGains=stockGainsBD.doubleValue();

        if(stockGains ==0){
            gainsLosses.setTextColor(AuxClass.COLOR_YELLOW);
        }
        else if (stockGains > 0){
            gainsLosses.setTextColor(AuxClass.COLOR_GREEN);
        }
        else{
            gainsLosses.setTextColor(AuxClass.COLOR_RED);
        }
        gainsLosses.setText(String.valueOf(stockGains));
        lastMarketPrice.setText(String.valueOf(userStockPositionByIndex.getLastStockPrice()));

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                startActivity(new Intent(PositionDetails.this, ListOfStocks.class));

            }
        });





    }
}