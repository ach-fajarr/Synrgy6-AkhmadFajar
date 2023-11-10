package id.achfajar.challenge5.service;

import id.achfajar.challenge5.exception.ResourceNotFoundException;
import id.achfajar.challenge5.model.Users;
import id.achfajar.challenge5.model.dto.UserDTO;
import id.achfajar.challenge5.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (Users user : users) {
            userDTOs.add(mapToDTO(user));
        }

        return userDTOs;
    }

    @Override
    public UserDTO getUserById(UUID id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User dengan id: " + id + "tidak ditemukan"));
        return mapToDTO(user);
    }
    @Override
    public Users getUsersById(UUID id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User dengan id: " + id + "tidak ditemukan"));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Users user = mapToEntity(userDTO);
        user = usersRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        Users existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User dengan id: " + id + "tidak ditemukan"));

        Users updatedUser = mapToEntity(userDTO);
        updatedUser.setId(existingUser.getId());
        updatedUser = usersRepository.save(updatedUser);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User dengan id: " + id + "tidak ditemukan"));
        usersRepository.delete(user);
    }

    private UserDTO mapToDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    private Users mapToEntity(UserDTO userDTO) {
        Users user = new Users();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        return user;
    }
}

