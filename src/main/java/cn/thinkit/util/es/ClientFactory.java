package cn.thinkit.util.es;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import cn.thinkit.config.ConfigProp;
import cn.thinkit.util.properties.ApplicationProperties;

/**
 * ES的ClientFactory
 *
 */
public class ClientFactory {
    private static volatile RestHighLevelClient restHighLevelClient;

    private static ConfigProp configProp = (ConfigProp) ApplicationProperties.getProperties().get("configProp");

    private static List<HttpHost> hosts;

    static {
        hosts = new ArrayList<>();
        String[] nodes = configProp.getEsNode().split(",");
        for (String node : nodes) {
            int st = node.indexOf(":");
            String ip = node.substring(0, st);
            int port = Integer.parseInt(node.substring(st + 1));
            hosts.add(new HttpHost(ip, port, "http"));
        }
    }

    public static RestHighLevelClient getRestHighLevelClient() {
        if (restHighLevelClient == null) {
            synchronized (RestHighLevelClient.class) {
                if (restHighLevelClient == null) {
                    restHighLevelClient = new RestHighLevelClient(
                            RestClient.builder(hosts.toArray(new HttpHost[hosts.size()]))
                                    .setRequestConfigCallback(requestConfigBuilder -> {
                                        requestConfigBuilder
                                                .setConnectTimeout(5000)
                                                .setSocketTimeout(60000);
                                        return requestConfigBuilder;
                                    })
                                    .setMaxRetryTimeoutMillis(60000)
                    );
                }
            }
        }
        return restHighLevelClient;
    }
}
