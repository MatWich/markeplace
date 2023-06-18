package com.agroniks.marketplace.user.jpa;

import com.agroniks.marketplace.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/persistence/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.of(userEntityService.findAll());
    }

    @GetMapping("{name}")
    public ResponseEntity<List<UserEntity>> getUsersByName(@PathVariable("name") String name) {
        return ResponseEntity.of(Optional.ofNullable(userEntityService.findByName(name)));
    }

    @PostMapping("")
    public ResponseEntity<UUID> saveNewUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userEntityService.save(userEntity))
                .toUri()).build();
    }

    @PostMapping("{userID}/items/{itemId}/amount/{amount}")
    public ResponseEntity<UserEntity> buySomeAssets(@PathVariable UUID userID, @PathVariable UUID itemId, @PathVariable Integer amount) {
        UserEntity modifiedUser = userEntityService.buyAsset(userID, itemId, amount);
        return ResponseEntity.accepted().body(modifiedUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUserInfo(@PathVariable("id") UUID id, @RequestBody User user) {
        userEntityService.updateById(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUserById(@RequestParam UUID id) {
        // TODO: add X-MESSAGE header
        userEntityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
