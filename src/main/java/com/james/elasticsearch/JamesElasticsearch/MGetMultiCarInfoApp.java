package com.james.elasticsearch.JamesElasticsearch;

import java.net.InetAddress;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class MGetMultiCarInfoApp {

    @SuppressWarnings({ "resource", "unchecked" })
    public static void main(String[] args) throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "james-es").put("client.transport.sniff", true)
                .build();
        
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        MultiGetResponse multiGetResponse = client.prepareMultiGet().add("car_shop", "cars", "1")
                .add("car_shop", "cars", "2").get();

        for (MultiGetItemResponse multiGetItemResponse : multiGetResponse) {
            GetResponse getResponse = multiGetItemResponse.getResponse();
            if (getResponse.isExists()) {
                System.out.println(getResponse.getSourceAsString());
            }
        }

        client.close();
    }

}
