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
import java.io.Serializable;

public class GeneralResponseModel implements Cloneable, Serializable {
	public String planID;
	public String exposeCheck;
	public String clickCheck;
	public String reachCheck;
	public String price;
	public String maPath;
	public String timeLong;
	public String creativeID;
	public String targetURL;
	public String type;
	public String category;
	public String para;
	public String advertiserID;
	public String landPage;
	public int openType;
	public String maTitle;
	public String maDescribe;
	public String advertSlogan;
	public String wordLinkTitle;
	public String wordLinkURL;
	public String iconType;
	public String iconURL;
	public String appName;
	public String packageName;
	public String appPhone;
	public String packageSize;
	public String appIntroduce;
	public String sspCreativeID;
	public String sspMaPath;
	public String text;
	public String ckmpUrl;
	private static final GeneralResponseModel __nullMarshalValue = new GeneralResponseModel();
	public static final long serialVersionUID = 2061467258;

	public GeneralResponseModel() {
		this.planID = "";
		this.exposeCheck = "";
		this.clickCheck = "";
		this.reachCheck = "";
		this.price = "";
		this.maPath = "";
		this.timeLong = "";
		this.creativeID = "";
		this.targetURL = "";
		this.type = "";
		this.category = "";
		this.para = "";
		this.advertiserID = "";
		this.landPage = "";
		this.maTitle = "";
		this.maDescribe = "";
		this.advertSlogan = "";
		this.wordLinkTitle = "";
		this.wordLinkURL = "";
		this.iconType = "";
		this.iconURL = "";
		this.appName = "";
		this.packageName = "";
		this.appPhone = "";
		this.packageSize = "";
		this.appIntroduce = "";
		this.sspCreativeID = "";
		this.sspMaPath = "";
		this.text = "";
		this.ckmpUrl = "";
	}

	public GeneralResponseModel(String planID, String exposeCheck, String clickCheck, String reachCheck, String price,
			String maPath, String timeLong, String creativeID, String targetURL, String type, String category,
			String para, String advertiserID, String landPage, int openType, String maTitle, String maDescribe,
			String advertSlogan, String wordLinkTitle, String wordLinkURL, String iconType, String iconURL,
			String appName, String packageName, String appPhone, String packageSize, String appIntroduce,
			String sspCreativeID, String sspMaPath, String text, String ckmpUrl) {
		this.planID = planID;
		this.exposeCheck = exposeCheck;
		this.clickCheck = clickCheck;
		this.reachCheck = reachCheck;
		this.price = price;
		this.maPath = maPath;
		this.timeLong = timeLong;
		this.creativeID = creativeID;
		this.targetURL = targetURL;
		this.type = type;
		this.category = category;
		this.para = para;
		this.advertiserID = advertiserID;
		this.landPage = landPage;
		this.openType = openType;
		this.maTitle = maTitle;
		this.maDescribe = maDescribe;
		this.advertSlogan = advertSlogan;
		this.wordLinkTitle = wordLinkTitle;
		this.wordLinkURL = wordLinkURL;
		this.iconType = iconType;
		this.iconURL = iconURL;
		this.appName = appName;
		this.packageName = packageName;
		this.appPhone = appPhone;
		this.packageSize = packageSize;
		this.appIntroduce = appIntroduce;
		this.sspCreativeID = sspCreativeID;
		this.sspMaPath = sspMaPath;
		this.text = text;
		this.ckmpUrl = ckmpUrl;
	}

