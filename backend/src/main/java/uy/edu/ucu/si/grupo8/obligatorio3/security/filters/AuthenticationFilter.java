package uy.edu.ucu.si.grupo8.obligatorio3.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.ResponseAPI;
import uy.edu.ucu.si.grupo8.obligatorio3.models.User;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Long EXPIRATION_TOKEN = 600000L;

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        try {
            final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (final IOException e) {
            throw new UnapprovedClientAuthenticationException("Uupps algo paso!!!");
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain,
                                            final Authentication auth) {
        final Claims claims = Jwts.claims()
                .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal())
                        .getUsername());

        claims.put("scopes",
                auth.getAuthorities()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()));

        final String token = Jwts.builder().
                setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                .signWith(SignatureAlgorithm.HS512, "s3cr37").compact();
        response.addHeader(HttpHeaders.AUTHORIZATION, token);
    }

    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) throws AuthenticationException, IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        final ResponseAPI payload = getErrorResponseBody();
        final PrintWriter writer = response.getWriter();
        writer.print(payload);
    }

    private ResponseAPI getErrorResponseBody() {
        return ResponseAPI.error("Upsss algo ocurrio!!!");
    }
}