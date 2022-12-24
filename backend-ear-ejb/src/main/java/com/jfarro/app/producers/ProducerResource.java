package com.jfarro.app.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@ApplicationScoped
public class ProducerResource {

    //Creo la fabrica del entity manager con la conexion mysql del persistence
    @PersistenceUnit(name = "mysql")
    private EntityManagerFactory emf;

    //Agrego el entity manager en el contexto de la aplicacion para poder inyectarlo en otras clases
    @Produces
    @RequestScoped
    private EntityManager beanEntityManager() {
        return emf.createEntityManager();
    }

    //Verifica si esta abierto la conexion para luego cerrarla
    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}
