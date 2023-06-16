package com.wvaviator.greenkeep.security;

import com.wvaviator.greenkeep.user.Role;
import com.wvaviator.greenkeep.user.User;
import com.wvaviator.greenkeep.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("Processing OIDC user info request for {}", userRequest.getClientRegistration().getRegistrationId());
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getAttribute("email");

        return new CustomOidcUser(oidcUser, userRepository.findByEmail(email).orElseGet(() -> {
            log.info("Creating new user with email {}", email);

            String firstName = oidcUser.getAttribute("given_name");
            String lastName = oidcUser.getAttribute("family_name");

            User newUser = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .role(Role.USER)
                    .build();

            User savedUser = userRepository.save(newUser);
            log.info("Created new user with id {} and role {}", savedUser.getId(), savedUser.getRole());

            return savedUser;
        }));
    }
}
