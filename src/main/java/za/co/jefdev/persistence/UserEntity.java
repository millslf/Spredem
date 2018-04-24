package za.co.jefdev.persistence;

import za.co.jefdev.services.*;
import za.co.jefdev.utils.FileReaderWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserEntity implements Serializable{

    private List<User> userList = new ArrayList<>();

    private static Class[] FULL_LIST = {BTCBuyRUBSellZAR.class, BTCBuyUSDSellZAR.class, BTCBuyGBPSellZAR.class, BTCBuyEURSellZAR.class,
            BTCBuyZARSellUSD.class, ETHBuyEURSellZAR.class};

    private static Class[] BTC_ONLY_LIST = {BTCBuyUSDSellZAR.class, BTCBuyGBPSellZAR.class, BTCBuyEURSellZAR.class,
                                            BTCBuyZARSellUSD.class};

    public UserEntity() throws IOException, ClassNotFoundException {
        userList.add(new User("millslf@gmail.com", FULL_LIST));
        userList.add(new User("ettienneleroux@gmail.com", BTC_ONLY_LIST));
        userList.add(new User("millsgeo@gmail.com", FULL_LIST));
        userList.add(new User("heindrich_leroux@yahoo.com", BTC_ONLY_LIST));
        userList.add(new User("jaspervdbijl@gmail.com", BTC_ONLY_LIST));
        FileReaderWriter.persistEntities(this);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public class User implements Serializable{

        String emailAddress;
        Class[] subscriptions;

        public User(String emailAddress, Class... subscriptions) {
            this.emailAddress = emailAddress;
            this.subscriptions = subscriptions;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public Class[] getSubscriptions() {
            return subscriptions;
        }

    }
}
