package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@EnableMethodSecurity

public class securityConfig {

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf ( AbstractHttpConfigurer::disable )
                .sessionManagement
                        ( sm -> sm.sessionCreationPolicy ( SessionCreationPolicy.STATELESS ) )
                .authorizeHttpRequests ( auth -> auth

                .requestMatchers ( "/auth/**" , "/actuator/health" , "/v3/api-docs/**" , "/swagger-ui/**" )
                .permitAll ( )

                .requestMatchers ( "/admin/**" ).hasRole ( "ADMIN" )

                .requestMatchers ( HttpMethod.GET , "/api/v1/doctors/**" ).permitAll ( )
                .requestMatchers ( HttpMethod.POST , "/api/v1/doctors/**" ).hasAnyRole ( "ADMIN" , "PATIENT" )
                .requestMatchers ( HttpMethod.PUT , "/api/v1/doctors/**" ).hasAnyRole ( "ADMIN" , "PATIENT" )
                .requestMatchers ( HttpMethod.DELETE , "/api/v1/doctors/**" ).hasAnyRole ( "ADMIN" )

                .requestMatchers ( HttpMethod.GET , "/api/v1/patients/**" ).hasRole ( "ADMIN"  )
                .requestMatchers ( HttpMethod.POST , "/api/v1/patients/**" ).hasAnyRole ( "ADMIN" , "PATIENT" )
                .requestMatchers ( HttpMethod.PUT , "/api/v1/patients/**" ).hasAnyRole ( "ADMIN" , "PATIENT" )
                .requestMatchers ( HttpMethod.DELETE , "/api/v1/patients/**" ).hasAnyRole ( "ADMIN" )

                .requestMatchers ( HttpMethod.GET , "/api/v1/appointments/**" ).permitAll ( )
                .requestMatchers ( HttpMethod.POST , "/api/v1/appointments/**" ).hasAnyRole ( "ADMIN" , "DOCTOR" )
                .requestMatchers ( HttpMethod.PUT , "/api/v1/appointments/**" ).hasAnyRole ( "ADMIN" , "DOCTOR" )
                .requestMatchers ( HttpMethod.DELETE , "/api/v1/appointments/**" ).hasAnyRole ( "ADMIN" , "DOCTOR" )

                .anyRequest ( ).denyAll ( ) ).httpBasic ( Customizer.withDefaults ( ) ).build ( );


    }

}
