package com.example.rootstockwallet;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Text;

import java.security.Security;
import java.util.Map;

import rootstock.RootStock;

public class CreateWalletActivity extends AppCompatActivity {

    private RootStock rootStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_wallet);

        rootStock = new RootStock(this);

        Button createWalletButton  = findViewById(R.id.create_wallet);

        createWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG 1", "Attempting to create a wallet !");
                        // generate new key
                        rootStock.generateWallet(CreateWalletActivity.this);

                        if (rootStock.getPrivateKey() != null) {
                            // else go to good activity
                            Intent intent = new Intent(CreateWalletActivity.this, BalanceDisplayActivity.class);
                            startActivity(intent);
                        } else{
                            // now go to diff big activity, if generated
                            Toast.makeText(CreateWalletActivity.this, "Failed to generate wallet !!!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).run();

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.balance_display), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}