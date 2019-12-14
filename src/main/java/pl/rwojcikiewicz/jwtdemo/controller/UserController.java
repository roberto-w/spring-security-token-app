package pl.rwojcikiewicz.jwtdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info")
    public ResponseEntity getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();

        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities().stream()
                .map(authority -> ((GrantedAuthority) authority).getAuthority())
                .collect(toList())
        );

        return ok(model);
    }
}
