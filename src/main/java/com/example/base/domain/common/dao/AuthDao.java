package com.example.base.domain.common.dao;

import com.example.base.domain.member.model.member.AuthTokenEntity;
import com.example.base.domain.member.model.member.Member;

import java.util.Optional;

public interface AuthDao {

  Optional<Member> selectAccount(Member member);

  Optional<AuthTokenEntity> selectAuthToken(AuthTokenEntity authToken);

  void insertAuthToken(AuthTokenEntity authToken);
}
