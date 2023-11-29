package com.example.demo.Services;

import com.example.demo.Domain.Ubudehe;
import com.example.demo.Domain.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository repo;

    public List<User> listAll(){
        return repo.findAll();
    }

    public void save(User user){
        repo.save(user);
    }

    public User get(Long id){
        return repo.findById(id).get();
    }

    public void delete(Long id){
        repo.deleteById(id);
    }
    @Transactional
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }
    public boolean appUserEmailExists(String email){
        return findByEmail(email).isPresent();
    }

public User findUserById(Long id){
    return repo.findById(id).get();
}

}
