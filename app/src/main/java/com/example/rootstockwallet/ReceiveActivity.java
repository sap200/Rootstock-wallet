package com.example.rootstockwallet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rootstock.QRCodeGenerator;
import rootstock.RootStock;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receive);

        // get the private key
        RootStock rootStock = new RootStock(ReceiveActivity.this);
        Bitmap bitmap = QRCodeGenerator.generateQRcode(rootStock.getAddress());

        TextView addressView = findViewById(R.id.address_view);
        addressView.setText(rootStock.getAddress());


            ImageView qrView = findViewById(R.id.qr_image);
            qrView.setImageBitmap(bitmap);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.send_rbtc), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}