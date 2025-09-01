package br.edu.ifba.saj.fwads.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import br.edu.ifba.saj.fwads.model.AbstractEntity;

public class Repository<T extends AbstractEntity> {

    private static final EntityManagerFactory sessionFactory;

    static {
        try {
            sessionFactory = Persistence.createEntityManagerFactory("jpa");
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private final Class<T> entityClass;

    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
        runImport();
    }

    //executa o import.sql nos casos do hibernate update
    private void runImport() {
        try {
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();

            String sql = new String(Files.readAllBytes(Paths.get("src/main/resources/import.sql")),
                    StandardCharsets.UTF_8);

            for (String command : sql.split(";")) {
                if (!command.trim().isEmpty()) {
                    entityManager.createNativeQuery(command.trim()).executeUpdate();
                }
            }

            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }

    }

    public T create(T entity) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entity;
    }

    public T read(T entity) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        return entityManager.find(entityClass, entity.getId());
    }

    public List<T> findAll() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        return entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t").getResultList();
    }

    public List<T> findByMap(Map<String, Object> map) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        StringBuilder query = new StringBuilder("Select t from " + entityClass.getSimpleName() + " t where 1=1 ");
        if (!map.keySet().isEmpty()) {
            map.forEach((k, v) -> {
                query.append(" AND t." + k + " = :" + k);
            });
        }
        Query q = entityManager.createQuery(query.toString());
        if (!map.keySet().isEmpty()) {
            map.forEach((k, v) -> {
                q.setParameter(k, v);
            });
        }
        return q.getResultList();
    }

    public T update(T entity) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entity;
    }

    public void delete(T entity) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Long count() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(entityClass)));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
