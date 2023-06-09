package com.example.base.domain.member.service.auth.impl;

import com.example.base.common.exception.AccountDormancyException;
import com.example.base.common.exception.AccountLockException;
import com.example.base.common.exception.LoginFailException;
import com.example.base.common.util.SHA512PasswordEncoder;
import com.example.base.domain.common.dao.AuthDao;
import com.example.base.domain.common.model.PrincipalDetail;
import com.example.base.domain.member.model.member.Member;
import com.example.base.domain.member.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
  private final AuthDao authDao;
  private final SHA512PasswordEncoder passwordEncoder;

  @Override
  public PrincipalDetail authentication(Member member) {
    if (StringUtils.isEmpty(member.getId()) || StringUtils.isEmpty(member.getPw())) {
      throw new LoginFailException();
    }

    PrincipalDetail principalDetail = getPrincipalDetail(member);

    checkAccountDormancy(principalDetail);
    checkAccountLock(principalDetail);

    return principalDetail;
  }

  // 사용자 정보를 조회하여 아이디, 비밀번호를 검증한다.
  private PrincipalDetail getPrincipalDetail(Member member) {
    try {
      Member account = authDao.selectAccount(member).orElseThrow();
      if (!passwordEncoder.matches(member.getPw(), account.getPw())) {
        throw new LoginFailException();
      }

      return PrincipalDetail.builder().member(account).build();
    } catch (Exception e) {
      throw new LoginFailException();
    }
  }

  // 휴면 계정 상태 체크
  private void checkAccountDormancy(PrincipalDetail principalDetail) {
    if (!principalDetail.isEnabled() || !principalDetail.isAccountNonExpired()) {
      throw new AccountDormancyException();
    }
  }

  // 계정 장금 상태
  private void checkAccountLock(PrincipalDetail principalDetail) {
    if (!principalDetail.isAccountNonLocked()) {
      throw new AccountLockException();
    }
  }
}
