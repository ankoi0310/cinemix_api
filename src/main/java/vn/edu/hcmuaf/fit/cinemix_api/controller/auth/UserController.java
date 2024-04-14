package vn.edu.hcmuaf.fit.cinemix_api.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.controller.exceptionHandle.ControllerExceptionHandler;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserCreate;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserUpdate;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.user.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends ControllerExceptionHandler {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<HttpResponse> addUser(@RequestBody UserCreate request) throws Exception {
        UserDTO response = userService.createUser(request);
        return ResponseEntity.ok().body(HttpResponse.success(response,"Add new user successfully, email ="+request.getEmail()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) throws Exception {
        UserDTO response = userService.updateUserById(id,userUpdate);
        return ResponseEntity.ok().body(HttpResponse.success(response,"Update user successfully, id="+id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteById(id);
        return ResponseEntity.ok().body(HttpResponse.success("Delete user successfully, id= "+id));
    }

}
