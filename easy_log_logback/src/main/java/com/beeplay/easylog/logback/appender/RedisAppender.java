package com.beeplay.easylog.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.beeplay.easylog.core.LogMessage;
import com.beeplay.easylog.core.constant.LogMessageConstant;
import com.beeplay.easylog.core.dto.BaseLogMessage;
import com.beeplay.easylog.core.dto.RunLogMessage;
import com.beeplay.easylog.core.dto.TraceLogMessage;
import com.beeplay.easylog.core.redis.RedisClient;
import com.beeplay.easylog.core.util.GfJsonUtil;
import com.beeplay.easylog.core.util.ThreadPoolUtil;
import com.beeplay.easylog.logback.util.LogMessageUtil;

import java.util.concurrent.ThreadPoolExecutor;


public class RedisAppender extends AbstractAppender {
    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getPool(4, 8, 5000);
    private RedisClient redisClient;
    private String appName;
    private String reidsHost;
    private String redisPort;
    private String redisAuth;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setReidsHost(String reidsHost) {
        this.reidsHost = reidsHost;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public void setRedisAuth(String redisAuth) {
        this.redisAuth = redisAuth;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (redisClient == null) {
            redisClient = RedisClient.getInstance(this.reidsHost, Integer.parseInt(this.redisPort), this.redisAuth);
        }
        super.push(this.appName, event, redisClient);
    }
}
