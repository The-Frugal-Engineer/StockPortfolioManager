package com.sarmale.stockportfoliomanager.stocks;

public class StockOperations {


    public static double  CalculateCurrentOperationPosition(UserStockPosition userStock){
        //Calculate the winning or losses
        Double currentOperationPosition = (userStock.getLastStockPrice() * userStock.getNumberOfStocks()) -
                ( userStock.getAcquisitionPrice() * userStock.getNumberOfStocks());

        //if the result is positive, we are in the green, if negative, we are losing money
        return currentOperationPosition;
    }
}
