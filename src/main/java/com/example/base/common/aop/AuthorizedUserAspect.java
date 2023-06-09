package com.example.base.common.aop;

import com.example.base.common.aop.annotation.AuthorizedUser;
import com.example.base.common.exception.UnauthorizedException;
import com.example.base.common.util.RequestUtils;
import com.example.base.domain.common.jwt.JwtService;
import com.example.base.domain.common.model.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
@Aspect
@Slf4j
public class AuthorizedUserAspect {

    @Qualifier("jwtServiceImpl")
    private final JwtService jwtService;

    /**
     * AuthorizedUser 어노테이션 붙은 메서드를 체크하여 로그인된 사용자인지 체크
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("@annotation(com.example.base.common.aop.annotation.AuthorizedUser)")
    public Object authorizedUserAllow(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AuthorizedUser authorizedUser = methodSignature.getMethod().getAnnotation(AuthorizedUser.class);

        String accessToken = resolveToken();
        if (jwtService.validateToken(accessToken)) {
            PrincipalDetail principalDetail = jwtService.getAuthentication(accessToken);

            if (RequestUtils.getRequest() != null) {
                RequestUtils.getRequest().setAttribute("memberAuth", principalDetail);
            }
        } else {
            throw new UnauthorizedException();
        }

        return joinPoint.proceed();
    }

    private String resolveToken() {
        if (RequestUtils.getRequest() != null) {
            String bearerToken = RequestUtils.getRequest().getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
                return bearerToken.substring(7);
            }
        }

        return null;
    }
}
