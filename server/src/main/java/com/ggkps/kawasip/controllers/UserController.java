package com.ggkps.kawasip.controllers;

import com.ggkps.kawasip.config.MessageBroker.MessageSender;
import com.ggkps.kawasip.entities.User;
import com.ggkps.kawasip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Objects;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    private MessageSender messageSender;

    //GET
    @GetMapping()
    public ResponseEntity<String> getUserInfo(Authentication authentication, @RequestParam String username) {
        // Récupérer l'utilisateur actuel basé sur l'authentification
        User currentUser = (User) authentication.getPrincipal();

        // Comparer le token avec le nom d'utilisateur
        if (username.equals(currentUser.getId().toString())) {
            return ResponseEntity.ok("User info for: " + currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
    }

    //PUT
    @PutMapping()
    public ResponseEntity<String> updateUser(Authentication authentication, @RequestParam String username, @RequestBody User updatedUser) {
        // Récupérer l'utilisateur actuel basé sur l'authentification
        User currentUser = (User) authentication.getPrincipal();

        // Comparer le token avec le nom d'utilisateur
        if (username.equals(currentUser.getId().toString())) {
            // Check if Email change is Email already used
            if(userRepository.findByEmail(updatedUser.getEmail()).isEmpty() && !Objects.equals(currentUser.getEmail(), updatedUser.getEmail())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already exists.");
            }
            // Mettre à jour les informations de l'utilisateur
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setCity(updatedUser.getCity());
            currentUser.setLastname(updatedUser.getLastname());
            currentUser.setFirstname(updatedUser.getFirstname());
            currentUser.setPostalCode(updatedUser.getPostalCode());
            currentUser.setPassword(updatedUser.getPassword());
            currentUser.setCompanyName(updatedUser.getCompanyName());

            userRepository.save(currentUser);

            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
    }

    //DELETE
    @DeleteMapping()
    public ResponseEntity<String> deleteUser(Authentication authentication, @RequestParam String username) {
        // Récupérer l'utilisateur actuel basé sur l'authentification
        User currentUser = (User) authentication.getPrincipal();

        // Comparer le token avec le nom d'utilisateur
        if (username.equals(currentUser.getId().toString())) {

            // Suppression de l'utilisateur
            userRepository.deleteById(currentUser.getId());

            //Envoie un événement au message Broker
            messageSender.sendUserDelete(currentUser.getId());

            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
    }

}
