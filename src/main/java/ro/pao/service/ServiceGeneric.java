package ro.pao.service;


import ro.pao.exceptions.MaterialNotFoundException;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.exceptions.UserNotFoundException;
import ro.pao.model.Document;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ServiceGeneric<T> {
    Optional<T> getById(UUID id);

    List<T> getAllItems();

    void addOnlyOne(T newObject);

    void addMany(List<T> objectList);

    void removeById(UUID id);

    void updateById(UUID id, T newObject);
}
