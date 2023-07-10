package com.agroniks.marketplace.user;

import com.agroniks.marketplace.user.jpa.UserCommand;
import com.agroniks.marketplace.user.jpa.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("")
    public ResponseEntity<Iterable<UserEntity>> getAllUsers(Pageable pageable) {
        Page<UserEntity> page = userEntityService.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))
                )).get();
        return ResponseEntity.of(Optional.of(page.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.of(userEntityService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<UUID> saveNewUser(@RequestBody UserCommand user) {
        UserEntity newUser = userEntityService.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri()).build();
    }

    @PostMapping("{userID}/items/{itemId}/amount/{amount}")
    public ResponseEntity<UserEntity> buySomeAssets(@PathVariable UUID userID, @PathVariable UUID itemId, @PathVariable Integer amount) {
        UserEntity modifiedUser = userEntityService.buyAsset(userID, itemId, amount);
        return ResponseEntity.accepted().body(modifiedUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUserInfo(@PathVariable("id") UUID id, @RequestBody UserCommand userCommand) {
        UserEntity newUser = userEntityService.updateById(id, userCommand);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUserById(@RequestParam UUID id) {
        // TODO: add X-MESSAGE header
        userEntityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
