package com.dm.adrich.communicate.bid.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.log4j.Logger;

import java.util.Properties;

public enum KafkaUtil {
    INSTANCE;

    protected static final Logger log;
    private static int waitTimeMillis;

    static {
        waitTimeMillis = 2000;
        log = Logger.getLogger(KafkaUtil.class);
    }

    private Properties kafkaProps = null;
    private KafkaProducer<byte[], byte[]> logProducer = null;

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

