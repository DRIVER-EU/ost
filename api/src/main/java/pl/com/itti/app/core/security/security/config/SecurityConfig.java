package pl.com.itti.app.core.security.security.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import pl.com.itti.app.core.security.security.core.AuthEntryPoint;
import pl.com.itti.app.core.security.security.core.AuthLogoutHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private AuthLogoutHandler authLogoutHandler;

    /**
     * Configures settings that impact global security (ignore resources, set debug
     * mode, reject requests by implementing a custom firewall definition).
     *
     * @param webSecurity
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        // @formatter:off
        webSecurity
                .ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
        // @formatter:on
    }

    /**
     * Allows configuration of web based security at a resource level, based on a selection match.
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .requestCache()
                .requestCache(new NullRequestCache())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/anonymous/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(authLogoutHandler)
                .permitAll();
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) {
        try {
            authBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher publisher) {
        return new DefaultAuthenticationEventPublisher(publisher);
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
