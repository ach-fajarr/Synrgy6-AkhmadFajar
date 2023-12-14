package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.request.UserRegistrationRequest;
import id.achfajar.challenge7.exception.ResourceNotFoundException;
import id.achfajar.challenge7.model.ERole;
import id.achfajar.challenge7.model.Role;
import id.achfajar.challenge7.model.Users;
import id.achfajar.challenge7.dto.UserDTO;
import id.achfajar.challenge7.repository.RoleRepository;
import id.achfajar.challenge7.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO createUser(UserRegistrationRequest userRegistrationRequest) {
        Optional<Users> users = usersRepository.findByUsername(userRegistrationRequest.getUsername());
        if (users.isPresent()
                && users.get().getIsDeleted()
                && users.get().getRoles().stream().findFirst().get().getName()==ERole.DELETED)
        {
            users.get().setRoles(findRoles(userRegistrationRequest.getRoles()));
            users.get().setIsDeleted(false);
            usersRepository.save(users.get());
            return mapToDTO(users.get());
        } else {
            Users user = mapToEntity(userRegistrationRequest);
            user = usersRepository.save(user);
            return mapToDTO(user);
        }
    }

    @Override
    public UserDTO userInfo(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException(username));
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUser(String username, UserRegistrationRequest userRegistrationRequest) {
        Users existingUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        Users updatedUser = mapToEntity(userRegistrationRequest);
        updatedUser.setId(existingUser.getId());
        updatedUser = usersRepository.save(updatedUser);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        user.setIsDeleted(true);
        user.setRoles(findRoles(Collections.singleton(ERole.DELETED)));
        usersRepository.save(user);
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
    public Users getUsersByUsername(String name) {
        return usersRepository.findByUsername(name)
                .orElseThrow(() -> new ResourceNotFoundException(name));
    }

    private UserDTO mapToDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        Set<ERole> set = new HashSet<>();
        for (Role role : user.getRoles()) {
            ERole name = role.getName();
            String named = name.name();
            set.add(ERole.valueOf(named));
        }
        userDTO.setRoles(set);
        return userDTO;
    }

    private Users mapToEntity(UserRegistrationRequest userRegistrationRequest) {
        Users user = new Users();
        user.setEmail(userRegistrationRequest.getEmail());
        user.setUsername(userRegistrationRequest.getUsername());
        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setLastName(userRegistrationRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user.setRoles(findRoles(userRegistrationRequest.getRoles()));
        return user;
    }

    private Set<Role> findRoles(Set<ERole> role){
        Set<Role> roles = new HashSet<>();
        for (ERole roleName : role) {
            Role r = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(r);
        }
        return roles;
    }
}

