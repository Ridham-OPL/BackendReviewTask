package com.review.task.controller;

import com.review.task.entity.User;
import com.review.task.proxy.request.LoginReqProxy;
import com.review.task.proxy.request.UpdatePassProxy;
import com.review.task.proxy.request.UserReqProxy;
import com.review.task.proxy.request.UserUpdateReqProxy;
import com.review.task.proxy.response.LoginResProxy;
import com.review.task.proxy.response.UserResProxy;
import com.review.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/user")
@CrossOrigin(value = "http://loclalhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResProxy> login(@RequestBody LoginReqProxy login) {
        return userService.login(login);
    }

    @PostMapping("/register")
     public ResponseEntity<Void> createUser(@RequestBody UserReqProxy user) {
        return userService.createUser(user);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserResProxy> getUserByUsername(@PathVariable String userName) {
        return userService.getUser(userName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserUpdateReqProxy user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/pagination/{page}/{size}")
    public Page<User> getPaginatedUser(@PathVariable int page, @PathVariable int size) {
        return userService.getPaginatedUser(page,size);
    }

    @GetMapping("/faker/{no}")
    public ResponseEntity<Void> getFakeUser(@PathVariable Integer no) {
        return userService.createFakeUser(no);
    }

    @GetMapping("/pagination/{name}/{page}/{size}")
    public Page<User> getPaginatedUserWithName(@PathVariable int page, @PathVariable int size,@PathVariable String name) {
        return userService.getPaginatedUserWithName(page,size,name);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> uploadProfile(@PathVariable Long id, @RequestParam("profilePic") MultipartFile profile) {
        return userService.uploadProfile(id, profile);
    }


    @GetMapping("/send-link/{username}")
    public ResponseEntity<Void> sendLink(@PathVariable String username) {
        return userService.sendLink(username);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody UpdatePassProxy updatePass) {
        return userService.resetPassword(updatePass);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadEmployee() {
        return userService.writeUserToExcel();
    }


}
