package component.arquilliancourse.common;

import java.util.List;

public interface DAO<T> {
    List<T> findAll();
    T findById(String id);
    boolean insert(T obj);
    boolean update(T obj);
    boolean delete(T obj);
    boolean deleteById(String id);
}
