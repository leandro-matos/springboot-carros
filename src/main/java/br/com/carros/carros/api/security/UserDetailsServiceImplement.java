package br.com.carros.carros.api.security;

import br.com.carros.carros.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.carros.carros.domain.UserRepository;

@Service(value = "userDetailsService")
public class UserDetailsServiceImplement implements UserDetailsService {

	@Autowired
	private UserRepository userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		
		return user;
	}
}
