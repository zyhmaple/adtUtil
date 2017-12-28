package com.dm.adrich.communicate.bid.pool;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.GeneralParseRequestServicePrx;
import com.dm.adrich.communicate.bid.GeneralResponseModel;

import Ice.Communicator;

public enum BidSendUtil
{
  INSTANCE;

  private GenericObjectPool<Communicator> gprsPool = null;
  private FileInputStream fis = null;

  public GenericObjectPool<Communicator> getBidPool() {
    if (this.gprsPool != null)
    {
      return this.gprsPool;
    }

    Properties pro = new Properties();
    try {
      pro.load(this.fis);
    } catch (Exception e) {
      e.printStackTrace();
    }

    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(pro.getProperty("ice_blockWhenExhausted")));
    poolConfig.setMaxWaitMillis(Long.parseLong(pro.getProperty("ice_maxWait")));
    poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("ice_minIdle")));
    poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("ice_maxIdle")));
    poolConfig.setMaxTotal(Integer.parseInt(pro.getProperty("ice_maxTotal")));
    poolConfig.setTestOnBorrow(Boolean.parseBoolean(pro.getProperty("ice_testOnBorrow")));
    poolConfig.setTestOnReturn(Boolean.parseBoolean(pro.getProperty("ice_testOnReturn")));
    poolConfig.setTestOnCreate(true);
    poolConfig.setTestWhileIdle(Boolean.parseBoolean(pro.getProperty("ice_testWhileIdle")));
    poolConfig.setLifo(Boolean.parseBoolean(pro.getProperty("ice_lifo")));

    String serverIP = pro.getProperty("ice_conf_file");
    serverIP = "--Ice.Config=" + serverIP;
    System.out.println("serverIP = " + serverIP);
    this.gprsPool = 
      new GenericObjectPool(new IceConnectionFactory(serverIP), poolConfig);
    return this.gprsPool;
  }

  private Communicator borrowObject()
    throws Exception
  {
    return (Communicator)getBidPool().borrowObject();
  }
  private void returnObject(Communicator comm) {
    getBidPool().returnObject(comm);
  }

  public GeneralResponseModel sendRequest(GeneralParaModel gpm, int isPC) throws Exception
  {
    Communicator comm = borrowObject();
    GeneralParseRequestServicePrx gprsp = (GeneralParseRequestServicePrx)PoolPersistData.obj2PrxMap.get(comm);
    GeneralResponseModel grm = gprsp.generalDealBidRequest(gpm, isPC);
    returnObject(comm);
    return grm;
  }

  public void destroyObject() throws Exception {
    getBidPool().close();
    System.out.println(getBidPool().getDestroyedCount());
  }

  public FileInputStream getFis() {
    return this.fis;
  }

  public void setFis(FileInputStream fis) {
    this.fis = fis;
  }
}