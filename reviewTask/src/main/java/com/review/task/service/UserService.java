package com.review.task.service;

import com.review.task.entity.User;
import com.review.task.proxy.request.LoginReqProxy;
import com.review.task.proxy.request.UserReqProxy;
import com.review.task.proxy.request.UserUpdateReqProxy;
import com.review.task.proxy.response.LoginResProxy;
import com.review.task.proxy.response.UserResProxy;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseEntity<Void> createUser(UserReqProxy userReqProxy);

    ResponseEntity<Void> updateUser(Long id, UserUpdateReqProxy userReqProxy);

    ResponseEntity<Void> deleteUser(Long id);

    ResponseEntity<User> getUserById(Long id);

    Page<User> getPaginatedUser(int pageNo, int pageSize);

    ResponseEntity<Void> uploadProfile(Long id, MultipartFile profile);

    ResponseEntity<Void> createFakeUser(Integer noOfUser);

    ResponseEntity<LoginResProxy> login(LoginReqProxy login);

    ResponseEntity<UserResProxy> getUser(String userName);

    Page<User> getPaginatedUserWithName(int pageNo, int pageSize, String name);
}
