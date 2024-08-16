//package final_project.web.controller;
//
//import final_project.web.config.UserLoginRequest;
//import final_project.web.entity.User;
//import final_project.web.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/users")
//public class AuthenticationController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
//        User user = userService.findByEmail(request.getEmail());
//        if (user != null && userService.checkPassword(user, request.getPassword())) {
//            // Generate a JWT token or handle the successful login case
//            return ResponseEntity.ok("Login successful");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//    }
//}
