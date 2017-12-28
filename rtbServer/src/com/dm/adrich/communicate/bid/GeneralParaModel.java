/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  IceInternal.BasicStream
 *  IceInternal.HashUtil
 */
package com.dm.adrich.communicate.bid;

import IceInternal.BasicStream;
import IceInternal.HashUtil;
import com.dm.adrich.communicate.bid.SSPCreativeTypeListHelper;
import com.dm.adrich.communicate.bid.UserAttributeListHelper;
import java.io.Serializable;
import java.util.List;

public class GeneralParaModel
implements Cloneable,
Serializable {
    public long minDuration;
    public long maxDuration;
    public String maSize;
    public String ip;
    public String impAdID;
    public String isBanner;
    public String isApp;
    public long startTime;
    public String siteName;
    public String sspCode;
    public String requestID;
    public int miniPrice;
    public String displayType;
    public String pageVertical;
    public String userID;
    public String deviceType;
    public String osType;
    public String netType;
    public long lat;
    public long lon;
    public int buyWay;
    public String appID;
    public String text;
	public String isWap;
    public List<String> sspCreativeTypeList;
    public List<String> userAttributeList;
    private static final GeneralParaModel __nullMarshalValue = new GeneralParaModel();
    public static final long serialVersionUID = 848920319;

    public GeneralParaModel() {
        this.maSize = "";
        this.ip = "";
        this.impAdID = "";
        this.isBanner = "";
        this.isApp = "";
        this.siteName = "";
        this.sspCode = "";
        this.requestID = "";
        this.displayType = "";
        this.pageVertical = "";
        this.userID = "";
        this.deviceType = "";
        this.osType = "";
        this.netType = "";
        this.appID = "";
        this.text = "";
        this.isWap = "";
    }

    public GeneralParaModel(long minDuration, long maxDuration, String maSize, String ip, String impAdID, String isBanner, String isApp, long startTime, String siteName, String sspCode, String requestID, int miniPrice, String displayType, String pageVertical, String userID, String deviceType, String osType, String netType, long lat, long lon, int buyWay, String appID, String text,String isWap, List<String> sspCreativeTypeList, List<String> userAttributeList) {
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.maSize = maSize;
        this.ip = ip;
        this.impAdID = impAdID;
        this.isBanner = isBanner;
        this.isApp = isApp;
        this.startTime = startTime;
        this.siteName = siteName;
        this.sspCode = sspCode;
        this.requestID = requestID;
        this.miniPrice = miniPrice;
        this.displayType = displayType;
        this.pageVertical = pageVertical;
        this.userID = userID;
        this.deviceType = deviceType;
        this.osType = osType;
        this.netType = netType;
        this.lat = lat;
        this.lon = lon;
        this.buyWay = buyWay;
        this.appID = appID;
        this.text = text;
		this.text = text;
		this.isWap = isWap;
        this.sspCreativeTypeList = sspCreativeTypeList;
        this.userAttributeList = userAttributeList;
    }

    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        GeneralParaModel _r = null;
        if (rhs instanceof GeneralParaModel) {
            _r = (GeneralParaModel)rhs;
        }
        if (_r != null) {
            if (this.minDuration != _r.minDuration) {
                return false;
            }
            if (this.maxDuration != _r.maxDuration) {
                return false;
            }
            if (!(this.maSize == _r.maSize || this.maSize != null && _r.maSize != null && this.maSize.equals(_r.maSize))) {
                return false;
            }
            if (!(this.ip == _r.ip || this.ip != null && _r.ip != null && this.ip.equals(_r.ip))) {
                return false;
            }
            if (!(this.impAdID == _r.impAdID || this.impAdID != null && _r.impAdID != null && this.impAdID.equals(_r.impAdID))) {
                return false;
            }
            if (!(this.isBanner == _r.isBanner || this.isBanner != null && _r.isBanner != null && this.isBanner.equals(_r.isBanner))) {
                return false;
            }
            if (!(this.isApp == _r.isApp || this.isApp != null && _r.isApp != null && this.isApp.equals(_r.isApp))) {
                return false;
            }
            if (this.startTime != _r.startTime) {
                return false;
            }
            if (!(this.siteName == _r.siteName || this.siteName != null && _r.siteName != null && this.siteName.equals(_r.siteName))) {
                return false;
            }
            if (!(this.sspCode == _r.sspCode || this.sspCode != null && _r.sspCode != null && this.sspCode.equals(_r.sspCode))) {
                return false;
            }
            if (!(this.requestID == _r.requestID || this.requestID != null && _r.requestID != null && this.requestID.equals(_r.requestID))) {
                return false;
            }
            if (this.miniPrice != _r.miniPrice) {
                return false;
            }
            if (!(this.displayType == _r.displayType || this.displayType != null && _r.displayType != null && this.displayType.equals(_r.displayType))) {
                return false;
            }
            if (!(this.pageVertical == _r.pageVertical || this.pageVertical != null && _r.pageVertical != null && this.pageVertical.equals(_r.pageVertical))) {
                return false;
            }
            if (!(this.userID == _r.userID || this.userID != null && _r.userID != null && this.userID.equals(_r.userID))) {
                return false;
            }
            if (!(this.deviceType == _r.deviceType || this.deviceType != null && _r.deviceType != null && this.deviceType.equals(_r.deviceType))) {
                return false;
            }
            if (!(this.osType == _r.osType || this.osType != null && _r.osType != null && this.osType.equals(_r.osType))) {
                return false;
            }
            if (!(this.netType == _r.netType || this.netType != null && _r.netType != null && this.netType.equals(_r.netType))) {
                return false;
            }
            if (this.lat != _r.lat) {
                return false;
            }
            if (this.lon != _r.lon) {
                return false;
            }
            if (this.buyWay != _r.buyWay) {
                return false;
            }
            if (!(this.appID == _r.appID || this.appID != null && _r.appID != null && this.appID.equals(_r.appID))) {
                return false;
            }
            if (!(this.text == _r.text || this.text != null && _r.text != null && this.text.equals(_r.text))) {
                return false;
            }
			if (isWap != _r.isWap && (isWap == null || _r.isWap == null || !isWap.equals(_r.isWap)))
				return false;
			
            if (!(this.sspCreativeTypeList == _r.sspCreativeTypeList || this.sspCreativeTypeList != null && _r.sspCreativeTypeList != null && this.sspCreativeTypeList.equals(_r.sspCreativeTypeList))) {
                return false;
            }
            if (!(this.userAttributeList == _r.userAttributeList || this.userAttributeList != null && _r.userAttributeList != null && this.userAttributeList.equals(_r.userAttributeList))) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int __h = 5381;
        __h = HashUtil.hashAdd((int)__h, (Object)"::com::dm::adrich::communicate::bid::GeneralParaModel");
        __h = HashUtil.hashAdd((int)__h, (long)this.minDuration);
        __h = HashUtil.hashAdd((int)__h, (long)this.maxDuration);
        __h = HashUtil.hashAdd((int)__h, (Object)this.maSize);
        __h = HashUtil.hashAdd((int)__h, (Object)this.ip);
        __h = HashUtil.hashAdd((int)__h, (Object)this.impAdID);
        __h = HashUtil.hashAdd((int)__h, (Object)this.isBanner);
        __h = HashUtil.hashAdd((int)__h, (Object)this.isApp);
        __h = HashUtil.hashAdd((int)__h, (long)this.startTime);
        __h = HashUtil.hashAdd((int)__h, (Object)this.siteName);
        __h = HashUtil.hashAdd((int)__h, (Object)this.sspCode);
        __h = HashUtil.hashAdd((int)__h, (Object)this.requestID);
        __h = HashUtil.hashAdd((int)__h, (int)this.miniPrice);
        __h = HashUtil.hashAdd((int)__h, (Object)this.displayType);
        __h = HashUtil.hashAdd((int)__h, (Object)this.pageVertical);
        __h = HashUtil.hashAdd((int)__h, (Object)this.userID);
        __h = HashUtil.hashAdd((int)__h, (Object)this.deviceType);
        __h = HashUtil.hashAdd((int)__h, (Object)this.osType);
        __h = HashUtil.hashAdd((int)__h, (Object)this.netType);
        __h = HashUtil.hashAdd((int)__h, (long)this.lat);
        __h = HashUtil.hashAdd((int)__h, (long)this.lon);
        __h = HashUtil.hashAdd((int)__h, (int)this.buyWay);
        __h = HashUtil.hashAdd((int)__h, (Object)this.appID);
        __h = HashUtil.hashAdd((int)__h, (Object)this.text);
		__h = HashUtil.hashAdd(__h, isWap);
        __h = HashUtil.hashAdd((int)__h, this.sspCreativeTypeList);
        __h = HashUtil.hashAdd((int)__h, this.userAttributeList);
        return __h;
    }

    public GeneralParaModel clone() {
        GeneralParaModel c = null;
            try {
                c = (GeneralParaModel)super.clone();
            }
            catch (CloneNotSupportedException ex) {
            	assert false;
            }

        return c;
    }

    public void __write(BasicStream __os) {
        __os.writeLong(this.minDuration);
        __os.writeLong(this.maxDuration);
        __os.writeString(this.maSize);
        __os.writeString(this.ip);
        __os.writeString(this.impAdID);
        __os.writeString(this.isBanner);
        __os.writeString(this.isApp);
        __os.writeLong(this.startTime);
        __os.writeString(this.siteName);
        __os.writeString(this.sspCode);
        __os.writeString(this.requestID);
        __os.writeInt(this.miniPrice);
        __os.writeString(this.displayType);
        __os.writeString(this.pageVertical);
        __os.writeString(this.userID);
        __os.writeString(this.deviceType);
        __os.writeString(this.osType);
        __os.writeString(this.netType);
        __os.writeLong(this.lat);
        __os.writeLong(this.lon);
        __os.writeInt(this.buyWay);
        __os.writeString(this.appID);
        __os.writeString(this.text);
		__os.writeString(this.isWap);
        SSPCreativeTypeListHelper.write(__os, this.sspCreativeTypeList);
        UserAttributeListHelper.write(__os, this.userAttributeList);
    }

    public void __read(BasicStream __is) {
        this.minDuration = __is.readLong();
        this.maxDuration = __is.readLong();
        this.maSize = __is.readString();
        this.ip = __is.readString();
        this.impAdID = __is.readString();
        this.isBanner = __is.readString();
        this.isApp = __is.readString();
        this.startTime = __is.readLong();
        this.siteName = __is.readString();
        this.sspCode = __is.readString();
        this.requestID = __is.readString();
        this.miniPrice = __is.readInt();
        this.displayType = __is.readString();
        this.pageVertical = __is.readString();
        this.userID = __is.readString();
        this.deviceType = __is.readString();
        this.osType = __is.readString();
        this.netType = __is.readString();
        this.lat = __is.readLong();
        this.lon = __is.readLong();
        this.buyWay = __is.readInt();
        this.appID = __is.readString();
        this.text = __is.readString();
        this.isWap = __is.readString();
        this.sspCreativeTypeList = SSPCreativeTypeListHelper.read(__is);
        this.userAttributeList = UserAttributeListHelper.read(__is);
    }

    public static void __write(BasicStream __os, GeneralParaModel __v) {
        if (__v == null) {
            __nullMarshalValue.__write(__os);
        } else {
            __v.__write(__os);
        }
    }

    public static GeneralParaModel __read(BasicStream __is, GeneralParaModel __v) {
        if (__v == null) {
            __v = new GeneralParaModel();
        }
        __v.__read(__is);
        return __v;
    }
}

