package com.dm.adrich.communicate.bid.util;

import com.dm.adrich.netty.util.NettyPoolUtil;
import com.drunkmoon.xinhuanet.redis.impl.RedisGlobalConfig;
import com.drunkmoon.xinhuanet.redis.impl.XinHuaJedisPool;
import net.sf.ehcache.CacheManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SystemInit {

    INSTANCE;

    protected static final Logger log;
    private XinHuaJedisPool xinHuaJedisPool = null;
    private CacheManager cacheManager = null;

    static {
        log = Logger.getLogger(InvestJedisUtil.class);
    }



    public void contextDestroyed() {
        if (this.xinHuaJedisPool != null) {
            this.xinHuaJedisPool.xinHuaJedisPoolDistroy();
            System.out.println("redis pool distroy #################");
        } else {
            System.out.println("no redis pool exist ##################");
        }
        if (KafkaUtil.INSTANCE.getLogProducer() != null) {
            KafkaUtil.INSTANCE.close();
            System.out.println("logSendPool pool distroy #################");
        } else {
            System.out.println("no logSendPool pool exist ##################");
        }
        if (this.cacheManager != null) {
            this.cacheManager.shutdown();
            System.out.println("ehcache distroy #################");
        } else {
            System.out.println("no ehcache exist ##################");
        }
    }

    public void contextInitialized(String confFilePath) {

        InputStream sysInput = null;
        InputStream sspInput = null;
        InputStream kafkaInput = null;
        InputStream ehcacheInput = null;
        InputStream personTagInput = null;
        InputStream pageTagInput = null;
        InputStream displayTypeInput = null;
        InputStream osTypeInput = null;
        InputStream netTypeInput = null;
        InputStream deviceTypeInput = null;
        InputStream openTypeInput = null;
        InputStream creativeTypeInput = null;
        FileInputStream sspCreativeTypeInput = null;
        InputStream advertIndustryInput = null;

        try {
            //初始化sys.properties
            sysInput = new FileInputStream(confFilePath);
            SysParams.sysProps.load(sysInput);
            //初始化redis配置文件
            String confPath = SysParams.sysProps.getProperty("redis.confFilePath");
            log.info((new StringBuilder("confPath = ")).append(confPath).toString());
            RedisGlobalConfig.init(confPath);

            JedisUtil.INSTANCE.getJedis();
            sspInput = new FileInputStream(SysParams.sysProps.getProperty("ssp.confFilePath"));
            SysParams.sspProps.load(sspInput);
            xinHuaJedisPool = XinHuaJedisPool.getInstance();
            String investPoolConfPath = SysParams.sysProps.getProperty("netty.investPool.confFilePath");
            log.info((new StringBuilder("investPoolConfPath = ")).append(investPoolConfPath).toString());
            String investPoolName = SysParams.sysProps.getProperty("netty.investPoolName");
            NettyPoolUtil.getInstance().createPool(investPoolName, investPoolConfPath);
            String dmpPoolConfPath = SysParams.sysProps.getProperty("netty.dmpPool.confFilePath");
            log.info((new StringBuilder("dmpPoolConfPath = ")).append(dmpPoolConfPath).toString());
            String dmpPoolName = SysParams.sysProps.getProperty("netty.dmpPoolName");
            NettyPoolUtil.getInstance().createPool(dmpPoolName, dmpPoolConfPath);
            if ("yes".equals(SysParams.sysProps.getProperty("loadKafka"))) {
                kafkaInput = new FileInputStream(SysParams.sysProps.getProperty("kafka.confFilePath"));
                Properties kafkaProps = new Properties();
                kafkaProps.load(kafkaInput);
                kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
                kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
                KafkaUtil.INSTANCE.setKafkaProps(kafkaProps);
                Constant.kafkaProps = kafkaProps;
                org.apache.kafka.clients.producer.KafkaProducer kafkap = KafkaUtil.INSTANCE.getLogProducer();
                log.info((new StringBuilder("kafkap = ")).append(kafkap).toString());
            }
            if ("yes".equals(SysParams.sysProps.getProperty("isTest")))
                Constant.dealTimeOut = 3600000;
            log.info((new StringBuilder("dealTimeOut = ")).append(Constant.dealTimeOut).toString());
            personTagInput = new FileInputStream(SysParams.sysProps.getProperty("personTag.confFilePath"));
            SysParams.personTagProps.load(personTagInput);
            advertIndustryInput = new FileInputStream(SysParams.sysProps.getProperty("advertIndustry.confFilePath"));
            SysParams.advertIndustryProps.load(advertIndustryInput);
            IPLoadUtil.loadIPDataBase();
            ehcacheInput = new FileInputStream(SysParams.sysProps.getProperty("ehcache.confFilePath"));
            cacheManager = CacheManager.create(ehcacheInput);
            EhcacheUtil.INSTANCE.setCacheManager(cacheManager);
            SysParams.sspMidMap.put("_010", "1114");
            SysParams.sspMidMap.put("_011", "1238");
            SysParams.sspMidMap.put("_007", "2751");
            SysParams.sspMidMap.put("_032", "1646");
            SysParams.sspMidMap.put("_030", "1635");
            SysParams.sspMidMap.put("_028", "1113");
            System.out.println("系统加载完成！！！！");
        } catch (Exception e) {
            log.error((Object) ("contextInitialized = " + e.getMessage()), (Throwable) e);
            e.printStackTrace();
        } finally {
            try {
                sysInput.close();
                if (sspInput != null) {
                    sspInput.close();
                }
                if (kafkaInput != null) {
                    kafkaInput.close();
                }
                if (ehcacheInput != null) {
                    ehcacheInput.close();
                }
                if (personTagInput != null) {
                    personTagInput.close();
                }
                if (pageTagInput != null) {
                    pageTagInput.close();
                }
                if (displayTypeInput != null) {
                    displayTypeInput.close();
                }
                if (osTypeInput != null) {
                    osTypeInput.close();
                }
                if (netTypeInput != null) {
                    netTypeInput.close();
                }
                if (deviceTypeInput != null) {
                    deviceTypeInput.close();
                }
                if (openTypeInput != null) {
                    openTypeInput.close();
                }
                if (creativeTypeInput != null) {
                    creativeTypeInput.close();
                }
                if (advertIndustryInput != null) {
                    advertIndustryInput.close();
                }
            } catch (IOException e) {
                log.error((Object) e.getMessage(), (Throwable) e);
                e.printStackTrace();
            }
        }
    }
}


