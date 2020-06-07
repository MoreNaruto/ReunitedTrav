package com.tmorris.reunitedtrav.utils;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@NoArgsConstructor
@Component
public class UpdateBCrypt {
    private static final Logger log = LoggerFactory.getLogger(UpdateBCrypt.class);

    @Value("{bcrpyt.log-rounds}")
    private String logRounds;

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(Integer.parseInt(logRounds)));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        if (BCrypt.checkpw(password, hash)) {
            int logRoundsInteger = Integer.parseInt(logRounds);
            int rounds = getRounds(hash);
            // It might be smart to only allow increasing the rounds.
            // If someone makes a mistake the ability to undo it would be nice though.
            if (rounds != logRoundsInteger) {
                log.debug("Updating password from {} rounds to {}", rounds, logRoundsInteger);
                String newHash = hash(password);
                return updateFunc.apply(newHash);
            }
            return true;
        }
        return false;
    }

    private int getRounds(String salt) {
        char minor;
        int off;

        if (salt.charAt(0) != '$' || salt.charAt(1) != '2')
            throw new IllegalArgumentException("Invalid salt version");
        if (salt.charAt(2) == '$')
            off = 3;
        else {
            minor = salt.charAt(2);
            if (minor != 'a' || salt.charAt(3) != '$')
                throw new IllegalArgumentException("Invalid salt revision");
            off = 4;
        }

        // Extract number of rounds
        if (salt.charAt(off + 2) > '$')
            throw new IllegalArgumentException("Missing salt rounds");
        return Integer.parseInt(salt.substring(off, off + 2));
    }
}
