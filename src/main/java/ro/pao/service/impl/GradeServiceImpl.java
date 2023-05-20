package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Grade;
import ro.pao.repository.GradeRepository;
import ro.pao.repository.impl.GradeRepositoryImpl;
import ro.pao.service.GradeService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class GradeServiceImpl implements GradeService {
    private static final GradeRepository gradeRepository = new GradeRepositoryImpl();

    @Override
    public Optional<Grade> getById(UUID id) {
        try {
            return gradeRepository.getObjectById(id);
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Grade> getAllItems() {
        return gradeRepository.getAll();
    }

    @Override
    public void addOnlyOne(Grade newObject) {
        try {
            gradeRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Grade> objectList) {
        try {
            gradeRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void removeById(UUID id) {
        gradeRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Grade newObject) {
        gradeRepository.updateObjectById(id, newObject);
    }
}
