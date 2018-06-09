package uy.edu.ucu.si.grupo8.obligatorio3.transport;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PasswordPawnedRestClient {

    private final RestTemplate restTemplate;

    public PasswordPawnedRestClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void a() throws URISyntaxException {


        final HttpHeaders headers = new HttpHeaders();
        headers.set("User-agent", "SomeUserAgent");
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        final RestTemplate rt = new RestTemplate();
        final String response = rt.exchange("https://api.pwnedpasswords.com/range/21BD1", HttpMethod.GET, entity, String.class).getBody();
        processResponse(response);
    }

    private Map<String, Integer> processResponse(final String response) {

        final String[] hashes = response.split("\r\n");

        final Map<String, Integer> collect = Stream.of(hashes)
                .map(hash -> hash.split(":"))
                .collect(Collectors.
                        toMap(hash -> hash[0], hash -> Integer.parseInt(hash[1])));

        System.out.println(collect);
        return null;
    }


}
