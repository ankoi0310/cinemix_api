package vn.edu.hcmuaf.fit.cinemix_api.service.auth.user;

import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;

public interface UserService {

    public AppUser createNewUser(RegisterRequest request) throws Exception  ;
    public void lockUser(AppUser user);

    public AppUser findByEmail(String email);

    public void unlockUser(String email);

    public AppUser save(AppUser user);

    public AppUser login(LoginRequest request);

}
