package com.sarmale.stockportfoliomanager.external;

import android.util.Log;

import com.google.gson.Gson;
import com.sarmale.stockportfoliomanager.auxelements.AuxClass;
import com.sarmale.stockportfoliomanager.stocks.Stock;
import com.sarmale.stockportfoliomanager.stocks.UserStockPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiCallYahoo extends Thread {
    String apiKey;
    String host;
    private static final String TAG = AuxClass.TAG;
    private Stock stockPrice;

    public ApiCallYahoo (String host, String apiKey){
        this.host=host;
        this.apiKey=apiKey;
    }

    public Stock updateStock(String symbol) {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("symbol", symbol)
                .build();

        Request request = new Request.Builder()
                .url("https://yahoo-finance97.p.rapidapi.com/stock-info")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", host)
                .build();

        try {
            Gson gson = new Gson();
            Response response = client.newCall(request).execute();
            stockPrice=gson.fromJson(response.body().string(), Stock.class);
            response.body();
        } catch (IOException e) {
            Log.e(TAG,"Error in the API Call");
            e.printStackTrace();
        }

        return stockPrice;
    }
    
    public ArrayList<UserStockPosition> refreshAllStocks(ArrayList<UserStockPosition> userStockPositionList){
        int i =0;
        for (UserStockPosition userStockPosition: userStockPositionList) {
            Stock stock = updateStock(userStockPosition.getStockSymbol());
            if(stock.data != null)
            {
                userStockPosition.setLastStockPrice(stock.data.currentPrice);
                userStockPosition.setStockName(stock.data.shortName);
                //TODO set the date as well
                Date lastUpdatedDate = Calendar.getInstance().getTime();
                userStockPosition.setLastUpdatedDate(lastUpdatedDate);
                userStockPositionList.set(i,userStockPosition);
                i++;
            }
        }
        
        return userStockPositionList;
    }


}
