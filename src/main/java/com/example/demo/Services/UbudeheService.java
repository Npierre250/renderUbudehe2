package com.example.demo.Services;

import com.example.demo.Domain.Ubudehe;
import com.example.demo.Repositories.UbudeheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbudeheService {

    @Autowired
    private UbudeheRepository repo;

    public List<Ubudehe> listAll(){
        return repo.findAll();
    }

    public void save(Ubudehe incident){
        repo.save(incident);
    }

    public Ubudehe get(Long id){
        return repo.findById(id).get();
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

}
