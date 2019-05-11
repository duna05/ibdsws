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
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.SystemException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
public class Facade<T> extends DAOUtil {

    private Class<T> entityClass;

    static final Logger logger = Logger.getLogger(Facade.class.getName());

    public Facade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Resource
    EJBContext context;

    protected EntityManager entityManager;

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
            context.getUserTransaction().begin();
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
                context.getUserTransaction().commit();
            }
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            try {
                context.getUserTransaction().rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(Facade.class.getName()).log(null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Facade.class.getName()).log(null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(Facade.class.getName()).log(null, ex);
            }
        } catch (Exception e) {
            try {
                context.getUserTransaction().rollback();
            } catch (Exception ex) {
                Logger.getLogger(Facade.class.getName()).log( null, ex);
            }
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
    public RespuestaDTO modificarPj(T entity) {
        RespuestaDTO rdto = new RespuestaDTO();
        try {
            context.getUserTransaction().begin();
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
                context.getUserTransaction().commit();
            }
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            rdto.setCodigo("JPA004");
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR ModificarPj EN AbstractFacade: ")
                    .append("-CH- ").append("")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
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

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
