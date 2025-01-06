package com.example.ideasphere.Config;

import com.example.ideasphere.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    //COMPANY|INDIVIDUAL|PARTICIPANT|ADMIN
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/company-organizer/register","/api/v1/participant/register","/api/v1/individual-organizer/register" , "/api/v1/competition/get-all-competition", "/api/v1/company-competition/get-all-competition").permitAll()
                .requestMatchers("/api/v1/company-organizer/get-profile","/api/v1/individual-competition/get-all-competition"
                        ,"/api/v1/subscription-package/get-subscription-package-by-id/{id}"
                        ,"/api/v1/company-organizer/update-profile"
                        ,"/api/v1/company-competition/get-my-competitions"
                        ,"/api/v1/company-competition/add-competition-payment"
                        ,"/api/v1/company-competition/extend-competition"
                        ,"/api/v1/company-competition/update-competition"
                        ,"/api/v1/company-competition/create-competition-financial-by-organizer"
                        ,"/api/v1/company-competition/create-competition-financial-by-vote"
                        ,"/api/v1/company-competition/create-competition-financial-interview"
                        ,"/api/v1/company-competition/create-competition-interview"
                        ,"/api/v1/competition-payment/get-my-competition-payment"
                        ,"/api/v1/submission/company/accept/feedback/request/{submission_id}"
                        ,"/api/v1/submission/company/reject/feedback/request/{submission_id}"
                        ,"/api/v1/submission/company/select/winner/{competition_id}/{submission_id}"
                        ,"/api/v1/submission/company/view/my-competition/submissions/{competition_id}"
                        ,"/api/v1/competition/company/get/my-competition/reviews/{competition_id}"
                        ,"/api/v1/company-competition/cancel-competition/{companyCompetitionId}").hasAuthority("COMPANY")
                .requestMatchers(
                        "/api/v1/monthlyDraw/get-all-monthly-draws", "/api/v1/monthlyDraw/eligible","/api/v1/monthlyDraw/findMonthlyDrawByPrize/{prize}",
                        "/api/v1/monthlyDraw/findDrawsByName/{name}","/api/v1/monthlyDraw/findMonthlyDrawWinner/{drawId}",
                        "/api/v1/competition/get-all-competition"
                        ,"/api/v1/participant/update/{id}"
                        ,"/api/v1/participant/get/my-achievements"
                        ,"/api/v1/participant/get/my-feedbacks"
                        ,"/api/v1/participant/send-complain"
                        ,"/api/v1/submission/submit/{competition_id}"
                        ,"/api/v1/submission/get/my-submissions"
                        ,"/api/v1/submission/request-feedback/{submission_id}"
                        ,"/api/v1/votes/vote/{submission_id}",
                        "/api/v1/monthly-draw-participant/add/{monthlyDrawId}"
                        ,"/api/v1/category/add/category/to/participant/{category_id}",
                        "/api/v1/competition/add-review/{competition_id}").hasAuthority("PARTICIPANT")

                .requestMatchers(
                        "/api/v1/subscription-package/get-subscription-package-by-id/{id}"
                        ,"/api/v1/individual-organizer/get-profile","/api/v1/individual-organizer/send-complain"
                ,"/api/v1/individual-organizer/update",
                        "/api/v1/individual-competition/extend-competition","/api/v1/individual-competition/add-competition-payment",
                        "/api/v1/individual-competition/get-my-competitions",
                        "/api/v1/individual-competition/update","/api/v1/individual-competition/add",
                        "/api/v1/individual-competition/get-competitions-by-status/{status}"
                        ,"/api/v1/submission/individual/accept/feedback/request/{submission_id}"
                        ,"/api/v1/submission/individual/reject/feedback/request/{submission_id}"
                        ,"/api/v1/submission/individual/select/winner/{competition_id}/{submission_id}"
                        ,"/api/v1/submission/individual/view/my-competition/submissions/{competition_id}"
                        ,"/api/v1/competition/individual/get/my-competition/reviews/{competition_id}"
                        ,"/api/v1/individual-competition/select/winner/"
                        ,"/api/v1/individual-competition/cancel-competition/{individualCompetitionId}").hasAuthority("INDIVIDUAL")
                .requestMatchers(
                        "/api/v1/subscription-package/active-subscription-package/{id}"
                        ,"/api/v1/subscription-package/add-subscription-package"
                        ,"/api/v1/subscription-package/deactivated-subscription-package/{id}"
                        ,"/api/v1/subscription-package/get-subscription-package-by-id/{id}"
                        ,"/api/v1/subscription-package/update-subscription-package"
                        ,"/api/v1/monthly-subscription/get-all-monthly-subscription"
                        ,"/api/v1/category/upload-category"
                        ,"/api/v1/monthlyDraw/add",
                        "/api/v1/monthlyDraw/update",
                        "/api/v1/monthly-draw-participant/get-all-monthly-draw-" ,
                        "/api/v1/monthly-draw-participant/participants/{drawId}"
                        ,"api/v1/admin/active-company-user/{id}"
                        ,"api/v1/admin/detective-company-user/{id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/subscription-package/get-all-subscription-package").hasAnyAuthority("ADMIN","INDIVIDUAL","COMPANY")
                .requestMatchers("/api/v1/monthly-subscription/subscribe/{subscriptionPackageId}"
                        ,"/api/v1/monthly-subscription/renew-subscription/{subscriptionPackageId}"
                        ,"/api/v1/monthly-subscription/get-my-monthly-subscription").hasAnyAuthority("INDIVIDUAL","COMPANY")
                .anyRequest().hasAuthority("ADMIN")
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

        //.requestMatchers().hasAuthority("USER")
    }
}