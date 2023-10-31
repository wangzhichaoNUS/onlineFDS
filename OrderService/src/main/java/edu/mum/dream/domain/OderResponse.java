package edu.mum.dream.domain;

import java.util.ArrayList;
import java.util.List;

public class OderResponse {
    List<Order> alloders;
    List<Order> waitpayoders;
    List<Order> waitcommentoders;
    List<Order> afteroders;

    public OderResponse(){

    }

    public OderResponse(List<Order> orderByUserID, List<Order> orders, List<Order> orders1, List<Order> orders2) {
        this.alloders = orderByUserID;
        waitpayoders = orders;
        waitcommentoders = orders1;
        afteroders = orders2;
    }

    public void setAlloders(List<Order> t){
        this.alloders = t;
    }

    public List<Order> getAlloders(List<Order> t) {return this.alloders;}
    public List<Order> getWaitpayoders(List<Order> t) {return this.waitpayoders;}
    public List<Order> getWaitcommentoders(List<Order> t) {return this.waitcommentoders;}
    public List<Order> getAfteroders(List<Order> t) {return this.afteroders;}

    public void setWaitpayoders(List<Order> t){
        this.alloders = t;
    }
    public void setWaitcommentoders(List<Order> t){
        this.alloders = t;
    }
    public void setAfteroders(List<Order> t){
        this.alloders = t;
    }
}
