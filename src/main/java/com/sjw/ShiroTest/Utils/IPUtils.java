package com.sjw.ShiroTest.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Watson on 01/03/2017.
 */
public class IPUtils {
    private String ip;
    private int port;

    public IPUtils(HttpServletRequest request){
        String localIP = "127.0.0.1";
        if (request == null){
            this.ip = localIP;
            this.port = 8080;
        }
        else{
            if("0:0:0:0:0:0:0:1".equals(ip))
                ip = localIP;
            else{
                this.ip = request.getHeader("x-forwarded-for");
                if ("".equals(ip) || ip == null || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if ("".equals(ip) || ip == null || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if ("".equals(ip) || ip == null || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            }
            this.port = request.getLocalPort();
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getIpWithPort(){
        return ip+":"+port;
    }
}
