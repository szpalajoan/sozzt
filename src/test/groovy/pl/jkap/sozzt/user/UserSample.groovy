package pl.jkap.sozzt.user

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

trait UserSample {
    UsernamePasswordAuthenticationToken MONIKA_CONTRACT_INTRODUCER = createUser("Monika", "ROLE_CONTRACT_INTRODUCER")
    UsernamePasswordAuthenticationToken DAREK_PRELIMINARY_PLANER = createUser("Darek", "ROLE_PRELIMINARY_PLANER")
    UsernamePasswordAuthenticationToken MARCIN_TERRAIN_VISIONER = createUser("Marcin", "ROLE_TERRAIN_VISIONER")
    UsernamePasswordAuthenticationToken WALDEK_SURVEYOR = createUser("Waldek", "ROLE_SURVEYOR")
    UsernamePasswordAuthenticationToken KASIA_CONSENT_CORDINATOR = createUser("Kasia", "ROLE_CONSENT_COORDINATOR")
    UsernamePasswordAuthenticationToken IWONA_CONSENT_COLLECTOR = createUser("Iwona", "ROLE_CONSENT_COLLECTOR")
    UsernamePasswordAuthenticationToken DANIEL_ROUTE_DRAWER = createUser("Daniel", "ROUTE_DRAWER")
    UsernamePasswordAuthenticationToken WOJTEK_DESIGNER = createUser("Wojtek", "ROLE_DESIGNER")


    UsernamePasswordAuthenticationToken createUser(String name, String role) {
        User user = new User(name, new Random().nextLong().toString(), List.of(new SimpleGrantedAuthority(role)))
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
    }

    void loginUser(UsernamePasswordAuthenticationToken user){
        SecurityContextHolder.getContext().setAuthentication(user)
    }

}