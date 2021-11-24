package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDb userDb;

    @Override
    public void addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        userDb.save(user);
    }

    @Override
    public UserModel updateUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public void deleteUser(UserModel user) {
        userDb.delete(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordHash = passwordEncoder.encode(password);
        return passwordHash;
    }

    @Override
    public Boolean isValidPassword(String password) {
        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (password.length() < 8 ||
                !specailCharPatten.matcher(password).find() ||
                !UpperCasePatten.matcher(password).find() ||
                !lowerCasePatten.matcher(password).find() ||
                !digitCasePatten.matcher(password).find()
        ) return false;
        return true;
    }

    @Override
    public UserModel getUserById(Long id) {
        Optional<UserModel> user = userDb.findById(id);
        if (user.isPresent()){
            return user.get();
        } else {
            return null;
        }
    }
}
