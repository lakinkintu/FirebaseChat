package com.chat.firebasechat.Constantvalue;


public class GroupMember {

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public GroupMember(String userName, String UID) {
        UserName = userName;
        this.UID = UID;
    }

    private String UserName;
    private String UID;

}
