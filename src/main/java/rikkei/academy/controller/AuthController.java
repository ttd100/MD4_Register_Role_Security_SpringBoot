package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rikkei.academy.dto.request.SignUpForm;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;
import rikkei.academy.service.role.IRoleService;
import rikkei.academy.service.user.IUserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpForm signUpForm){
        if (userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("username_existed"),HttpStatus.OK);
        }
        if (userService.existsByUsername(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("email_existed"),HttpStatus.OK);
        }
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role.toLowerCase()){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(()-> new RuntimeException(" not found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(()-> new RuntimeException(" not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(()-> new RuntimeException(" not found"));
                    roles.add(userRole);
            }
        });
        User user = new User(signUpForm.getName(),signUpForm.getUsername(),signUpForm.getEmail(),passwordEncoder.encode(signUpForm.getPassword()),roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("create_success"),HttpStatus.OK);

    }
}
