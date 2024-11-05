package facades;

import entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author maria
 */

@Stateless
public class UsuarioFacade {
    @PersistenceContext(unitName = "CashCrafterPU")
    private EntityManager em;
    
    public void create(Usuario usuario) {
        em.persist(usuario);
    }
    
    public Usuario findById(Long id) {
        return em.find(Usuario.class, id);
    }
    
    public Usuario findByCorreo(String correo) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class)
                    .setParameter("correo", correo)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}