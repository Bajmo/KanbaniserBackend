package me.bajmo.kanbaniser.services;

import lombok.AllArgsConstructor;
import me.bajmo.kanbaniser.entities.User;
import me.bajmo.kanbaniser.exceptions.UserNotFoundException;
import me.bajmo.kanbaniser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!"));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setImage(updatedUser.getImage());
        userRepository.save(user);
    }

    public JpaRepository<User, Long> getUserRepository() {
        return this.userRepository;
    }

}