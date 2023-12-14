package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.request.UserRegistrationRequest;
import id.achfajar.challenge7.model.Users;
import id.achfajar.challenge7.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserRegistrationRequest userRegistrationRequest);

    UserDTO userInfo(String username);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(String username, UserRegistrationRequest userRegistrationRequest);

    void deleteUser(String username);

    Users getUsersByUsername(String name);

}

