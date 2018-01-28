package za.co.jefdev.tm.luno;

import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.jefdev.tm.luno.Constants;
import za.co.jefdev.tm.luno.account.*;
import za.co.jefdev.tm.luno.order.*;
import za.co.jefdev.tm.luno.trade.ListTradesDao;
import za.co.jefdev.tm.luno.trade.TickerDao;
import za.co.jefdev.tm.luno.trade.TickersDao;
import za.co.jefdev.tm.luno.trade.TradesDao;
import za.co.jefdev.tm.luno.transactions.FeeInfoDao;
import za.co.jefdev.tm.luno.transactions.PendingTransactionsDao;
import za.co.jefdev.tm.luno.transactions.TransactionDao;
import za.co.jefdev.tm.luno.transactions.TransactionsDao;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

import static org.springframework.http.HttpMethod.*;

@Component
public class LunoDaoService {

    public static final String XBTZAR = "XBTZAR";

    @Value("${luno.key_id}")
    private String keyId;

    @Value("${luno.secret}")
    private String secret;

    private HttpHeaders authHeaders;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper om = new ObjectMapper();

    @PostConstruct
    public LunoDaoService init() {
        String plainCreds = String.format("%s:%s",keyId,secret);
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);

        authHeaders = new HttpHeaders();
        authHeaders.add("Authorization", "Basic " + new String(base64CredsBytes));
        return this;
    }

    public TickerDao getTicker(String pair) {
        return restTemplate.getForObject("https://api.mybitx.com/api/1/ticker?pair={pair}", TickerDao.class,pair);
    }

    public TickerDao getTicker() {
        return getTicker(XBTZAR);
    }

    public TickersDao getTickers() {
        return restTemplate.getForObject("https://api.mybitx.com/api/1/tickers", TickersDao.class);
    }

    @SneakyThrows
    public OrderBookDao getOrderBook(String pair) {
        String json =
            restTemplate.getForObject("https://api.mybitx.com/api/1/orderbook?pair={pair}", String.class,pair);
        return om.readValue(json, OrderBookDao.class);
    }

    public OrderBookDao getOrderBook() {
        return getOrderBook(XBTZAR);
    }

    public TradesDao getTrades(String pair) {
        return restTemplate.getForObject("https://api.mybitx.com/api/1/trades?pair={pair}", TradesDao.class,pair);
    }

    public TradesDao getTrades() {
        return getTrades(XBTZAR);
    }

    public TradesDao getTrades(String pair,Timestamp since) {
        return restTemplate.getForObject("https://api.mybitx.com/api/1/trades?pair={pair}&since={since}",
                TradesDao.class,pair,since.getTime());
    }

    public TradesDao getTrades(Timestamp since) {
        return getTrades(XBTZAR,since);
    }

    public BalancesDao getBalances() {
        HttpEntity<TransactionDao> entity = new HttpEntity<>(authHeaders);
        return restTemplate.exchange(
                "https://api.mybitx.com/api/1/balance", GET,entity, BalancesDao.class).getBody();
    }

    public TransactionsDao getTransactions(String accountId, int limit) {
        HttpEntity<TransactionDao> entity = new HttpEntity<>(authHeaders);
        return restTemplate.exchange(
                "https://api.mybitx.com/api/1/accounts/{accountId}/transactions?min_row={minRow}&max_row={maxRow}"
                , GET,entity, TransactionsDao.class,accountId,limit*-1,0).getBody();
    }

    public PendingTransactionsDao getPending(String accountId) {
        HttpEntity<PendingTransactionsDao> entity = new HttpEntity<>(authHeaders);
        return restTemplate.exchange(
                "https://api.mybitx.com/api/1/accounts/{accountId}/pending"
                , GET,entity, PendingTransactionsDao.class,accountId).getBody();
    }

    public OrdersDao getPending() {
        HttpEntity<OrdersDao> entity = new HttpEntity<>(authHeaders);
        return restTemplate.exchange("https://api.mybitx.com/api/1/listorders", GET,entity, OrdersDao.class).getBody();
    }

    public OrdersDao getPending(Constants.State state, String pair) {
        HttpEntity<OrdersDao> entity = new HttpEntity<>(authHeaders);
        return restTemplate.exchange("https://api.mybitx.com/api/1/listorders?state={state}&pair={pair}"
                , GET,entity, OrdersDao.class,state.toString(),pair).getBody();
    }

    public OrdersDao listOrders(Constants.State state, String pair) {
        HttpEntity<OrdersDao> entity = new HttpEntity<>(authHeaders);
        return restTemplate.exchange("https://api.mybitx.com/api/1/listorders?state={state}&pair={pair}"
                , GET,entity, OrdersDao.class,state.toString(),pair).getBody();
    }

    public CreateLimitOrderResponse createLimitOrder(CreateLimitOrderRequest request) {
        HttpEntity<CreateLimitOrderResponse> entity = new HttpEntity<>(authHeaders);
        return restTemplate.exchange("https://api.mybitx.com/api/1/postorder"
                , POST,new HttpEntity<>(authHeaders), CreateLimitOrderResponse.class,request).getBody();
    }

    public CreateMarketOrderResponse createMarketOrder(CreateMarketOrderRequest request) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/marketorder"
                , POST,new HttpEntity<>(authHeaders), CreateMarketOrderResponse.class,request).getBody();
    }

    public StopOrderResponse stopOrder(String orderId) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/stoporder"
                , POST,new HttpEntity<>(authHeaders), StopOrderResponse.class,new StopOrderRequest(orderId)).getBody();
    }

    public GetOrderResponse getOrder(String orderId) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/orders/{id}", GET
                ,new HttpEntity<>(authHeaders), GetOrderResponse.class,orderId).getBody();
    }

    public ListTradesDao listTrades(String pair,Timestamp since,Integer limit) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/listtrades?pair={pair}&since={since}&limit={limit}"
                , GET,new HttpEntity<>(authHeaders), ListTradesDao.class,pair).getBody();
    }

    public ListTradesDao listTrades(String pair) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/listtrades?pair={pair}"
                , GET,new HttpEntity<>(authHeaders), ListTradesDao.class,pair).getBody();
    }

    public FeeInfoDao feeInfo(String pair) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/fee_info?pair={pair}"
                , GET,new HttpEntity<>(authHeaders), FeeInfoDao.class,pair).getBody();
    }


    public ReceiveAddressDao getReceiveAddresses(String asset, String address) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/funding_address?asset={asset}&address={address}"
                , GET,new HttpEntity<>(authHeaders), ReceiveAddressDao.class,asset,address).getBody();
    }

    public CreateAddressResponse createReceiveAddresses(String asset) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/funding_address"
                , POST,new HttpEntity<>(authHeaders), CreateAddressResponse.class,asset,new CreateAddressRequest(asset)).getBody();
    }

    public CreateWithdrawalResponseDao createWithdrawalRequest(CreateWithdrawalRequestDao request) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/withdrawals"
                , POST,new HttpEntity<>(authHeaders), CreateWithdrawalResponseDao.class,request).getBody();
    }

    public WithdrawalDao getCreateWithdrawalRequestStatus(String id) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/withdrawals/{id}", GET
                ,new HttpEntity<>(authHeaders), WithdrawalDao.class).getBody();
    }

    public WithdrawalDao cancelWithdrawalRequestStatus(String id) {
        return restTemplate.exchange("https://api.mybitx.com/api/1/withdrawals/{id}", DELETE
                ,new HttpEntity<>(authHeaders), WithdrawalDao.class).getBody();
    }


    public static void main(String args[]) throws Exception {
        BalancesDao balancesDao = new LunoDaoService().init().getBalances();
        System.out.println(balancesDao);
    }

}
