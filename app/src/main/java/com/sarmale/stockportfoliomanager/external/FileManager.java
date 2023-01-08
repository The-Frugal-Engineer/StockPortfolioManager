package com.sarmale.stockportfoliomanager.external;

import android.content.Context;
import android.util.Log;

import com.sarmale.stockportfoliomanager.auxelements.AuxClass;
import com.sarmale.stockportfoliomanager.stocks.UserStockPosition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/*
IMPORTANT TO UPDATE THE CONSTANT AuxClass.FILE_COLUMNS if we add more fields to the file
Order of fields in the file
Each line onf the CSV represents a user stock position
1. Symbol Stock
2. Acquisition Price of the Stock
3. Number Of Stocks of the position
4. Acquisition Date of the position
5. The Last Stock Price from the stock Market (usually fetched from the public API)
6. Stock Name (usually fetched from the public API)
7. Last Updated date by the API
*
* */

public class FileManager {
    private static final String TAG = AuxClass.TAG;
    private Context context;
    public FileManager(Context context){

        this.context=context;
    }

    public boolean deleteFile(){
       return context.deleteFile(AuxClass.FILE_NAME);

    }

    public boolean writeAllUserPositionToFile(ArrayList<UserStockPosition> allUserStockPosition){
       //Delete the file so we can write it again
        context.deleteFile(AuxClass.FILE_NAME);
        boolean result =true;


        for (UserStockPosition userStockPosition: allUserStockPosition){
            if(!writeUserPositionToFile(userStockPosition)){
                result=false;
            }
        }

        return result;
    }

    public boolean writeUserPositionToFile(UserStockPosition userStockPosition){

        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(AuxClass.FILE_NAME, Context.MODE_APPEND))) {
            String rowToWrite = userStockPosition.getStockSymbol()+","
                    +userStockPosition.getAcquisitionPrice()+","
                    +userStockPosition.getNumberOfStocks()+","
                    +userStockPosition.getAcquisitionDate().toString()+","
                    +userStockPosition.getLastStockPrice()+","
                    +userStockPosition.getStockName().replace(",", "")+","
                    +userStockPosition.getLastUpdatedDate() +"\n";

            outputStreamWriter.write(rowToWrite);
        }
        catch (Exception e){
            Log.e(TAG,"Error writing to file");
            return false;

        }

        return true;
    }
    public ArrayList<UserStockPosition> readUserPositionsFromFile(){

        ArrayList<UserStockPosition> allReadUserStockPosition = new ArrayList<UserStockPosition>();

        InputStream inputStream;
        try {

            inputStream = context.openFileInput(AuxClass.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                // Do something with the line of text
                String[] rowIntoArray = line.split(",");
                if(rowIntoArray.length == AuxClass.FILE_COLUMNS){
                    SimpleDateFormat curFormater = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

                    UserStockPosition userStockPosition = new UserStockPosition();
                    userStockPosition.setStockSymbol(rowIntoArray[0]);
                    userStockPosition.setAcquisitionPrice(Double.parseDouble(rowIntoArray[1]));
                    userStockPosition.setNumberOfStocks(Integer.parseInt(rowIntoArray[2]));
                    try {
                        Date auxDate = curFormater.parse(rowIntoArray[3]);
                        userStockPosition.setAcquisitionDate(auxDate);
                    } catch (ParseException e) {
                        Log.d(TAG,"Issue parsing AcquisitionDate");
                        e.printStackTrace();
                    }
                    userStockPosition.setLastStockPrice(Double.parseDouble(rowIntoArray[4]));
                    userStockPosition.setStockName(rowIntoArray[5]);

                    try {
                        Date auxDate2 = curFormater.parse(rowIntoArray[6]);
                        userStockPosition.setLastUpdatedDate(auxDate2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    allReadUserStockPosition.add(userStockPosition);
                    line = bufferedReader.readLine();
                }
                else{
                    line = bufferedReader.readLine();
                    Log.d(TAG,"Ignoring line");
                }
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"Error reading from file");

        }
       return allReadUserStockPosition;
    }

}
