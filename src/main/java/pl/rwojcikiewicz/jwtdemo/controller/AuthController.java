package pl.rwojcikiewicz.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rwojcikiewicz.jwtdemo.model.User;
import pl.rwojcikiewicz.jwtdemo.security.JwtTokenProvider;
import pl.rwojcikiewicz.jwtdemo.service.UserDetailsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            String username = authenticationRequest.getUsername();
            String password = authenticationRequest.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = createToken(username);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    private String createToken(String username) {
        User user = (User) userDetailsService.loadUserByUsername(username);

        List<String> roles = user.getRoles();
        return jwtTokenProvider.createToken(username, roles);
    }

}
