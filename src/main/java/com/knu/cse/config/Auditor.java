/*
package com.knu.cse.config;

import com.knu.cse.member.security.SecurityMember;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Auditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated())
            return null;

        SecurityMember principal = (SecurityMember) authentication.getPrincipal();
        String email = principal.getEmail();
        int aIndex = email.indexOf("@");
        return Optional.of(email.substring(0, aIndex));
    }

}
*/
