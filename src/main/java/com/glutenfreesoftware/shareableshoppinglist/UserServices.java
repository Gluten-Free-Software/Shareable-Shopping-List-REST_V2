package com.glutenfreesoftware.shareableshoppinglist;

import com.glutenfreesoftware.shareableshoppinglist.domain.*;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Kristian
 */
@Stateless
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserServices {
    @PersistenceContext
    EntityManager em;
    
    @GET
    @Path("getUser")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Users> getUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        if (username != null && password != null) {
            List<Users> result = null;
            result = em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                    .setParameter("username", username)
                    .getResultList();
            if(result.size() != 1){
                return Collections.EMPTY_LIST;
            }
            else{
                Users u = (Users) result.get(0);
                String hashedPW = BCrypt.hashpw(password, u.getSalt());
                String passwordOnDB = u.getPassword();
                
                if(hashedPW.equals(passwordOnDB)){
                    System.out.println("It is a match: " + hashedPW + ", " + passwordOnDB);
                    return result;
                }
                return Collections.EMPTY_LIST;
            }
        }
        return Collections.EMPTY_LIST;
    }
     
    @POST
    @Path("registerUser")
    public Response addUser(@QueryParam("username") String username,
                            @QueryParam("email")    String email,
                            @QueryParam("password") String password){
        if (username != null && email != null && password != null) {
            List<Users> result = null;
            result = em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                .setParameter("username", username)
                .getResultList();
            if(result.size() == 0){
                String salt = BCrypt.gensalt();
                String hashedPW = BCrypt.hashpw(password, salt);
                Users u = new Users();
                em.persist(u);
                u.setUsername(username);
                u.setEmail(email);
                u.setPassword(hashedPW); 
                u.setSalt(salt);
            }else{
                return Response.noContent().build();
            }
            return Response.ok(username).build();
        }
        return Response.noContent().build();     
    }
}
