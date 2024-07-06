package com.example.rootstockwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

import rootstock.RootStock;

public class MainActivity extends AppCompatActivity {

    private RootStock rootStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initializeBouncyCastle();

        rootStock = new RootStock(this);

        Button getStartedButton = findViewById(R.id.get_started);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add code here ...
                // Create 2 activities and on a condition create either of the 2 activities...
                if (rootStock.getPrivateKey() != null) {
                    // else go to good activity
                    Intent intent = new Intent(MainActivity.this, BalanceDisplayActivity.class);
                    startActivity(intent);
                } else {
                    // go to create wallet activity
                    Intent intent = new Intent(MainActivity.this, CreateWalletActivity.class);
                    startActivity(intent);
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.balance_display), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Register Bouncy Castle provider if not already registered
            Security.addProvider(new BouncyCastleProvider());
        } else {
            // Ensure Bouncy Castle is the preferred provider
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
            Security.addProvider(new BouncyCastleProvider());
        }
    }
}