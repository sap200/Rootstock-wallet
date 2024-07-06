package com.example.rootstockwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rootstock.MyWeb3JClient;
import rootstock.RootStock;

public class BalanceDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_balance_display);

        TextView balanceView = findViewById(R.id.balance_text);
        MyWeb3JClient web3JClient = new MyWeb3JClient();
        RootStock rootStock = new RootStock(BalanceDisplayActivity.this);
        String myAddress = rootStock.getAddress();

        Button receiveButton = findViewById(R.id.receive_button);
        Button sendButton = findViewById(R.id.send_button);
        ImageButton activityButton = findViewById(R.id.activity_button);

        new Thread(new Runnable() {
            @Override
            public void run() {

                String balance = web3JClient.fetchBalance(myAddress) + " TRBTC";
                runOnUiThread(() -> balanceView.setText(balance));
            }
        }).start();

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // else go to good activity
                Intent intent = new Intent(BalanceDisplayActivity.this, ReceiveActivity.class);
                startActivity(intent);


            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // else go to good activity
                Intent intent = new Intent(BalanceDisplayActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BalanceDisplayActivity.this, ActivityView.class);
                startActivity(intent);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.balance_display), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}