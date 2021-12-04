package com.kama.code_review_kamenan.application.config.security

import com.kama.code_review_kamenan.application.config.security.handler.AuthenticationSuccessHandler
import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
import com.kama.code_review_kamenan.application.config.security.UserDetailsServiceImplementation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import kotlin.jvm.Throws

/**
 * Default Configuration for Web Security Module
 *
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig @Autowired
constructor(
    private val userDetailsServiceImplementation: UserDetailsServiceImplementation,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf()
            .ignoringAntMatchers(
                "/${RestControllerEndpoint.API_BASE_URL}/**",
                "/${RestControllerEndpoint.API_BASE_SECURED_URL}/**"
            )
            .and()
            .authorizeRequests()
            .antMatchers(
                "/account/login",
                "/account/register",
                "/account/forgot-password",
                "/${RestControllerEndpoint.API_BASE_URL}/**",
                "/${RestControllerEndpoint.API_BASE_SECURED_URL}/**"
            )
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/account/login")
            .failureUrl("/account/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(authenticationSuccessHandler)
            .defaultSuccessUrl("/dashboard/", true)
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/account/logout"))
            .logoutSuccessUrl("/account/login").permitAll()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers(
            "/backend/**",
            "/login/**",
            "/css/**",
            "/demo/**",
            "/fonts/**",
            "/img/**",
            "/js/**",
            "/scss/**"
        )
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsServiceImplementation)
            .passwordEncoder(BCryptPasswordEncoder())
    }
}
