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
 - 유저 접근 java
 - 로그인 테스트
 
AccountContext
 - ThreadLocal 정의
 
AuthencationManage가 UserNamePasswordFilter에서 인증하고나서 
persistenceFilter에서 session에 context를 저장한뒤
시큐리티 필터를 거치는 모든 요청에 ThreadLocal에 저장이 된다.