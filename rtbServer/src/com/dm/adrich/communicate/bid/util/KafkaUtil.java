/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.kafka.clients.producer.KafkaProducer
 *  org.apache.log4j.Logger
 */
package com.dm.adrich.communicate.bid.util;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.log4j.Logger;

public enum KafkaUtil {
    INSTANCE;
    
    private static int waitTimeMillis;
    private Properties kafkaProps = null;
    private KafkaProducer<byte[], byte[]> logProducer = null;
    protected static final Logger log;

    static {
        waitTimeMillis = 2000;
        log = Logger.getLogger(KafkaUtil.class);
    }

    public KafkaProducer getLogProducer() {
        if (this.logProducer != null) {
            return this.logProducer;
        }
        this.logProducer = new KafkaProducer(this.kafkaProps);
        return this.logProducer;
    }

    public Properties getKafkaProps() {
        return this.kafkaProps;
    }

    public void setKafkaProps(Properties kafkaProps) {
        this.kafkaProps = kafkaProps;
    }

    public void close() {
        this.getLogProducer().close();
    }
}

