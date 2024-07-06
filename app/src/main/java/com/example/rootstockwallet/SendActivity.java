package com.example.rootstockwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import rootstock.MyWeb3JClient;
import rootstock.RootStock;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send);

        EditText addressField = findViewById(R.id.address_field);

        EditText amountField = findViewById(R.id.amount_field);

        RootStock rootStock = new RootStock(SendActivity.this);



        Button sendTxnButton = findViewById(R.id.send_txn_button);

        MyWeb3JClient web3JClient = new MyWeb3JClient();
        String finalAddress = "";
    sendTxnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = addressField.getText().toString();
                String amount = amountField.getText().toString();

                if (address != null && address != "" && amount != null && amount != "") {
                    // send
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String txnHash = web3JClient.sendTransaction(rootStock.getPrivateKey(), address, amount);
                            System.out.println(txnHash);

                            String basePath = "https://explorer.testnet.rootstock.io/tx/";

                            runOnUiThread(() -> {
                                // go to new activity and display txn hash
                                // else go to good activity
                                Intent intent = new Intent(SendActivity.this, TransactionExplorer.class);
                                intent.putExtra("url", basePath + txnHash);
                                startActivity(intent);
                            });
                        }
                    }).start();
                } else {
                    // go to new activity and display txn hash
                    Toast.makeText(SendActivity.this, "Invalid address or amount !!!", Toast.LENGTH_LONG).show();
                }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.send_rbtc), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}