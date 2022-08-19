package uz.example.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uz.example.springsecurity.entity.User;
import uz.example.springsecurity.security.JwtTokenProvider;
import uz.example.springsecurity.service.LoginVm;
import uz.example.springsecurity.service.UserService;
import uz.example.springsecurity.service.dto.UserDTO;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;


    private final AuthenticationManager authenticationManager;

    private final UserService userService;


    @GetMapping
    public List<UserDTO> getAllStudents(Pageable pageable, @RequestParam MultiValueMap<String , String> queryParams){
        log.info("User get all in controller");
       return userService.getAll(pageable, queryParams);
    }

    @PostMapping
    public UserDTO create(@Valid @RequestBody UserDTO userDTO){
        log.info("User create in controller");
        return userService.create(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id){
        return ResponseEntity.ok(userService.update(userDTO, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id){
        log.info("Get One id = " + id);

       return ResponseEntity.status(202).body(userService.getOne(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginVm loginVm){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginVm.getLogin(),loginVm.getPassword()
            ));
            String token= jwtTokenProvider.generateToken((User)authentication.getPrincipal());
            return ResponseEntity.ok(token);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(401).body("Bad");
        }
    }
}
