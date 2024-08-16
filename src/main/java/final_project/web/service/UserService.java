package final_project.web.service;

import final_project.web.entity.User;
import final_project.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken!");
        }
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        System.out.println("Attempting login for email: " + email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("User found: " + user.get().getEmail());
            if (user.get().getPassword().equals(password)) {
                System.out.println("Password match");
                return user.get();
            } else {
                System.out.println("Password mismatch");
            }
        } else {
            System.out.println("No user found with email: " + email);
        }
        throw new RuntimeException("Invalid email or password");
    }
}