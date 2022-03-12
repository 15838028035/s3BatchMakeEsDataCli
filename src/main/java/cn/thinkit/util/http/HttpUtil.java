package cn.thinkit.util.http;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http请求工具类
 *
 * @author huzenghui
 * @date 2020/12/26
 */
public class HttpUtil {

    /**
     * 发送请求
     *
     * @param url         接收请求的url
     * @param postParam   向url发送的字符串
     * @param contentType postParam的格式，如json
     * @param charset     postParam的编码格式，如utf-8
     * @return 发送字符串后的到的返回值
     * @throws Exception 网络连接失败时的异常
     */
    private static String send(String url, String postParam, String contentType, String charset) throws Exception {
        OutputStream osw = null;
        InputStream ins = null;
        String var7;
        try {
            HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setConnectTimeout(10000);
            con.setReadTimeout(30000);
            con.setRequestProperty("Content-Type", contentType);
            if (null != postParam) {
                con.setDoOutput(true);
                byte[] resCode = postParam.getBytes("UTF-8");
                con.setRequestProperty("Content-Length", Integer.toString(resCode.length));
                osw = con.getOutputStream();
                osw.write(resCode);
                osw.flush();
            }

            int resCode1 = con.getResponseCode();
            if (resCode1 < 400) {
                ins = con.getInputStream();
            } else {
                ins = con.getErrorStream();
            }

            var7 = readContent(ins, charset);
        } finally {
            if (osw != null) {
                osw.close();
            }

            if (ins != null) {
                ins.close();
            }

        }
        return var7;
    }

    private static String readContent(InputStream ins, String charset) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(ins, charset));

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * 发送Post请求
     * 数据格式为json
     *
     * @param url  接收请求的url地址
     * @param json 要发送的请求
     * @return 发送请求后收到的返回值
     */
    public static String sendJsonHttpPost(String url, String json) throws Exception {
        String charset = "UTF-8";
        return send(url, json, "application/json", charset);
    }
}
