package manajero.xp.manajeroxpmethodology.Services;

import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.User;
import manajero.xp.manajeroxpmethodology.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEmail(userDetails.getEmail());
            user.setMatriculate(userDetails.getMatriculate());
            user.setResetPassword(userDetails.isResetPassword());
            user.setFailedAttempts(userDetails.getFailedAttempts());
            user.setLastName(userDetails.getLastName());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setTasks(userDetails.getTasks());

            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Map<String, Integer> getUserActivityMetrics(String userId) {
        User user = userRepository.findUserWithTasks(userId);
        Map<String, Integer> metrics = new HashMap<>();
        metrics.put("Total Tasks", user.getTasks().size());
        metrics.put("Completed Tasks", (int) user.getTasks().stream()
                .filter(task -> task.getStatus() == Status.DONE)
                .count());
        return metrics;
    }
}

