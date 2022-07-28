# 스프링시큐리티

### 기본 동작
SampleController
 - id : user
 - pw : console 확인
 - Principal 동작확인

### 시큐리티 설정
SecurityConfig
 - filterChain 권한분리
 - InMemoryUserDetailsManager
  - 임의의 id/pw 부여하여 로그인처리
 
application.yml
 - id/pw 를 부여하여 처리할수 있다.

 
 SpringSecurityApplication
 - passwordEncoder pw 저장방식을 bean으로 등록
 
 
 AccountControllerTest
 - 유저 접근 test
 - 로그인 테스트