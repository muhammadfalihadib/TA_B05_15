package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    UserModel updateUser(UserModel user);
    void deleteUser(UserModel user);
//    List<UserModel> getListUser();
    UserModel getUserById(Long id);
    String encrypt(String password);
    Boolean isValidPassword(String password);
}
