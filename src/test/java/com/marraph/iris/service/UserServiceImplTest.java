package com.marraph.iris.service;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.User;
import com.marraph.iris.repository.UserRepository;
import com.marraph.iris.service.implementation.organisation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate_UserDoesNotExist_ShouldCreateNewUser() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.exists(any(Example.class))).thenReturn(false);
        when(userRepository.findOne(any(Example.class))).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        CompletableFuture<User> result = userService.create(user);

        // Assert
        assertThat(result.get()).isNotNull();
        assertThat(result.get().getName()).isEqualTo("Test User");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetById_UserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(userId).join())
                .isInstanceOf(EntryNotFoundException.class);
    }

    @Test
    public void testDelete_UserExists_ShouldDeleteUser() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}