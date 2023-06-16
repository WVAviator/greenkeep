package com.wvaviator.greenkeep.security;

import com.wvaviator.greenkeep.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.debug("Checking if parameter {} is supported", parameter.getParameterType());
        return parameter.getParameterAnnotation(AuthenticatedUser.class) != null
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        log.debug("Resolving argument {}", parameter.getParameterType());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.warn("No authentication found in security context.");
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomOidcUser customOidcUser) {
            log.debug("Principal verified, returning user entity.");
            return customOidcUser.getUser();
        }

        log.warn("Principal {} is invalid.", principal);
        return null;
    }
}
