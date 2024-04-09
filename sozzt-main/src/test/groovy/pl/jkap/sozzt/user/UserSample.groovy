package pl.jkap.sozzt.user

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

trait UserSample {
    UsernamePasswordAuthenticationToken MONIKA_CONTRACT_INTRODUCER = createUser("Monika", "CONTRACT_INTRODUCER")

    UsernamePasswordAuthenticationToken createUser(String name, String role) {
        User user = new User(name, new Random().nextLong().toString(), List.of(new SimpleGrantedAuthority(role)))
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
    }

    void loginUser(UsernamePasswordAuthenticationToken user){
        SecurityContextHolder.getContext().setAuthentication(user)
    }

}