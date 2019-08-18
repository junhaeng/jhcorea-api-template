package com.jhcorea.template.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class BaseClient implements InitializingBean {

    protected final static Logger LOGGER = LoggerFactory.getLogger(BaseClient.class);

    private CloseableHttpClient client;

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    @Value("${base.client.connectionTimeout:1500}")
    private int connectionTimeout;
    @Value("${base.client.connectionRequestTimeout:1500}")
    private int connectionRequestTimeout;
    @Value("${base.client.socketTimeout:1500}")
    private int socketTimeout;
    @Value("${base.client.maxTotal:100}")
    private int maxTotal;
    @Value("${base.client.defaultMaxPerRoute:15}")
    private int defaultMaxPerRoute;

    @Override
    public void afterPropertiesSet() throws Exception {

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout).build();

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        CloseableHttpClient client = HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(config)
                .build();

        this.client = client;

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
    }

    public String getByTest(String url) {
        HttpGet get = new HttpGet(URI.create(url));
        try {
            CloseableHttpResponse response = client.execute(get);
            return response.getEntity().getContent().toString();
        } catch (Exception e) {
            LOGGER.error("error!!");
            return null;
        }
    }
}
