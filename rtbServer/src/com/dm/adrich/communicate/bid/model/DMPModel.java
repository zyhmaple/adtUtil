/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid.model;

public class DMPModel {
    private String userID = null;
    private String orderID = null;
    private int isPC = 0;
    private String osType = null;
    private String sspCode = null;
	private String token = null;
	
    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getIsPC() {
        return this.isPC;
    }

    public void setIsPC(int isPC) {
        this.isPC = isPC;
    }

    public String getOsType() {
        return this.osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getSspCode() {
        return this.sspCode;
    }

    public void setSspCode(String sspCode) {
        this.sspCode = sspCode;
    }
    

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

