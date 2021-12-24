package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.UserModel;

import java.util.List;

public interface UserService {
    void addUser(UserModel user);
    UserModel updateUser(UserModel user);
    void deleteUser(UserModel user);
    List<UserModel> getListUser();
    UserModel getUserById(Integer id);
    String encrypt(String password);
    List<String> isValidPassword(String password);
    Boolean isValidUsername(String username);
    UserModel findByUsername(String username);
    Boolean hasAuthority(String authorityName);
}
