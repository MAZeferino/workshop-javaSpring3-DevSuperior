package com.udemynelio.course.services;

import com.udemynelio.course.entities.User;
import com.udemynelio.course.exceptions.ResourceNotFoundException;
import com.udemynelio.course.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User obj){
        return repository.save(obj);
    }

    public void delete(Long id){
        Integer rows = repository.delUser(id);
        if (rows == 0) throw new ResourceNotFoundException(id);
    }

    public User update(@PathVariable Long id, User user){
        try{
            User entity = repository.getReferenceById(id);
            updateData(entity, user);
            return repository.save(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }

    }

    private void updateData(User entity, User user) {
        entity.setName(user.getName() != null ? user.getName() : entity.getName());
        entity.setEmail(user.getEmail() != null ? user.getEmail() : entity.getEmail());
        entity.setPhone(user.getPhone() != null ? user.getPhone() : entity.getPhone());
    }
}
