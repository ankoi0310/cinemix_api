package vn.edu.hcmuaf.fit.cinemix_api.service.auth.user;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BadRequestException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserCreate;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserUpdate;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;

public interface UserService {

    public AppUser createNewUser(RegisterRequest request) throws Exception  ;
    public void lockUser(AppUser user);

    public AppUser findByEmail(String email);

    public void unlockUser(String email);

    public AppUser save(AppUser user);

    public AppUser login(LoginRequest request);

    public UserDTO createUser(UserCreate request) throws Exception ;

    public UserDTO updateUserById(Long id, UserUpdate userUpdate) throws Exception;

    public void deleteById(Long id) throws Exception;

}
