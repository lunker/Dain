package dev.dain;

import java.io.Serializable;

/**
 * Created by davidha on 2015. 3. 12..
 */
public class FacebookFriends implements Serializable {
    String Friend_ID;
    String Friend_NAME;
    public FacebookFriends()
    {
        Friend_ID=null;
        Friend_NAME=null;
    }

    public String getFriend_ID() {
        return Friend_ID;
    }

    public void setFriend_ID(String friend_ID) {
        Friend_ID = friend_ID;
    }

    public String getFriend_NAME() {
        return Friend_NAME;
    }

    public void setFriend_NAME(String friend_NAME) {
        Friend_NAME = friend_NAME;
    }
}
