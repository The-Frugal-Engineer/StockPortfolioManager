package com.sarmale.stockportfoliomanager.auxelements;

import android.app.Application;

import com.sarmale.stockportfoliomanager.stocks.UserStockPosition;

import java.util.ArrayList;

public class MyApplication extends Application
{
    private static MyApplication sInstance;
    ArrayList<UserStockPosition> allUserStockPosition = new ArrayList<UserStockPosition>();

    public static MyApplication getApplication() {
        return sInstance;
    }

    public  void setupConnectedThread() {
    }

    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public void setAllUserStockPosition(ArrayList<UserStockPosition> allUserStockPosition)
    {
        this.allUserStockPosition=allUserStockPosition;
    }

    public ArrayList<UserStockPosition> getAllUserStockPosition()
    {
        return allUserStockPosition;
    }

    public void AddPositionToList(UserStockPosition userStockPosition){
        this.allUserStockPosition.add(userStockPosition);
    }
    public void removeAllUserStockPosition(){
        this.allUserStockPosition.clear();
    }

    public UserStockPosition getUserStockPositionByIndex(int index){
        return allUserStockPosition.get(index);
    }
}