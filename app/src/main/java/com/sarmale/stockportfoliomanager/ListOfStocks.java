package com.sarmale.stockportfoliomanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sarmale.stockportfoliomanager.auxelements.AuxClass;
import com.sarmale.stockportfoliomanager.auxelements.MyApplication;
import com.sarmale.stockportfoliomanager.auxelements.SaveAppPreferences;
import com.sarmale.stockportfoliomanager.external.ApiCallYahoo;
import com.sarmale.stockportfoliomanager.external.FileManager;
import com.sarmale.stockportfoliomanager.stocks.UserStockPosition;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListOfStocks  extends AppCompatActivity implements StockAdapter.ItemClickListener {
    StockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_stocks);

        Button addPosition = findViewById(R.id.add_position);
        Button refreshPrices = findViewById(R.id.refreshPrices);

        // data to populate the RecyclerView with
        ArrayList<UserStockPosition> userStockPositions = new ArrayList<>();
        TextView emptyList = findViewById(R.id.empty_list);
        if (!MyApplication.getApplication().getAllUserStockPosition().isEmpty()) {
            emptyList.setVisibility(View.INVISIBLE);
            userStockPositions = MyApplication.getApplication().getAllUserStockPosition();
        } else {
            emptyList.setVisibility(View.VISIBLE);
        }
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StockAdapter(this, userStockPositions);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //Observable
        final Observable<ArrayList<UserStockPosition>> connectToYahooAPI = Observable.create(emitter -> {
            Log.d(AuxClass.TAG, "Calling API");

            SaveAppPreferences saveAppPreferences = new SaveAppPreferences(getApplicationContext());
            ArrayList<String> credentials = new ArrayList<String>();
            credentials = saveAppPreferences.getAPICredentials();

            ApiCallYahoo apiCallYahoo = new ApiCallYahoo(credentials.get(0), credentials.get(1));
            ArrayList<UserStockPosition> userStockPositionArrayList = MyApplication.getApplication().getAllUserStockPosition();

            emitter.onNext(apiCallYahoo.refreshAllStocks(userStockPositionArrayList));

            emitter.onComplete();
        });

        addPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                startActivity(new Intent(ListOfStocks.this, AddPosition.class));

            }
        });

        refreshPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                ArrayList<String> credentials = new ArrayList<String>();
                SaveAppPreferences saveAppPreferences = new SaveAppPreferences(getApplicationContext());
                credentials = saveAppPreferences.getAPICredentials();
                if (credentials.get(0) == null || credentials.get(1) == null) {
                    Toast.makeText(getApplicationContext(), "Please first set the credentials", Toast.LENGTH_SHORT).show();
                } else {
                    //The credentials are set
                    final ArrayList<UserStockPosition>[] userStockPositionArrayList = new ArrayList[]{new ArrayList<UserStockPosition>()};
                    connectToYahooAPI.
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribeOn(Schedulers.io()).
                            subscribe(userStockPositionsUpdated -> {
                                //valueRead returned by the onNext() from the Observable
                                Log.d(AuxClass.TAG, "API called");
                                userStockPositionArrayList[0] = userStockPositionsUpdated;
                                MyApplication.getApplication().setAllUserStockPosition(userStockPositionArrayList[0]);
                                FileManager fileManager = new FileManager(getApplicationContext());
                                fileManager.writeAllUserPositionToFile(userStockPositionArrayList[0]);
                                startActivity(new Intent(ListOfStocks.this, ListOfStocks.class));

                                //We just scratched the surface with RxAndroid
                            });


                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        UserStockPosition userStockPositionSelected = adapter.getItem(position);
        Intent i = new Intent(this, PositionDetails.class);
        i.putExtra(AuxClass.INTENT_TAG_STOCK, position);
        startActivity(i);
    }
}