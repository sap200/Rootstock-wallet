package rootstock;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.EthLog;

import java.io.File;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RootStock {
    private SharedPreferences sharedPreferences;

    public static final String PRIVATE_KEY = "private_key";
    public static final String PUBLIC_KEY = "public_key";
    public static final String ADDRESS = "address";


    public RootStock(Context context) {
        sharedPreferences = context.getSharedPreferences("wallet_prefs", Context.MODE_PRIVATE);
    }



    public void generateWallet(Context context) {
        try {
            // Create a new wallet
            Log.d("Address", "Creating a wallet !");
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            String privateKey = ecKeyPair.getPrivateKey().toString(16);
            String publicKey = ecKeyPair.getPublicKey().toString(16);
            String address = Keys.getAddress(ecKeyPair);

            // Print keys
            System.out.println("Private Key: " + privateKey);
            System.out.println("Public Key: " + publicKey);
            System.out.println("Address: " + address);

            this.saveKeys(privateKey, publicKey, address);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void saveKeys(String privateKey, String publicKey, String address) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PRIVATE_KEY, privateKey);
        editor.putString(PUBLIC_KEY, publicKey);
        editor.putString(ADDRESS, address);
        editor.commit();

        System.out.println( "Inside Shared preferences: " +  sharedPreferences.getString(PRIVATE_KEY, null) );
    }

    public void deleteWallet() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PRIVATE_KEY);
        editor.remove(PUBLIC_KEY);
        editor.remove(ADDRESS);
        editor.commit();
    }

    public String getPrivateKey() {
        return sharedPreferences.getString(PRIVATE_KEY, null);
    }

    public String getPublicKey() {
        return sharedPreferences.getString(PUBLIC_KEY, null);
    }

    public String getAddress() {
        return sharedPreferences.getString(ADDRESS, null);
    }
}
