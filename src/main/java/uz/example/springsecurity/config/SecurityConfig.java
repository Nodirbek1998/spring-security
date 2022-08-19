package uz.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.example.springsecurity.security.DomainUserDetailsService;
import uz.example.springsecurity.security.JwtAuthenticationFilter;

import static uz.example.springsecurity.config.ApplicationUserRole.STUDENT;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final PasswordEncoder passwordEncoder;

    private final DomainUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(DomainUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
//                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyAuthority(STUDENT_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/management/api/**").hasAnyAuthority(STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAnyAuthority(STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAnyAuthority(STUDENT_WRITE.getPermission())
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails nodir = User.builder()
//                .username("nodir")
//                .password(passwordEncoder.encode("password"))
////                .roles("STUDENT")
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//        UserDetails aziz = User.builder()
//                .username("aziz")
//                .password(passwordEncoder.encode("password"))
////                .roles("STUDENT")
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                nodir,
//                aziz
//        );
//
//    }
}
