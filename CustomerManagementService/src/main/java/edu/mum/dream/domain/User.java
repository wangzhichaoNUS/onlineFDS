package edu.mum.dream.domain;

public class User {
    private String _id;
    private String id;
    private String userName;
    private String userPass;
    private Integer userLevel;
    private String userAddress;
    private String userPhone;
    private String userAvatar;

    public User() {
        // 默认构造函数
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

//    public User(String userName, String userPass, String userAvatar) {
//        this.userName = userName;
//        this.userPass = userPass;
//        this.userAvatar = userAvatar;
//    }
//
//    public User(String userName, String userPass) {
//        this.userName = userName;
//        this.userPass = userPass;
//    }
}

