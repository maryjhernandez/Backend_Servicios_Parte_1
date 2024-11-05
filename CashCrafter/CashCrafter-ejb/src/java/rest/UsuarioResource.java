package rest;

import entities.Usuario;
import facades.UsuarioFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author maria
 */

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    private static final Logger logger = Logger.getLogger(UsuarioResource.class.getName());
    
    @EJB 
    private UsuarioFacade usuarioFacade;
    
    @POST
    @Path("/registro")
    public Response registrarUsuario(Usuario usuario) {
        try {
            logger.log(Level.INFO, "Iniciando registro de usuario: {0}", usuario.getCorreo());
            
            // Validación de campos
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("El nombre es requerido")
                             .build();
            }
            if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("El correo es requerido")
                             .build();
            }
            if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("La contraseña es requerida")
                             .build();
            }
            if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("El usuario es requerido")
                             .build();
            }
            if (usuario.getRol() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("El rol es requerido")
                             .build();
            }
            
            logger.log(Level.INFO, "Guardando usuario en la base de datos");
            usuarioFacade.create(usuario);
            logger.log(Level.INFO, "Usuario registrado exitosamente: {0}", usuario.getCorreo());
            
            return Response.status(Response.Status.CREATED)
                         .entity("Usuario creado exitosamente")
                         .build();
                         
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al registrar usuario: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error al registrar usuario: " + e.getMessage())
                         .build();
        }
    }
}