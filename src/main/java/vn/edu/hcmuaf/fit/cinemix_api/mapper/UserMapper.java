package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;

@Mapper
public interface UserMapper {
    @Mapping(target = "fullName", source = "userInfo.fullName")
    @Mapping(target = "role", source = "appRole.role")
    UserProfileDTO toUserProfileDTO(AppUser appUser);
}
