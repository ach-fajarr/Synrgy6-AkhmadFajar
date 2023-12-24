package id.achfajar.challenge8.service;

import id.achfajar.challenge8.dto.request.UserRegistrationRequest;
import id.achfajar.challenge8.model.Users;
import id.achfajar.challenge8.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserRegistrationRequest userRegistrationRequest);

    UserDTO userInfo(String username);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(String username, UserRegistrationRequest userRegistrationRequest);

    void deleteUser(String username);

    Users getUsersByUsername(String name);

    List<Users> getSubscribedUsers();
}

