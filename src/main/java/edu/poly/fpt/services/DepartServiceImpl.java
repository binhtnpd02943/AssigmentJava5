package edu.poly.fpt.services;

import edu.poly.fpt.models.Depart;
import edu.poly.fpt.repositories.DepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartServiceImpl implements DepartService {
    @Autowired
    private DepartRepository departRepository;

    public List<Depart> findAll() {
        return departRepository.findAll();
    }

    public Depart save(Depart entity) {
        return departRepository.save(entity);
    }

    public List<Depart> saveAll(List<Depart> entities) {
        return departRepository.saveAll(entities);
    }

    public Optional<Depart> findById(Integer integer) {
        return departRepository.findById(integer);
    }

    public List<Depart> findByNameLikeOrderByName(String name) {
        return departRepository.findByNameLikeOrderByName("%"+ name + "%");
    }

    public boolean existsById(Integer integer) {
        return departRepository.existsById(integer);
    }

    public long count() {
        return departRepository.count();
    }

    public void deleteById(Integer integer) {
        departRepository.deleteById(integer);
    }

    public void delete(Depart depart) {
        departRepository.delete(depart);
    }

    public void deleteAll(List<Depart> list) {
        departRepository.deleteAll(list);
    }

    public void deleteAll() {
        departRepository.deleteAll();
    }
}
