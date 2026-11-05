package com.acme.salary.security;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TokenBlacklist {

    private final ConcurrentMap<String, Long> revokedTokens = new ConcurrentHashMap<>();

    public void revoke(String token, Date expiration) {
        purgeExpiredTokens();
        revokedTokens.put(token, expiration.getTime());
    }

    public boolean isRevoked(String token) {
        Long expiration = revokedTokens.get(token);
        if (expiration == null) {
            return false;
        }
        if (expiration <= System.currentTimeMillis()) {
            revokedTokens.remove(token, expiration);
            return false;
        }
        return true;
    }

    private void purgeExpiredTokens() {
        long now = System.currentTimeMillis();
        revokedTokens.entrySet().removeIf(entry -> entry.getValue() <= now);
    }
}
