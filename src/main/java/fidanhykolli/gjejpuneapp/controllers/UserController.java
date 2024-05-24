package fidanhykolli.gjejpuneapp.controllers;


import fidanhykolli.gjejpuneapp.entities.User;
import fidanhykolli.gjejpuneapp.payloads.NewUserDTO;
import fidanhykolli.gjejpuneapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UserController {
    @Autowired
    UserServices userServices;

    @GetMapping
    public List<User> getAllUsers(){
        return userServices.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id){
        return userServices.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody NewUserDTO body){
        return userServices.createUser(body);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User body){
        return userServices.updateUser(id, body);
    }
}
