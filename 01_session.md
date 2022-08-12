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
 
### 시큐리티 아키텍처
 
SecurityContextHolder
 - SecurityContext
  - Authentication : ThreadLocal로 한쓰레드안에서 관리
   - principal 
 
SecurityContextHolder.getContext().getAuthentication();

AuthenticationManager
인증을 관리하고 인증을 만든다.


AuthenticationManager는 사용자가 입력한 authentication객체를 인증할수 있는 
AutheticationProvider를 찾아주고 
해당 provider는 UserdetailsService가 반환한 userDetail를 바탕으로
비번일치유무로 인증을 하고 일치한다면 authenticationmanager는 
autheticationprovider가 반환한 authentication(principal 변수에 userdetail를 담음)를 getmaapin 
메서드에 principal매개변수로 넘겨준다.
