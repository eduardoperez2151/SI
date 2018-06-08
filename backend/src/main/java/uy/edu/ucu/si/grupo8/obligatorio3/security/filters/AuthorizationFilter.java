package uy.edu.ucu.si.grupo8.obligatorio3.security.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.ResponseAPI;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(final AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {

            final PrintWriter writer = response.getWriter();
            final ResponseEntity<ResponseAPI> payoad = getErrorResponseBody();
            writer.print(payoad);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
        final UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null) {
            return null;
        }

        final Claims ClaimsBody = Jwts.parser()
                .setSigningKey("s3cr37")
                .parseClaimsJws(token)
                .getBody();

        final String username = ClaimsBody.getSubject();
        if (username == null) {
            return null;
        }

        final List<String> scopes = ClaimsBody.get("scopes", List.class);
        final List<GrantedAuthority> authorities = scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);

    }

    private ResponseEntity<ResponseAPI> getErrorResponseBody() {
        return ResponseEntity.
                status(HttpStatus.FORBIDDEN)
                .body(ResponseAPI.error("Upsss algo ocurrio!!!"));
    }
}
