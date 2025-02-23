package com.apartment.management.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.apartment.management.repository.UserRepository;
import com.apartment.management.service.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public SecurityConfig(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // ✅ กำหนด UserDetailsService เพื่อใช้กับระบบ Authentication
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("🔍 ค้นหาผู้ใช้จากฐานข้อมูล: " + username);
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        System.out.println("🚨 ไม่พบผู้ใช้: " + username);
                        return new UsernameNotFoundException("User not found: " + username);
                    });
        };
    }

    // ✅ Security Filter Chain รวมทั้ง 2 Unit
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ รองรับ CORS
                .csrf(csrf -> csrf.disable()) // ❌ ปิด CSRF เพื่อให้ REST API ทำงาน
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ✅ API Public ที่ทุกคนเข้าได้ (จาก Unit 2)
                        .requestMatchers("/api/public/**").permitAll()

                        // ✅ API สำหรับ Authentication (Login/Register)
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                        // ✅ API ที่ต้องใช้ Authentication (จาก Unit 1)
                        .requestMatchers("/api/auth/check").authenticated()

                        // ✅ API ที่อนุญาตให้ USER และ ADMIN ใช้ได้
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")

                        // ✅ API ที่ ADMIN เท่านั้นเข้าได้
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // ✅ API ทั่วไปที่ต้องมีการล็อกอิน
                        .requestMatchers("/api/**").permitAll() 

                        // ❌ อื่น ๆ ปิดกั้นหมด
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ JWT Authentication Filter
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userDetailsService(), jwtService);
    }

    // ✅ กำหนด BCryptPasswordEncoder สำหรับเข้ารหัสรหัสผ่าน
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ กำหนด AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ✅ กำหนด AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ✅ CORS Configuration รองรับหลายยูนิต
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:3000")); // ✅ Angular & React
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

// package com.apartment.management.config;

// import com.apartment.management.repository.UserRepository;
// import com.apartment.management.service.JwtService;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.Arrays;
// import java.util.List;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final UserRepository userRepository;
//     private final UserDetailsService userDetailsService;

//     public SecurityConfig(UserRepository userRepository) {
//         this.userRepository = userRepository;
//         this.userDetailsService = username -> userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//     }

//     @Bean
//     public JwtService jwtService() {
//         return new JwtService(); // ✅ สร้าง Bean ของ JwtService
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
//         http
//                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                 .csrf(csrf -> csrf.disable())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
//                         .requestMatchers("/api/auth/check").authenticated()
//                         .requestMatchers("/api/**").authenticated()
//                         .anyRequest().authenticated())
//                 .addFilterBefore(jwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class); // ✅
//                                                                                                                    // ส่ง
//                                                                                                                    // jwtService
//                                                                                                                    // เข้าไป

//         return http.build();
//     }

//     @Bean
//     public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
//         return new JwtAuthenticationFilter(userDetailsService, jwtService); // ✅ แก้ไขให้ส่ง JwtService ไปด้วย
//     }

//     @Bean
//     public BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//         provider.setUserDetailsService(userDetailsService);
//         provider.setPasswordEncoder(passwordEncoder());
//         return provider;
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//         return configuration.getAuthenticationManager();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//         configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         return username -> {
//             System.out.println("🔍 ค้นหาผู้ใช้จากฐานข้อมูล: " + username);
//             return userRepository.findByUsername(username)
//                     .orElseThrow(() -> {
//                         System.out.println("🚨 ไม่พบผู้ใช้: " + username);
//                         return new UsernameNotFoundException("User not found: " + username);
//                     });
//         };
//     }

// }

// // package com.apartment.management.config;

// // import org.springframework.context.annotation.Bean;
// // import org.springframework.context.annotation.Configuration;
// // import
// // org.springframework.security.config.annotation.web.builders.HttpSecurity;
// // import
// // org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// // import org.springframework.security.web.SecurityFilterChain;
// // import org.springframework.web.cors.CorsConfiguration;
// // import org.springframework.web.cors.CorsConfigurationSource;
// // import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// // import java.util.Arrays;
// // import java.util.List;

// // import static org.springframework.security.config.Customizer.withDefaults;

// // @Configuration
// // @EnableWebSecurity
// // public class SecurityConfig {

// // @Bean
// // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// // Exception {
// // http
// // .cors(withDefaults()) // เปิดใช้งาน CORS
// // .csrf().disable() // ปิด CSRF เพื่อให้ REST API ทำงาน
// // .authorizeHttpRequests((authorize) -> authorize
// // .requestMatchers("/api/**").permitAll() // ✅ อนุญาตทุก API
// // .anyRequest().permitAll() // ✅ ให้สิทธิ์ทุก request
// // )

// // .httpBasic().disable(); // ปิด Basic Auth
// // return http.build();
// // }

// // @Bean
// // public CorsConfigurationSource corsConfigurationSource() {
// // CorsConfiguration configuration = new CorsConfiguration();
// // configuration.setAllowedOrigins(List.of("http://localhost:4200")); // ✅
// // อนุญาต Angular
// // configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
// // "OPTIONS")); // ✅ Methods ที่อนุญาต
// // configuration.setAllowedHeaders(Arrays.asList("*")); // ✅ อนุญาตทุก Header
// // configuration.setAllowCredentials(true); // ✅ อนุญาตให้ใช้ Credentials
// // (สำคัญมาก)

// // UrlBasedCorsConfigurationSource source = new
// // UrlBasedCorsConfigurationSource();
// // source.registerCorsConfiguration("/**", configuration); // ✅ ใช้กับทุก URL
// // return source;
// // }

// // }
