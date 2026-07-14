package io.github.vonner04.contact_game.repository;

import java.util.Collection;
import java.util.Optional;

/**
 * A generic storage for domain classes/objects that need to be saved, retrieved,
 * checked, deleted and listed.
 * 
 * It exists so that the application does not depend directly on a specific storage
 * implementation. So that later on I can potentially consider some level of persistence 
 * when saving domain objects. 
 * 
 * @param <T> The type of object being stored
 * @param <ID> The object's unique identifier
 */
public interface Store<T, ID> {
 
    void save(T item);
    Optional<T> findById(ID id);
    void deleteById(ID id);
    boolean existsById(ID id);
    Collection<T> findAll();

}
