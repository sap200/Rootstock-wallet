package rootstock;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MyWeb3JClient {
    private Web3j web3j;

    public MyWeb3JClient() {
        web3j = Web3j.build(new HttpService("https://public-node.testnet.rsk.co"));
    }

    public String fetchBalance(String address) {
        try {
            EthGetBalance balanceWei = web3j.ethGetBalance(address, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();

            // Convert balance from Wei to Ether and print
            BigInteger balanceInWei = balanceWei.getBalance();
            BigDecimal balanceInEther = Convert.fromWei(new BigDecimal(balanceInWei), Convert.Unit.ETHER);
            return String.format("%.5s", balanceInEther.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return "Error";
    }

    public String sendTransaction(String SENDER_PRIVATE_KEY, String RECEIVER_ADDRESS, String amount ) {

        try {
            Credentials credentials = Credentials.create(SENDER_PRIVATE_KEY);

            // Convert Ether to Wei (1 Ether = 10^18 Wei)
            System.out.println("Amount: " + amount );
            System.out.println("Address: " + RECEIVER_ADDRESS);
            BigDecimal amountEther = new BigDecimal(amount.trim()); // Replace with the amount you want to send
            BigInteger amountWei = Convert.toWei(amountEther, Convert.Unit.ETHER).toBigInteger();

            // Get the nonce
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            // Create the transaction
            EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
            BigInteger gasPrice = ethGasPrice.getGasPrice();

            BigInteger gasLimit = BigInteger.valueOf(21000);

            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    nonce, gasPrice, gasLimit, RECEIVER_ADDRESS.substring(2), amountWei);

            // Sign the transaction
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            String x = web3j.ethSendRawTransaction(hexValue).send().getTransactionHash();

            System.out.println("Transaction hash: " + x);

            return x;

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Here i am ");
        return null;
    }


}
