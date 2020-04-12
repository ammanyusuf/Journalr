package Journalr.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/adduser").hasRole("ADMIN")					// the admin can add users
				.antMatchers("/editUser").hasRole("ADMIN")
				.antMatchers("/", "/home", "/signUp", "/css/**", "/images/**").permitAll()             // permitted pages (no need login)
				.antMatchers("/author").hasAnyRole("AUTHOR", "ADMIN")         // admin can access these other pages
				.antMatchers("/authorHarry").hasAnyRole("AUTHOR", "ADMIN") 
				.antMatchers("/editpaper").hasAnyRole("AUTHOR", "ADMIN")
				.antMatchers("/downloadpaper").permitAll()
				.antMatchers("/uploadForm").hasAnyRole("AUTHOR", "ADMIN")
				.antMatchers("/reviewer/**").hasAnyRole("REVIEWER", "ADMIN")
				.antMatchers("/paperTopics").hasAnyRole("REVIEWER", "ADMIN")			
				.antMatchers("/editor").hasAnyRole("EDITOR", "ADMIN")
				.antMatchers("/editDeadline/**").hasAnyRole("EDITOR","ADMIN")
				.antMatchers("/reviewersperpaper").hasAnyRole("EDITOR", "ADMIN")
				.antMatchers("/user").hasAnyRole("USER", "ADMIN")
				.antMatchers("/upload.html").hasAnyRole("AUTHOR", "ADMIN")  // author upload paper page
				.antMatchers("/comment").hasAnyRole("REVIEWER", "ADMIN")    // reviewer comment on paper page
				.antMatchers("/add").permitAll()					// the admin can add users
				.antMatchers("/all").permitAll()					// the admin can see all users
				.antMatchers("/delete").permitAll()					// the admin can delete all users
				.antMatchers("/user/**").permitAll()
				.antMatchers("/update/**").permitAll()
				.anyRequest().authenticated()                                 // any url request before login would be redirected to login page, except the permitted ones
			.and()
				.csrf().disable()                                             // allows for post and get requests
				.formLogin()//.defaultSuccessUrl("/home");                        // use default login page and logout,  // if you use your own login page, you need to supply the logout page // default login page does not have sign up
				.loginPage("/login").defaultSuccessUrl("/home")               // after a successful login (user verified) , when the "/home" is called, a method in the login controller is executed to redirect the user to the appropriate page 
				.permitAll()                                            
			.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login") // when "/logout" is entered, and logout successful, go to "/login"
				.permitAll();
				
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {           //for password (set to cleartext(nothing) right now, just need this for it to work)
        return NoOpPasswordEncoder.getInstance();
	}
}
