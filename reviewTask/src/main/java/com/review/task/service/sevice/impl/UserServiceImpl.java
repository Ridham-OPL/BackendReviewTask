package com.review.task.service.sevice.impl;

import com.github.javafaker.Faker;
import com.review.task.entity.User;
import com.review.task.enums.Gender;
import com.review.task.enums.Role;
import com.review.task.exception.RecordNotFoundException;
import com.review.task.proxy.request.LoginReqProxy;
import com.review.task.proxy.request.UserReqProxy;
import com.review.task.proxy.request.UserUpdateReqProxy;
import com.review.task.proxy.response.LoginResProxy;
import com.review.task.proxy.response.UserResProxy;
import com.review.task.repository.UserRepository;
import com.review.task.service.UserService;
import com.review.task.utils.Helper;
import com.review.task.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Helper helper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Void> createUser(UserReqProxy userReqProxy) {
        User user = helper.convert(User.class, userReqProxy);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User created successfully with id : {}", savedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> updateUser(Long id, UserUpdateReqProxy userReqProxy) {
        User userToUpdate = userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        userToUpdate.setName(userReqProxy.getName());
        userToUpdate.setDob(userReqProxy.getDob());
        userToUpdate.setGender(Gender.valueOf(userReqProxy.getGender()));
        userToUpdate.setContactNumber(userReqProxy.getContactNumber());
        userToUpdate.setAddress(userReqProxy.getAddress());
        userToUpdate.setPinCode(userReqProxy.getPinCode());
        userRepository.save(userToUpdate);
        log.info("User updated successfully with id : {}", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("User deleted successfully with id : {}", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return null;
    }

    @Override
    public Page<User> getPaginatedUser(int pageNo, int pageSize) {
        log.info("Pagination called for page {} of {}", pageNo, pageSize);
        return userRepository.findAllByAccessRole(Role.USER,
                PageRequest.of(pageNo - 1, pageSize)
        );
    }

    @Override
    public Page<User> getPaginatedUserWithName(int pageNo, int pageSize, String name) {
        log.info("Pagination called for name {} page {} of {}", name, pageNo, pageSize);
        return userRepository.findAllByNameAndAccessRole(name,Role.USER,
                PageRequest.of(pageNo - 1, pageSize)
        );
    }

    @Override
    public ResponseEntity<Void> uploadProfile(Long id, MultipartFile profile) {
        User userToUpdate = userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        String fileName = helper.generateFileName(profile.getOriginalFilename());
        try {
            String url = new ClassPathResource("").getFile().getAbsolutePath() + File.separator + "static/document";
//            String url = "src/main/resources/static/document";
            File imageFile = new File(url);
            if (!imageFile.exists())
                imageFile.mkdirs();
            String profileUrl = url + File.separator + fileName;
            Files.copy(profile.getInputStream(), Paths.get(profileUrl), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        String fullPath = "C:\\Users\\RidhamPatel\\IdeaProjects\\reviewTask\\target\\classes\\static\\document\\" + fileName;
        userToUpdate.setProfileImage(fullPath);
        userRepository.save(userToUpdate);
        log.info("User profile successfully updated : {}", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<UserResProxy> getUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(RecordNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(helper.convert(UserResProxy.class, user));
    }

    @Override
    public ResponseEntity<LoginResProxy> login(LoginReqProxy login) {
        log.info("login request received for username {}", login.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Authentication authResult = authenticationManager.authenticate(auth);
        if (authResult.isAuthenticated()) {
            String token = jwtUtils.generateToken(login.getUsername(), authResult.getAuthorities());
            LoginResProxy userProxy = new LoginResProxy(login.getUsername(), token, authResult.getAuthorities().toString());
            return ResponseEntity.ok().body(userProxy);
        }
        return null;
    }

    @Override
    public ResponseEntity<Void> createFakeUser(Integer noOfUser) {
        List<User> fakeUsers = generateUsers(noOfUser);
        userRepository.saveAll(fakeUsers);
        log.info("{} fake Users are created successfully.", noOfUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private List<User> generateUsers(int numOfStudents) {
        List<User> students = new ArrayList<>();
        for (int i = 0; i < numOfStudents; i++) {
            Faker faker = new Faker();
            String name = faker.name().firstName();
            String lastName = faker.name().lastName();
            String username = name + "." + lastName + "@gmail.com";
            students.add(
                    new User(name,
                            username,
                            passwordEncoder.encode(username),
                            faker.date().birthday(18, 58),
                            Gender.MALE,
                            faker.address().fullAddress(),
                            faker.phoneNumber().cellPhone(),
                            faker.address().zipCode(),
                            Role.USER));
        }
        return helper.convertList(User.class, students);
    }
}
