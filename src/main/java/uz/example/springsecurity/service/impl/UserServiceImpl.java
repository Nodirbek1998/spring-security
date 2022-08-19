package uz.example.springsecurity.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import uz.example.springsecurity.entity.User;
import uz.example.springsecurity.repository.UserRepository;
import uz.example.springsecurity.security.UserNotFoundException;
import uz.example.springsecurity.service.UserService;
import uz.example.springsecurity.service.dto.UserDTO;
import uz.example.springsecurity.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserDTO> getAll(Pageable pageable, MultiValueMap<String , String> queryParams) {
        log.info("User get all");
        log.error(queryParams.getFirst("username"));
        return userRepository.findAll(pageable).stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = userMapper.toEntity(userDTO);
            userRepository.save(user);
        }else {
            throw new UserNotFoundException("User not found" + id +" ind database");
        }
        return userDTO;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getOne(Long id) {
        return userMapper.toDto(userRepository.findById(id).get());
    }
}