	public boolean equals(Object rhs) {
		if (this == rhs) {
			return true;
		}
		GeneralResponseModel _r = null;
		if (rhs instanceof GeneralResponseModel) {
			_r = (GeneralResponseModel) rhs;
		}
		if (_r != null) {
			if (!(this.planID == _r.planID
					|| this.planID != null && _r.planID != null && this.planID.equals(_r.planID))) {
				return false;
			}
			if (!(this.exposeCheck == _r.exposeCheck
					|| this.exposeCheck != null && _r.exposeCheck != null && this.exposeCheck.equals(_r.exposeCheck))) {
				return false;
			}
			if (!(this.clickCheck == _r.clickCheck
					|| this.clickCheck != null && _r.clickCheck != null && this.clickCheck.equals(_r.clickCheck))) {
				return false;
			}
			if (!(this.reachCheck == _r.reachCheck
					|| this.reachCheck != null && _r.reachCheck != null && this.reachCheck.equals(_r.reachCheck))) {
				return false;
			}
			if (!(this.price == _r.price || this.price != null && _r.price != null && this.price.equals(_r.price))) {
				return false;
			}
			if (!(this.maPath == _r.maPath
					|| this.maPath != null && _r.maPath != null && this.maPath.equals(_r.maPath))) {
				return false;
			}
			if (!(this.timeLong == _r.timeLong
					|| this.timeLong != null && _r.timeLong != null && this.timeLong.equals(_r.timeLong))) {
				return false;
			}
			if (!(this.creativeID == _r.creativeID
					|| this.creativeID != null && _r.creativeID != null && this.creativeID.equals(_r.creativeID))) {
				return false;
			}
			if (!(this.targetURL == _r.targetURL
					|| this.targetURL != null && _r.targetURL != null && this.targetURL.equals(_r.targetURL))) {
				return false;
			}
			if (!(this.type == _r.type || this.type != null && _r.type != null && this.type.equals(_r.type))) {
				return false;
			}
			if (!(this.category == _r.category
					|| this.category != null && _r.category != null && this.category.equals(_r.category))) {
				return false;
			}
			if (!(this.para == _r.para || this.para != null && _r.para != null && this.para.equals(_r.para))) {
				return false;
			}
			if (!(this.advertiserID == _r.advertiserID || this.advertiserID != null && _r.advertiserID != null
					&& this.advertiserID.equals(_r.advertiserID))) {
				return false;
			}
			if (!(this.landPage == _r.landPage
					|| this.landPage != null && _r.landPage != null && this.landPage.equals(_r.landPage))) {
				return false;
			}
			if (this.openType != _r.openType) {
				return false;
			}
			if (!(this.maTitle == _r.maTitle
					|| this.maTitle != null && _r.maTitle != null && this.maTitle.equals(_r.maTitle))) {
				return false;
			}
			if (!(this.maDescribe == _r.maDescribe
					|| this.maDescribe != null && _r.maDescribe != null && this.maDescribe.equals(_r.maDescribe))) {
				return false;
			}
			if (!(this.advertSlogan == _r.advertSlogan || this.advertSlogan != null && _r.advertSlogan != null
					&& this.advertSlogan.equals(_r.advertSlogan))) {
				return false;
			}
			if (!(this.wordLinkTitle == _r.wordLinkTitle || this.wordLinkTitle != null && _r.wordLinkTitle != null
					&& this.wordLinkTitle.equals(_r.wordLinkTitle))) {
				return false;
			}
			if (!(this.wordLinkURL == _r.wordLinkURL
					|| this.wordLinkURL != null && _r.wordLinkURL != null && this.wordLinkURL.equals(_r.wordLinkURL))) {
				return false;
			}
			if (!(this.iconType == _r.iconType
					|| this.iconType != null && _r.iconType != null && this.iconType.equals(_r.iconType))) {
				return false;
			}
			if (!(this.iconURL == _r.iconURL
					|| this.iconURL != null && _r.iconURL != null && this.iconURL.equals(_r.iconURL))) {
				return false;
			}
			if (!(this.appName == _r.appName
					|| this.appName != null && _r.appName != null && this.appName.equals(_r.appName))) {
				return false;
			}
			if (!(this.packageName == _r.packageName
					|| this.packageName != null && _r.packageName != null && this.packageName.equals(_r.packageName))) {
				return false;
			}
			if (!(this.appPhone == _r.appPhone
					|| this.appPhone != null && _r.appPhone != null && this.appPhone.equals(_r.appPhone))) {
				return false;
			}
			if (!(this.packageSize == _r.packageSize
					|| this.packageSize != null && _r.packageSize != null && this.packageSize.equals(_r.packageSize))) {
				return false;
			}
			if (!(this.appIntroduce == _r.appIntroduce || this.appIntroduce != null && _r.appIntroduce != null
					&& this.appIntroduce.equals(_r.appIntroduce))) {
				return false;
			}
			if (!(this.sspCreativeID == _r.sspCreativeID || this.sspCreativeID != null && _r.sspCreativeID != null
					&& this.sspCreativeID.equals(_r.sspCreativeID))) {
				return false;
			}
			if (!(this.sspMaPath == _r.sspMaPath
					|| this.sspMaPath != null && _r.sspMaPath != null && this.sspMaPath.equals(_r.sspMaPath))) {
				return false;
			}
			if (!(this.text == _r.text || this.text != null && _r.text != null && this.text.equals(_r.text))) {
				return false;
			}
			return ckmpUrl == _r.ckmpUrl || ckmpUrl != null && _r.ckmpUrl != null && ckmpUrl.equals(_r.ckmpUrl);
		}
		return false;
	}

