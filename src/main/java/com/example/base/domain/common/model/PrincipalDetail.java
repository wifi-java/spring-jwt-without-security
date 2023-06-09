package com.example.base.domain.common.model;

import com.example.base.domain.member.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalDetail {
  private Member member;

  public Member getMember() {
    return member;
  }

  public int getIdx() {
    return member.getIdx();
  }

  public String getUserId() {
    return member.getId();
  }

  public String getUsername() {
    return member.getName();
  }

  public String getPassword() {
    return member.getPw();
  }

  /**
   * 계정 만료 여부
   *
   * @return boolean
   */
  public boolean isAccountNonExpired() {
    // true 만료되지 않음
    return true;
  }

  /**
   * 계정 잠금 여부
   *
   * @return boolean
   */
  public boolean isAccountNonLocked() {
    // true 잠기지 않음
    return true;
  }

  /**
   * 패스워드 만료 여부
   *
   * @return boolean
   */
  public boolean isCredentialsNonExpired() {
    // true 만료되지 않음
    return true;
  }

  /**
   * 계정 활성화 여부
   *
   * @return boolean
   */
  public boolean isEnabled() {
    return true;
  }
}
