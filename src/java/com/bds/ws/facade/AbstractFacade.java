/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dao.util.JsfUtil;
import com.bds.ws.dto.RespuestaDTO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
public abstract class AbstractFacade<T> extends DAOUtil {

    private Class<T> entityClass;

    static final Logger logger = Logger.getLogger(AbstractFacade.class.getName());
    
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());

                JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
        } else {
            getEntityManager().persist(entity);
        }
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Registra un objeto a nivel de persistencia e incluye un manejador de
     * errores
     *
     * @param entity
     * @return RespuestaDTO
     */
    public RespuestaDTO crear(T entity) {
        RespuestaDTO rdto = new RespuestaDTO();
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
            if (constraintViolations.size() > 0) {
                rdto.setCodigo("JPA004");
                Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
                while (iterator.hasNext()) {
                    ConstraintViolation<T> cv = iterator.next();
                    System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());

                    JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                }
            } else {
                getEntityManager().persist(entity);
            }
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            rdto.setCodigo("JPA004");
        }
        return rdto;
    }
    
    /**
     * Registra un objeto a nivel de persistencia e incluye un manejador de
     * errores
     *
     * @param entity
     * @return RespuestaDTO
     */
    public RespuestaDTO crearPJ(T entity) {
        RespuestaDTO rdto = new RespuestaDTO();
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
            if (constraintViolations.size() > 0) {
                rdto.setCodigo("JPA004");
                Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
                while (iterator.hasNext()) {
                    ConstraintViolation<T> cv = iterator.next();
                    System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                    JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                }
            } else {
                getEntityManager().persist(entity);
                getEntityManager().flush();
            }
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            rdto.setCodigo("JPA004");
        } catch (Exception e) {
            throw e;            
        }
        return rdto;
    }

    /**
     * Actualiza un objeto a nivel de persistencia e incluye un manejador de
     * errores
     *
     * @param entity
     * @return RespuestaDTO
     */
    public RespuestaDTO actualizar(T entity) {
        RespuestaDTO rdto = new RespuestaDTO();
        try {
            getEntityManager().merge(entity);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            rdto.setCodigo("JPA004");
        }
        return rdto;
    }

    /**
     * Elimina un objeto a nivel de persistencia e incluye un manejador de
     * errores
     *
     * @param entity
     * @return RespuestaDTO
     */
    public RespuestaDTO eliminar(T entity) {
        RespuestaDTO rdto = new RespuestaDTO();
        try {
            getEntityManager().remove(entity);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            rdto.setCodigo("JPA004");
        }
        return rdto;
    }
    
    /**
     * Elimina un objeto a nivel de persistencia e incluye un manejador de
     * errores
     *
     * @param entity
     * @return RespuestaDTO
     */
    public RespuestaDTO eliminarPj(T entity) {
        RespuestaDTO rdto = new RespuestaDTO();
        try {
            getEntityManager().remove(entity);
            getEntityManager().flush();
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            rdto.setCodigo("JPA004");
        }
        return rdto;
    }
    
    /**
     * @param entity
     * @return T
     */
    public T modificar(T entity) {
        return getEntityManager().merge(entity);
    }
    
    /**
     * @param entity
     * @return T
     */
    public RespuestaDTO modificarPj(T entity) {
        RespuestaDTO rdto = new RespuestaDTO();
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
            if (constraintViolations.size() > 0) {
                rdto.setCodigo("JPA004");
                Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
                while (iterator.hasNext()) {
                    ConstraintViolation<T> cv = iterator.next();
                    System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                    JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                }
            } else {
                getEntityManager().merge(entity);
                getEntityManager().flush();
            }
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            rdto.setCodigo("JPA004");
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR ModificarPj EN AbstractFacade: ")
                    .append("-CH- ").append("")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            throw e;
        }
        return rdto;
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
