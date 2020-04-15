package Journalr.com.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// This class is used for spring security authentication
public class UserDetailsClass implements UserDetails { 
	
	private int Id;
	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
    
    /**
     * A UserDetailsClass contructor that copies the values of a User object into it
     * @param user The user we want to build a user details class around
     */
	public UserDetailsClass(User user) {
		this.Id = user.getUserId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.active = user.isActive();
		this.authorities = Arrays.stream(user.getRoles().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
	}

    /**
     * This method gets the authority of the user.  Authority depends
     * on the role of the user
     * @return the authorities of the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * This method gets the id of the UserDetail
     * @return
     */
	public int getId() {
		return Id;
	}

    /**
     * This method gets the password of the user datail
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * This method retrieves the username of the user detail
     * @return the user name
     */
    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * This method always returns true when calling
     * is AccountNonExpired
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This method always returns true when calling
     * is AccountNonLocked
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

     /**
     * This method always returns true when calling
     * is CredentialsNonExpired
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * This method will return the active status of the user
     * when isEnabled is called
     * @return the active status of the user
     */
    @Override
    public boolean isEnabled() {
        return active;
    }

}
