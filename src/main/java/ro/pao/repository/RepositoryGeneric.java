package ro.pao.repository;

import ro.pao.exceptions.ObjectNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryGeneric<T> {
    Optional<T> getObjectById(UUID id) throws SQLException, ObjectNotFoundException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, T newObject);

    void addNewObject(T newObject) throws SQLException;

    List<T> getAll();

    void addAllFromGivenList(List<T> objectList) throws SQLException;
}
