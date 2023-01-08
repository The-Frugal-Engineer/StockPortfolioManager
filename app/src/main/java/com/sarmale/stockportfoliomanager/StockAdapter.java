package com.sarmale.stockportfoliomanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sarmale.stockportfoliomanager.auxelements.AuxClass;
import com.sarmale.stockportfoliomanager.stocks.StockOperations;
import com.sarmale.stockportfoliomanager.stocks.UserStockPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private List<UserStockPosition> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    StockAdapter(Context context, List<UserStockPosition> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.stock_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String stockName = mData.get(position).getStockName();
        String stockAmount = String.valueOf(mData.get(position).getNumberOfStocks());
        UserStockPosition userStockPositionAux = mData.get(position);

        Double auxStockGains = StockOperations.CalculateCurrentOperationPosition(userStockPositionAux);
        BigDecimal stockGainsBD = new BigDecimal(auxStockGains).setScale(2, RoundingMode.HALF_UP);
        double stockGains=stockGainsBD.doubleValue();

        holder.stockName.setText(stockName);
        holder.stockAmount.setText(stockAmount);
        //Setting the color of the text depending on Gains or losses
        if(stockGains ==0){
            holder.stockGains.setTextColor(AuxClass.COLOR_YELLOW);
        }
        else if (stockGains > 0){
            holder.stockGains.setTextColor(AuxClass.COLOR_GREEN);
        }
        else{
            holder.stockGains.setTextColor(AuxClass.COLOR_RED);
        }
        holder.stockGains.setText(String.valueOf(stockGains));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stockName, stockAmount, stockGains;

        ViewHolder(View itemView) {
            super(itemView);
            stockName = itemView.findViewById(R.id.stock_name);
            stockAmount = itemView.findViewById(R.id.stock_amount);
            stockGains = itemView.findViewById(R.id.stock_gains);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    UserStockPosition getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}