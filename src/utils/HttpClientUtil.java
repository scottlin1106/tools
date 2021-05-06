package utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    public static String formPostSkipSSL(String strUrl, Map reqMap) {
        return httpPost(strUrl, reqMap, "UTF-8", "UTF-8");
    }

    public static String httpPost(String url, Map paraMap, String charset, String resCharset) {
        String result = "";
        String content = "";
        content = transKeyValuePair(paraMap);
        try {
            CloseableHttpClient client;
            client = HttpClients.createDefault();

            HttpPost request = new HttpPost();
            request.setURI(new java.net.URI(url));
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(10000).build();
            request.setConfig(requestConfig);
            request.setHeader("Content-Type", "application/x-www-form-urlencoded;chatset=" + charset);
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36)");

            request.setEntity(new ByteArrayEntity(content.getBytes(charset)));
            CloseableHttpResponse response = client.execute(request);

            result = EntityUtils.toString(response.getEntity(), resCharset);
            request.abort();
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    //SkipSSL

    //key Value pair
    public static String transKeyValuePair(Map param) {
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(param.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            if (param.get(key) != null && !param.get(key).equals("")) {
                sb.append(key).append("=").append(param.get(key)).append("&");
            }
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
