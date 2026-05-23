package e_commerce_backend.common.util;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>,
                                                         org.springframework.core.convert.converter.Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Long userId = jwt.getClaim("id");
        Collection<String> roles = jwt.getClaim("role");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        }
        UserInformation userInformation = new UserInformation();
        userInformation.setAccountId(userId);
        return new UsernamePasswordAuthenticationToken(userInformation, jwt, authorities);
    }

    @Override
    public AbstractAuthenticationToken convert(DeserializationContext ctxt, Jwt value) {
        return Converter.super.convert(ctxt, value);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
