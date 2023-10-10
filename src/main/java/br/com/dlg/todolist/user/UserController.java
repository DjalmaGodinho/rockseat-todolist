package br.com.dlg.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository repository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        Optional<UserModel> user = this.repository.findByUserName(userModel.getUserName());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User exists!");
        }

        userModel = this.repository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
