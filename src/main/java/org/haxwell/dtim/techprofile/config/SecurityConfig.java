package org.haxwell.dtim.techprofile.config;

import static org.haxwell.dtim.techprofile.enums.Role.ADMIN;
import static org.haxwell.dtim.techprofile.enums.Role.CANDIDATE;
import static org.haxwell.dtim.techprofile.enums.Role.CLIENT;
import static org.springframework.http.HttpMethod.POST;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //====================providers
    @Bean
    public DaoAuthenticationProvider jsonAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    //====================encoders
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //====================authentication_managers
    @Bean
    public ProviderManager providerManager() {
        return new ProviderManager(Arrays.asList(
                jsonAuthenticationProvider()
        ));
    }

    //===================CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Configuration
    @Order(1)
    public static class InternalWebApiConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            final String[] URLS = {
                    "/api/authenticate",
                    "/api/mockinterviewsession/**",
                    "/api/user/in-attendance",
                    "/api/user/new",
                    "/api/user/**",
                    "/api/question/**",
                    "/api/techprofile/**",
                    "/api/attendance-history/**"
            };


            http
                    .csrf().disable()
                    .cors()
                    .and()
                    //.anonymous().disable()
                    .requestMatchers()
                    .antMatchers(URLS)
                    .antMatchers(POST, "/api/user/new")
//                    .and()
//                    
//                    // TODO Tighten up all the permitAll()s. I use them because at the time the client is calling them, a user has 
//                    //  not been set, so unless I figure out a way (haven't even really tried at this point) to use two users, one
//                    //  being the admin, and still being able to keep its password secret.. especially that last part. how to do that?
//                    
//                    .authorizeRequests()
//                    //.antMatchers("/api/authenticate")
//                    //.antMatchers(POST, "/api/user/new").permitAll() 
//                    //.antMatchers("/api/mockinterviewsession/create").permitAll()
//                    //.antMatchers("/api/mockinterviewsession/mostrecent").permitAll()
//                    //.antMatchers("/api/user/**").hasAnyAuthority(CANDIDATE.name(), CLIENT.name(), ADMIN.name())
//                    .antMatchers("/api/question/**").hasAnyAuthority(CANDIDATE.name(), CLIENT.name(), ADMIN.name())
//                    .antMatchers("/api/techprofile/**").hasAnyAuthority(CANDIDATE.name(), CLIENT.name(), ADMIN.name())
            ;
        }
    }
}
