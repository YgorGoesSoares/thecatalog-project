package com.project.thecatalog.TheCatalog.services;

import com.project.thecatalog.TheCatalog.dto.RoleDTO;
import com.project.thecatalog.TheCatalog.dto.UserDTO;
import com.project.thecatalog.TheCatalog.dto.UserInsertDTO;
import com.project.thecatalog.TheCatalog.entities.Role;
import com.project.thecatalog.TheCatalog.entities.User;
import com.project.thecatalog.TheCatalog.repositories.RoleRepository;
import com.project.thecatalog.TheCatalog.repositories.UserRepository;
import com.project.thecatalog.TheCatalog.services.exceptions.DatabaseException;
import com.project.thecatalog.TheCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {

        Page<User> list = userRepository.findAll(pageable);
        return list.map(x -> new UserDTO(x));

    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insertNewUser(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }



    @Transactional
    public UserDTO updateUser(Long id, UserDTO dto) {
        try {User entity = userRepository.getReferenceById(id);
        copyDtoToEntity(dto, entity);
        entity = userRepository.save(entity);
        return new UserDTO(entity);}
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation: " + e.getCause());
        }

    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found (" + id +")");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setEmail(dto.getEmail());


    entity.getRoles().clear();
    for(RoleDTO roleDTO : dto.getRoles()) {
        Role role = roleRepository.getReferenceById(roleDTO.getId());
        entity.getRoles().add(role);
    }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(username);
       if (user == null) {
           logger.error("User not found: " + username);
           throw new UsernameNotFoundException("E-mail not found.");
       }
       logger.info("User found: " + username);
       return user;
    }
}
