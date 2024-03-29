package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    public List<String> isValidPassword(String password) {
        List<String> errorMessages = new ArrayList<>();
        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern upperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

//        if (password.length() < 8 ||
//                !specailCharPatten.matcher(password).find() ||
//                !UpperCasePatten.matcher(password).find() ||
//                !lowerCasePatten.matcher(password).find() ||
//                !digitCasePatten.matcher(password).find()
//        ) return false;
        if (password.length() < 8){
            errorMessages.add("Panjangnya harus lebih dari 8");
        } if (!specailCharPatten.matcher(password).find()){
            errorMessages.add("Harus mengandung simbol");
        } if (!upperCasePatten.matcher(password).find()){
            errorMessages.add("Harus mengandung huruf kapital");
        } if (!lowerCasePatten.matcher(password).find()){
            errorMessages.add("Harus mengandung huruf kecil");
        } if (!digitCasePatten.matcher(password).find()){
            errorMessages.add("Harus mengandung angka");
        }
        return errorMessages;
    }

    @Override
    public Boolean isValidUsername(String username) {
        if (userDb.findByUsername(username) != null) {
            return false;
        }
        return true;
    }

    @Override
    public UserModel getUserById(Integer id) {
        Optional<UserModel> user = userDb.findById(id);
        if (user.isPresent()){
            return user.get();
        } else {
            return null;
        }
    }

    @Override
    public List<UserModel> getListUser() { return userDb.findAll();}

    @Override
    public UserModel findByUsername(String username){
       return userDb.findByUsername(username);
    }

    @Override
    public Boolean hasAuthority(String authorityName) {
        GrantedAuthority authority = new SimpleGrantedAuthority(authorityName);
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .contains(authority);
    }
}
