package fidanhykolli.gjejpuneapp.services;

import fidanhykolli.gjejpuneapp.DAO.UserDAO;
import fidanhykolli.gjejpuneapp.entities.User;
import fidanhykolli.gjejpuneapp.enums.UserType;
import fidanhykolli.gjejpuneapp.exceptions.BadRequestException;
import fidanhykolli.gjejpuneapp.payloads.NewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    public UserDAO userDAO;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User getUserById(Integer id) {
        Optional<User> userOptional = userDAO.findById(id);
        return userOptional.orElse(null);
    }

    public User updateUser(Integer id, User updatedUser) {
        Optional<User> existingUserOptional = userDAO.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }

            if (updatedUser.getSurname() != null) {
                existingUser.setSurname(updatedUser.getSurname());
            }

            if (updatedUser.getAddress() != null) {
                existingUser.setAddress(updatedUser.getAddress());
            }

            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getDateOfBirth() != null) {
                existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            }

            if (updatedUser.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            }

            if (updatedUser.getWorkExperience() != null) {
                existingUser.setWorkExperience(updatedUser.getWorkExperience());
            }

            if (updatedUser.getSpokenLanguage() != null) {
                existingUser.setSpokenLanguage(updatedUser.getSpokenLanguage());
            }

            return userDAO.save(existingUser);
        }
        return null;
    }


    public User createUser(NewUserDTO newUserDTO) {
        String email = newUserDTO.email();
        if (userDAO.existsByEmail(email)) {
            throw new BadRequestException("Email " + email + " is already taken");
        }
        String encodedPassword = passwordEncoder.encode(newUserDTO.password());

        UserType newRole = newUserDTO.role().equals("User") ? UserType.User : UserType.Admin;

        System.out.println(encodedPassword);
        User user = new User(newUserDTO.name(), newUserDTO.surname(), newUserDTO.email(), encodedPassword, newUserDTO.hasWorkVisa(), newUserDTO.dateOfBirth(), newUserDTO.address(), newUserDTO.phoneNumber(), newUserDTO.workExperience(), newUserDTO.spokenLanguage(), newRole);


        user = userDAO.save(user);


        return user;
    }

    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}

