package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.jwt.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

////    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndpoint(){
        return "Hello User!";
    }
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/admin")
//    public String aminEndpoint(){
//        return "Hello Admin!";
//    }

//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
//        Authentication authentication = null;
//        try {
//            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        } catch (AuthenticationException exception){
//            Map<String, Object> map = new HashMap<>();
//            map.put("message", "Bad credentials");
//            map.put("status",false);
//            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
//        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String jwtToken = jwtUtils.generateTokenFromUserName(userDetails);
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        LoginResponse loginResponse = new LoginResponse(userDetails.getUsername(),jwtToken,roles);
//        return ResponseEntity.ok(loginResponse);
//    }
}
