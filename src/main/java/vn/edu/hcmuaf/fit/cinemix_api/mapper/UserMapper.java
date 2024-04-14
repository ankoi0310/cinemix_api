package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.MovieDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserUpdate;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Movie;

@Mapper
public interface UserMapper {
    @Named("toDTO")
    @Mapping(target = "roleName", source = "appRole.name")
    @Mapping(target = "fullName", source = "userInfo.fullName")
    @Mapping(target = "birthday", source = "userInfo.birthday")
    @Mapping(target = "avatar", source = "userInfo.avatar")
    UserDTO toDTO(AppUser user);


}
