package com.example.english.service;

import com.example.english.dao.model.UserEntity;
import com.example.english.dao.repository.UserRepository;
import com.example.english.dto.LoginDTO;
import com.example.english.dto.UserDTO;
import com.example.english.enums.BlockEnum;
import com.example.english.exception.BaseException;
import com.example.english.msg.Msg;
import com.example.english.security.JwtTokenProvider;
import com.example.english.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl extends AbstractBaseService<UserEntity, UserDTO, UserRepository> implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static ModelMapper modelMapper = null;

    private Long userId = null;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected UserRepository getRepository() {
        return null;
    }

    @Override
    protected void specificMapToDTO(UserEntity entity, UserDTO dto) {
        super.specificMapToDTO(entity, dto);
        dto.setCreatedName(getUsernameById(entity.getCreatedBy()));
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    protected Class<UserDTO> getDtoClass() {
        return UserDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public UserDTO create(UserDTO dto) {
        init();
        UserEntity entity = mapToEntity(dto);

        if (Objects.isNull(entity.getUsername())) {
            entity.setUsername(generateUsername(entity.getName()));
        } else {
            if (repository.existsByUsername(dto.getUsername())) {
                throw new BaseException(100, Msg.getMessage("Tồn tại username"));
            }
        }
        if (Objects.isNull(entity.getPassword())) {
            entity.setPassword(getHashPassword("123456", entity.getSalt()));
        } else {
            entity.setPassword(getHashPassword(entity.getPassword(), entity.getSalt()));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        init();
        UserEntity entity = getById(id);
        if (!entity.getEmail().equals(dto.getEmail()) && repository.findFirstByEmail(dto.getEmail()) != null) {
            throw new BaseException(1001, Msg.getMessage("Tồn tại tài khoản"));
        }
        entity.setName(dto.getName());
        entity.setGender(dto.getGender());
        entity.setEmail(dto.getEmail());
        entity.setAvatar(dto.getAvatar());
        repository.save(entity);
        return mapToDTO(entity);
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace('-', '_');
    }

    private String getHashPassword(String password, String salt) {
        if (salt == null) {
            salt = "";
        }

        return DigestUtils.sha256Hex(password + salt);
    }

    private String generateUsername(String name) {
        int index = 0;
        String nickName = StringUtil.deleteAccents(name).toLowerCase().replace(" ", "");
        while (repository.existsByUsername(nickName + (index == 0 ? "" : "" + index))) {
            index++;
        }
        return nickName + (index == 0 ? "" : "" + index);
    }

    @Override
    public String validateLogin(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if (username == null) {
            throw new BaseException(1, Msg.getMessage("Username null"));
        }

        if (password == null) {
            throw new BaseException(2, Msg.getMessage("Password null"));
        }

        UserEntity user = repository.findFirstByUsername(username.trim());
        if (!user.getPassword().equals(getHashPassword(password, user.getSalt()))) {
            throw new BaseException(3, Msg.getMessage("Sai password"));
        }

        if (user.getBlock() == BlockEnum.BLOCKED) {
            throw new BaseException(4, Msg.getMessage("Tai khoan da bi khoa"));
        }

        Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("avatar", user.getAvatar());
        additionalInformation.put("gender", user.getGender());
        additionalInformation.put("user_type", user.getUserType());
        user.setLoginToken(jwtTokenProvider.generateToken(user.getId()));
        return jwtTokenProvider.generateToken(user.getId());
    }

    @Override
    public UserDTO block(Long id) {
        init();
        UserEntity user = getById(id);

        user.setBlock(user.getBlock().equals(BlockEnum.BLOCKED) ? BlockEnum.UNBLOCKED : BlockEnum.BLOCKED);

        repository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO resetPassword(Long id) {
        init();
        UserEntity user = getById(id);

        user.setPassword(getHashPassword("123456", user.getSalt()));
        repository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO updatePassword(Long id, UserDTO userDto) {
        init();
        UserEntity entity = getById(id);
        if (!Objects.equals(entity.getPassword(), getHashPassword(userDto.getPassword(), entity.getSalt()))) {
            throw new BaseException(1001, "Tồn tại password");
        }
        String pasWordNew = userDto.getNewPassword();
        entity.setPassword(getHashPassword(pasWordNew, entity.getSalt()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public UserDTO updateEmail(Long id, UserDTO userDto) {
        init();
        UserEntity user = getById(id);

        if (user.getEmail().equals(userDto.getEmail())) {
            return mapToDTO(user);
        }

        if (!Objects.equals(user.getPassword(), getHashPassword(userDto.getPassword(), user.getSalt()))) {
            throw new BaseException(1004, Msg.getMessage("Tồn tại password"));
        }

        if (!user.getEmail().equals(userDto.getEmail()) && repository.findFirstByEmail(userDto.getEmail()) != null) {
            throw new BaseException(1002, Msg.getMessage("Tồn tại email"));
        }

        user.setEmail(userDto.getEmail());

        repository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateAvatar(Long id, UserDTO userDto) {
        init();
        UserEntity user = getById(id);
        user.setAvatar(userDto.getAvatar());

        repository.save(user);
        return mapToDTO(user);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
        init();
    }

    @Override
    public void init() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        logger.info("Token ... " +token);
        userId = JwtTokenProvider.getUserIdFromToken(token);
        logger.info("UserId ..... " +userId);


    }

    @Override
    public String getUsernameById(Long id) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        logger.info("Token ... " +token);
        id = JwtTokenProvider.getUserIdFromToken(token);
        return repository.getUsernameById(id);
    }
}
