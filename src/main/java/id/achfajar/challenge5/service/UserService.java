package id.achfajar.challenge5.service;

import id.achfajar.challenge5.model.Users;
import id.achfajar.challenge5.model.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(UUID id);
    Users getUsersById(UUID id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UUID id, UserDTO userDTO);

    void deleteUser(UUID id);
}

