package com.ifchange.rpc.position.json;

/**
 * @ClassName ApiHeader
 * @Description: 请求头
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
public class ApiHeader {
    public String product_name = "position";
    public String provider = "data_group";
    public String uname = "position_rpc";
    public String signid;
    public String user_id;
    public String local_ip;
    public String log_id;
    public int appid;
    public int uid;
    public int session_id;
}