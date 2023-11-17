package com.utez.geco.model.Jwt;

import com.utez.geco.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtUserDetails extends User {
    public final Long id;

    public JwtUserDetails(final Long id, final String username, final String hash,
                          final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash,authorities);
        this.id = id;
    }
}
