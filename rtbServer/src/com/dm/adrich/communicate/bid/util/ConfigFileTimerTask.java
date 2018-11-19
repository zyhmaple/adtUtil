package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConfigFileTimerTask extends TimerTask {
    public static final String channelListFlag = "ChannleListFlag";
    public static final String sspCreativeType = "AdrichSSPCreativeType";
    public static final String creativeType = "AdrichCreativeType";
    public static final String displayType = "AdrichDisplayType";
    public static final String netType = "AdrichNetType";
    public static final String osType = "AdrichOSType";
    public static final String deviceType = "AdrichDeviceType";
    public static final String openType = "AdrichOpenType";
    protected static final Logger log = Logger.getLogger(ConfigFileTimerTask.class);
    private Map<String, Object> newMap = null;

    public void run() {
        log.warn("配置文件开始执行，执行时间为 time = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            //page_tag
            Map channelMap = getConfigFileMap("ChannleListFlag", 1);
            if (channelMap != null) {
                Map oldChannelMap = SysParams.pageTagMap;
                SysParams.pageTagMap = channelMap;
                if (oldChannelMap != null) {
                    oldChannelMap.clear();
                    oldChannelMap = null;
                }
            }
            log.info("SysParams.pageTagMap = " + SysParams.pageTagMap);
            //ssp_creative_list
            Map sspCreativeTypeMap = getConfigFileMap("AdrichSSPCreativeType");
            Map oldSSPCreativeTypeMap = SysParams.sspCreativeTypeProps;
            SysParams.sspCreativeTypeProps = sspCreativeTypeMap;
            if (oldSSPCreativeTypeMap != null) {
                oldSSPCreativeTypeMap.clear();
                oldSSPCreativeTypeMap = null;
            }

            //creative_type
            Map creativeTypeMap = getConfigFileMap("AdrichCreativeType");
            Map oldCreativeTypeMap = SysParams.creativeTypeProps;
            SysParams.creativeTypeProps = creativeTypeMap;
            if (oldCreativeTypeMap != null) {
                oldCreativeTypeMap.clear();
                oldCreativeTypeMap = null;
            }

            //display_type
            Map displayTypeMap = getConfigFileMap("AdrichDisplayType");
            Map oldDisplayTypeMap = SysParams.displayTypeProps;
            SysParams.displayTypeProps = displayTypeMap;
            if (oldDisplayTypeMap != null) {
                oldDisplayTypeMap.clear();
                oldDisplayTypeMap = null;
            }

            //open_type
            Map openTypeMap = getConfigFileMap("AdrichOpenType");
            Map oldOpenTypeMap = SysParams.openTypeProps;
            SysParams.openTypeProps = openTypeMap;
            if (oldOpenTypeMap != null) {
                oldOpenTypeMap.clear();
                oldOpenTypeMap = null;
            }

            //os_type
            Map osTypeMap = getConfigFileMap("AdrichOSType");
            Map oldOSTypeMap = SysParams.osTypeProps;
            SysParams.osTypeProps = osTypeMap;
            if (oldOSTypeMap != null) {
                oldOSTypeMap.clear();
                oldOSTypeMap = null;
            }

            //net_type
            Map netTypeMap = getConfigFileMap("AdrichNetType");
            Map oldNetTypeMap = SysParams.netTypeProps;
            SysParams.netTypeProps = netTypeMap;
            if (oldNetTypeMap != null) {
                oldNetTypeMap.clear();
                oldNetTypeMap = null;
            }

            //device_type
            Map deviceTypeMap = getConfigFileMap("AdrichDeviceType");
            Map oldDeviceTypeMap = SysParams.deviceTypeProps;
            SysParams.deviceTypeProps = deviceTypeMap;
            if (oldDeviceTypeMap != null) {
                oldDeviceTypeMap.clear();
                oldDeviceTypeMap = null;
            }

            log.info("初始化系统配置文件完成");
        } catch (Exception e) {
            log.error("ConfigFileTimerTask = " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    //拿出value非空的field-value map
    private Map getConfigFileMap(String key)
            throws Exception {
        while (true) {
            Set<String> setMap = JedisUtil.INSTANCE.getJedis().hkeys(key);
            log.info(key + "= " + setMap);
            if ((setMap != null) && (!setMap.isEmpty())) {
                Map configMap = new HashMap();
                for (String newKey : setMap) {
                    String value = JedisUtil.INSTANCE.getJedis().hget(key, newKey);
                    if ((value == null) || ("".equals(value)))
                        continue;
                    configMap.put(newKey, value);
                }

                return configMap;
            }
            Thread.sleep(15000L);
        }
    }

    private Map getConfigFileMap(String key, int flag) throws Exception {
        Map configMap = JedisUtil.INSTANCE.getJedis().hgetAll(key);
        if ((configMap != null) && (!configMap.isEmpty())) {
            return configMap;
        }

        return null;
    }
}