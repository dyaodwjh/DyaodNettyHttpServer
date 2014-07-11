package com.dyaod.system.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by dyaod on 14-7-9.
 */
public class AppConfig {

    private String ip; //启动的ip,如果为空则使用本机ip
    private Integer port; //配置的port
    private Integer bossGroupSize;
    private Integer workerGroupSize;

    public void init(){

            InetAddress addr = null;
            try {
                addr = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            this.ip=addr.getHostAddress();

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getBossGroupSize() {
        return bossGroupSize;
    }

    public void setBossGroupSize(Integer bossGroupSize) {
        this.bossGroupSize = bossGroupSize;
    }

    public Integer getWorkerGroupSize() {
        return workerGroupSize;
    }

    public void setWorkerGroupSize(Integer workerGroupSize) {
        this.workerGroupSize = workerGroupSize;
    }
}
