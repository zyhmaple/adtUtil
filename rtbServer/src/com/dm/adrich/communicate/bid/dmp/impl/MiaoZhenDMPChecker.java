package com.dm.adrich.communicate.bid.dmp.impl;

import com.dm.adrich.communicate.bid.dmp.DMPChecker;
import com.dm.adrich.communicate.bid.model.DMPModel;
import com.dm.adrich.communicate.bid.util.CacheUtil;
import com.dm.adrich.communicate.bid.util.MiaoZhenDMPUtil;
import java.util.List;

public class MiaoZhenDMPChecker
  implements DMPChecker
{
  public static final String mzTagIDPCKey = "MZTagIDPCKey";
  public static final String mzTagIDMBKey = "MZTagIDMBKey";

  public boolean checkDmpTags(DMPModel dmpModel)
    throws Exception
  {
    return false;
  }

  public List getDmpTags(DMPModel dmpModel)
    throws Exception
  {
    String token = dmpModel.getToken();
    String tagIDStr = CacheUtil.INSTANCE.getEhcache("MZTagIDMBKey", token);
    String userID = dmpModel.getUserID();
    if (dmpModel.getIsPC() == 1)
    {
      tagIDStr = CacheUtil.INSTANCE.getEhcache("MZTagIDPCKey", token);
    }
    else if (dmpModel.getIsPC() == 2)
    {
      userID = userID.toUpperCase();
    }
    else if (dmpModel.getIsPC() == 3)
    {
      userID = userID.toUpperCase();
    }

    List mzPCUserTag = MiaoZhenDMPUtil.getUserTag(tagIDStr, userID, token);
    return mzPCUserTag;
  }
}