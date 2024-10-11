// package com.traineemanagement.userservice.config;


// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.lang.NonNull;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import com.traineemanagement.userservice.Util.CustomUserDetailsService;
// import com.traineemanagement.userservice.Util.JwtAuthenticationFilter;
// import com.traineemanagement.userservice.Util.JwtUtil;



// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// public class Security {

//     private final JwtUtil jwtService;
//     private final CustomUserDetailsService customUserDetailsService;

//     // Constructor for dependency injection
//     public Security(JwtUtil jwtService, CustomUserDetailsService customUserDetailsService) {
//         this.jwtService = jwtService;
//         this.customUserDetailsService = customUserDetailsService;
//     }

    
//     /** 
//      * @param http
//      * @return SecurityFilterChain
//      * @throws Exception
//      */
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .csrf(csrf -> csrf.disable())
//                 .cors(cors -> cors.configure(http))
//                 .sessionManagement(session -> session
//                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(requests -> requests
//                         .requestMatchers("/v1/auth/**").permitAll()
//                         .anyRequest().authenticated())
//                 .addFilterBefore(new JwtAuthenticationFilter(jwtService, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }

    
//     /** 
//      * @return BCryptPasswordEncoder
//      */
//     @Bean
//     public BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

    
//     /** 
//      * @return WebSecurityCustomizer
//      */
//     @Bean
//     public WebSecurityCustomizer webSecurityCustomizer() {
//         return (web) -> web.ignoring().requestMatchers(
//                 "/v1/auth/**",
//                 "/swagger-resources/**",
//                 "/swagger-ui.html/**",
//                 "/swagger-ui/**",
//                 "/v3/api-docs/**");
//     }

    
//     /** 
//      * @return WebMvcConfigurer
//      */
//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(@NonNull CorsRegistry registry) {
//                 registry.addMapping("/**")
//                         .allowedMethods("*");
//             }
//         };
//     }
// }
