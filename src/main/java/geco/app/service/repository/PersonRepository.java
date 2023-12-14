package geco.app.service.repository;

import geco.app.service.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAll();
    Person findByIdPerson(long id);
    Person save(Person person);
    @Query(value = "SELECT id_person FROM person ORDER BY id_person DESC LIMIT 1", nativeQuery = true)
    long findLastId();
 }
