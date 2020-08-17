package edu.poly.fpt.services;

import edu.poly.fpt.models.Depart;

import java.util.List;
import java.util.Optional;

public interface DepartService {

    List<Depart> findAll();

    Depart save(Depart entity);

    List<Depart> saveAll(List<Depart> entities);

    Optional<Depart> findById(Integer integer);

    List<Depart> findByNameLikeOrderByName(String name);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Depart depart);

    void deleteAll(List<Depart> list);

    void deleteAll();
}