	public int hashCode() {
		int __h = 5381;
		__h = HashUtil.hashAdd((int) __h, (Object) "::com::dm::adrich::communicate::bid::GeneralResponseModel");
		__h = HashUtil.hashAdd((int) __h, (Object) this.planID);
		__h = HashUtil.hashAdd((int) __h, (Object) this.exposeCheck);
		__h = HashUtil.hashAdd((int) __h, (Object) this.clickCheck);
		__h = HashUtil.hashAdd((int) __h, (Object) this.reachCheck);
		__h = HashUtil.hashAdd((int) __h, (Object) this.price);
		__h = HashUtil.hashAdd((int) __h, (Object) this.maPath);
		__h = HashUtil.hashAdd((int) __h, (Object) this.timeLong);
		__h = HashUtil.hashAdd((int) __h, (Object) this.creativeID);
		__h = HashUtil.hashAdd((int) __h, (Object) this.targetURL);
		__h = HashUtil.hashAdd((int) __h, (Object) this.type);
		__h = HashUtil.hashAdd((int) __h, (Object) this.category);
		__h = HashUtil.hashAdd((int) __h, (Object) this.para);
		__h = HashUtil.hashAdd((int) __h, (Object) this.advertiserID);
		__h = HashUtil.hashAdd((int) __h, (Object) this.landPage);
		__h = HashUtil.hashAdd((int) __h, (int) this.openType);
		__h = HashUtil.hashAdd((int) __h, (Object) this.maTitle);
		__h = HashUtil.hashAdd((int) __h, (Object) this.maDescribe);
		__h = HashUtil.hashAdd((int) __h, (Object) this.advertSlogan);
		__h = HashUtil.hashAdd((int) __h, (Object) this.wordLinkTitle);
		__h = HashUtil.hashAdd((int) __h, (Object) this.wordLinkURL);
		__h = HashUtil.hashAdd((int) __h, (Object) this.iconType);
		__h = HashUtil.hashAdd((int) __h, (Object) this.iconURL);
		__h = HashUtil.hashAdd((int) __h, (Object) this.appName);
		__h = HashUtil.hashAdd((int) __h, (Object) this.packageName);
		__h = HashUtil.hashAdd((int) __h, (Object) this.appPhone);
		__h = HashUtil.hashAdd((int) __h, (Object) this.packageSize);
		__h = HashUtil.hashAdd((int) __h, (Object) this.appIntroduce);
		__h = HashUtil.hashAdd((int) __h, (Object) this.sspCreativeID);
		__h = HashUtil.hashAdd((int) __h, (Object) this.sspMaPath);
		__h = HashUtil.hashAdd((int) __h, (Object) this.text);
		__h = HashUtil.hashAdd(__h, ckmpUrl);
		return __h;
	}

	public GeneralResponseModel clone() {
		GeneralResponseModel c = null;
		try {
			c = (GeneralResponseModel) super.clone();
		} catch (CloneNotSupportedException ex) {
			assert false;
		}

		return c;
	}

	public void __write(BasicStream __os) {
		__os.writeString(this.planID);
		__os.writeString(this.exposeCheck);
		__os.writeString(this.clickCheck);
		__os.writeString(this.reachCheck);
		__os.writeString(this.price);
		__os.writeString(this.maPath);
		__os.writeString(this.timeLong);
		__os.writeString(this.creativeID);
		__os.writeString(this.targetURL);
		__os.writeString(this.type);
		__os.writeString(this.category);
		__os.writeString(this.para);
		__os.writeString(this.advertiserID);
		__os.writeString(this.landPage);
		__os.writeInt(this.openType);
		__os.writeString(this.maTitle);
		__os.writeString(this.maDescribe);
		__os.writeString(this.advertSlogan);
		__os.writeString(this.wordLinkTitle);
		__os.writeString(this.wordLinkURL);
		__os.writeString(this.iconType);
		__os.writeString(this.iconURL);
		__os.writeString(this.appName);
		__os.writeString(this.packageName);
		__os.writeString(this.appPhone);
		__os.writeString(this.packageSize);
		__os.writeString(this.appIntroduce);
		__os.writeString(this.sspCreativeID);
		__os.writeString(this.sspMaPath);
		__os.writeString(this.text);
		__os.writeString(ckmpUrl);
	}

	public void __read(BasicStream __is) {
		this.planID = __is.readString();
		this.exposeCheck = __is.readString();
		this.clickCheck = __is.readString();
		this.reachCheck = __is.readString();
		this.price = __is.readString();
		this.maPath = __is.readString();
		this.timeLong = __is.readString();
		this.creativeID = __is.readString();
		this.targetURL = __is.readString();
		this.type = __is.readString();
		this.category = __is.readString();
		this.para = __is.readString();
		this.advertiserID = __is.readString();
		this.landPage = __is.readString();
		this.openType = __is.readInt();
		this.maTitle = __is.readString();
		this.maDescribe = __is.readString();
		this.advertSlogan = __is.readString();
		this.wordLinkTitle = __is.readString();
		this.wordLinkURL = __is.readString();
		this.iconType = __is.readString();
		this.iconURL = __is.readString();
		this.appName = __is.readString();
		this.packageName = __is.readString();
		this.appPhone = __is.readString();
		this.packageSize = __is.readString();
		this.appIntroduce = __is.readString();
		this.sspCreativeID = __is.readString();
		this.sspMaPath = __is.readString();
		this.text = __is.readString();
		ckmpUrl = __is.readString();
	}

	public static void __write(BasicStream __os, GeneralResponseModel __v) {
		if (__v == null) {
			__nullMarshalValue.__write(__os);
		} else {
			__v.__write(__os);
		}
	}

	public static GeneralResponseModel __read(BasicStream __is, GeneralResponseModel __v) {
		if (__v == null) {
			__v = new GeneralResponseModel();
		}
		__v.__read(__is);
		return __v;
	}
}
