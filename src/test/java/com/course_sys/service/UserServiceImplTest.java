package com.course_sys.service;


import com.course_sys.entity.User;
import com.course_sys.exception.AlreadyExistException;
import com.course_sys.exception.UserNotFoundException;
import com.course_sys.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private Authentication authentication;
    private User user1;

    private User user2;

    @BeforeEach
    public void setUp(){
        this.authentication = new UsernamePasswordAuthenticationToken("ulrikh.sergey@gmail.com", 12345);
        user1 = User.builder()
                .id(1)
                .firstname("Sergey")
                .lastname("Ulrikh")
                .email("ulrikh.sergey@gmail.com")
                .build();
        user2 = User.builder()
                .id(2)
                .firstname("Anton")
                .lastname("Antonov")
                .email("anton@gmail.com")
                .build();
    }
    @Test
    void getAllUsers() {
        given(userRepository.findAll()).willReturn(List.of(user1,user2));
        List<User> userList = userService.getAllUsers();
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
    }
    @Test
    void getAllUsersNegativeScenario() {
        given(userRepository.findAll()).willReturn(Collections.emptyList());
        List<User> userList = userService.getAllUsers();
        assertThat(userList).isEmpty();
    }

    @Test
    void saveUser() {
        given(userRepository.findById(user1.getId()))
                .willReturn(Optional.empty());

        given(userRepository.save(user1)).willReturn(user1);
        User savedUser = userService.saveUser(user1);
        System.out.println(savedUser);
        assertThat(savedUser).isNotNull();
    }
    @Test
    void saveUserWhenCourseAlreadyExist() {
        given(userRepository.findByEmail(user1.getEmail()))
                .willReturn(Optional.of(user1));

        assertThrows(AlreadyExistException.class, () -> userService.saveUser(user1));
    }

    @Test
    void getUser() {
        given(userRepository.findById(1)).willReturn(Optional.of(user1));
        User savedUser = userService.getUser(user1.getId());
        assertThat(savedUser).isNotNull();
    }

    @Test
    void getUserWhenUserNotFound() {
        given(userRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(1));
    }

    @Test
    void deleteUser() {
        int userId = 1;
        willDoNothing().given(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        verify(userRepository,times(1)).deleteById(userId);
    }

    @Test
    void updateFirstName() {
        String email = authentication.getName();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user1));
        given(userRepository.save(user1)).willReturn(user1);
        User user = userRepository.findByEmail(email).get();
        String firstname = user.getFirstname();
        user.setFirstname("Ivan");
        userRepository.save(user);
        assertThat(user.getFirstname()).isNotNull();
        assertThat(user.getFirstname()).isNotEqualTo(firstname);

    }

    @Test
    void updateLastName() {
        String email = authentication.getName();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user1));
        given(userRepository.save(user1)).willReturn(user1);
        User user = userRepository.findByEmail(email).get();
        String lastName = user.getLastname();
        user.setLastname("Ivan");
        userRepository.save(user);
        assertThat(user.getLastname()).isNotNull();
        assertThat(user.getLastname()).isNotEqualTo(lastName);
    }
}