package com.openparts.utils.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import java.util.ArrayList;
import java.util.List;

class RestClientAddressesBuilder {

    /**
     * Returns a new {@link RestClientBuilder} to help with {@link RestClient} creation.
     * Creates a new builder instance and sets the hosts that the client will send requests to.
     *
     * @param addresses  	the hostname (IP or DNS name) : port (the port number)
     * @param scheme		the name of the scheme.
     */
    public static RestClientBuilder builder(final String addresses, final String scheme) {

        String[] sources = addresses.split(";");
        HttpHost[] httpHosts = new HttpHost[sources.length];

        int i = 0;
        for(String item : sources) {
            String[] hp = item.split(":");

            // HttpHost(final String hostname, int port, final String scheme)
            httpHosts[i++] = new HttpHost(hp[0], Integer.valueOf(hp[1]), scheme);
        }

        return RestClient.builder(httpHosts);
    }
}
