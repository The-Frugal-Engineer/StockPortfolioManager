package com.sarmale.stockportfoliomanager.stocks;

import java.util.Date;

public class UserStockPosition {
    private double acquisitionPrice;
    private int numberOfStocks;
    private String currency;
    private Stock originalStock;
    private Date acquisitionDate;
    private String userStockPositionID;
    private String stockName;
    private String stockSymbol;
    //we want to keep the last price fetched from the public API
    private double lastStockPrice;
    private Date lastUpdatedDate;

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public UserStockPosition() {

    }

    public UserStockPosition(String stockName, String stockSymbol) {
        this.stockName=stockName;
        this.stockSymbol=stockSymbol;
    }

    public UserStockPosition(double acquisitionPrice, int numberOfStocks,
                             String currency, Stock originalStock,
                             Date acquisitionDate, String userStockPositionID, String stockName, String stockSymbol) {
        this.acquisitionPrice = acquisitionPrice;
        this.numberOfStocks = numberOfStocks;
        this.currency = currency;
        this.originalStock = originalStock;
        this.acquisitionDate = acquisitionDate;
        this.userStockPositionID = userStockPositionID;
        this.stockName=stockName;
        this.stockSymbol=stockSymbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }



    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getUserStockPositionID() {
        return userStockPositionID;
    }

    public void setUserStockPositionID(String userStockPositionID) {
        userStockPositionID = userStockPositionID;
    }

    public int getNumberOfStocks() {
        return numberOfStocks;
    }

    public void setNumberOfStocks(int numberOfStocks) {
        this.numberOfStocks = numberOfStocks;
    }

    public double getAcquisitionPrice() {
        return acquisitionPrice;
    }

    public void setAcquisitionPrice(double acquisitionPrice) {
        this.acquisitionPrice = acquisitionPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Stock getOriginalStock() {
        return originalStock;
    }

    public void setOriginalStock(Stock originalStock) {
        this.originalStock = originalStock;
    }
    public double getLastStockPrice() {
        return lastStockPrice;
    }

    public void setLastStockPrice(double lastStockPrice) {
        this.lastStockPrice = lastStockPrice;
    }
}
