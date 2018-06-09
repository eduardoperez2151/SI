package uy.edu.ucu.si.grupo8.obligatorio3.transport.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uy.edu.ucu.si.grupo8.obligatorio3.transport.PasswordPawnedRestClient;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PasswordPawnedRestClientImpl implements PasswordPawnedRestClient {

    public static final String CARRIAGE_RETURN = "\r\n";
    public static final String SEPARATOR = ":";


    @Value("${uy.edu.ucu.si.service.pawned.url}")
    private String passwordPawnedServiceUrl;


    private final RestTemplate restTemplate;

    public PasswordPawnedRestClientImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private HttpEntity<String> getUserAgent() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("User-agent", "grupo8-UA");
        return new HttpEntity<>(headers);
    }

    private Map<String, Integer> getHashMapFromResponse(final String response) {
        final String[] hashes = response.split(CARRIAGE_RETURN);
        return Stream.of(hashes)
                .map(hash -> hash.split(SEPARATOR))
                .collect(Collectors.
                        toMap(hash -> hash[0], hash -> Integer.parseInt(hash[1])));
    }

    @Override
    public Map<String, Integer> getPasswordPawnedResponse(final String hashedSHA1Password) {
        final HttpEntity<String> entity = getUserAgent();
        final String passwordHashedSHA1Prefix = hashedSHA1Password.substring(0, 5);
        final String response = restTemplate.exchange(passwordPawnedServiceUrl + passwordHashedSHA1Prefix, HttpMethod.GET, entity, String.class).getBody();
        return getHashMapFromResponse(response);
    }

}
