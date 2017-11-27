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

/**
 *
 * @author Kristian
 */
@Stateless
@Path("rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomServices {
    @PersistenceContext
    EntityManager em;
    
    @GET
    @Path("getRooms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rooms> getRooms(){
        List<Rooms> result = null; 
        result = em.createQuery("SELECT r FROM Rooms r", Rooms.class)
                .getResultList();
        return result != null ? result : Collections.EMPTY_LIST;
    }
    
    @GET
    @Path("getUserRooms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rooms> getUserRooms(@QueryParam("roomOwner") String roomOwner) {
        List<Rooms> result = null;
        List<Users> owner = null;
        owner = em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                .setParameter("username", roomOwner)
                .getResultList();
        if (owner.size() == 1) {
            Users u = (Users) owner.get(0);
            int userID = u.getUserID();
            result = em.createQuery("SELECT r FROM Rooms r WHERE r.userID.userID = :userID", Rooms.class)
                    .setParameter("userID", userID)
                    .getResultList();
            return result != null ? result : Collections.EMPTY_LIST;
        }
        return result != null ? result : Collections.EMPTY_LIST;
    }
    
    @POST
    @Path("addRoom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(@QueryParam("roomName") String roomName,
                            @QueryParam("roomOwner") String roomOwner) {
        if (roomName != null && roomOwner != null) {
            List<Users> owner = null;
            owner = em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                    .setParameter("username", roomOwner)
                    .getResultList();
            if (owner.size() == 1) {
                Users u = (Users) owner.get(0);
                Rooms room = new Rooms();
                em.persist(room);
                room.setRoomName(roomName);
                room.setUserID(u);
                return Response.ok(roomName).build();
            }
            return Response.noContent().build();
        }
        return Response.noContent().build();
    }
    
    @POST
    @Path("removeRoom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeRoom(@QueryParam("roomName") String roomName,
                               @QueryParam("roomOwner") String roomOwner) {
        if (roomName != null && roomOwner != null) {
            List<Users> owner = null;
            owner = em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                    .setParameter("username", roomOwner)
                    .getResultList();
            if (owner.size() == 1) {
                Users u = (Users) owner.get(0);
                int userID = u.getUserID();
                int change = em.createQuery("DELETE FROM Rooms r WHERE r.roomName = :roomName AND r.userID.userID = :userID", Rooms.class)
                        .setParameter("roomName", roomName)
                        .setParameter("userID", userID)
                        .executeUpdate();
                if(change != 0){
                    return Response.ok("Room removed: " + roomName).build();
                }
                return Response.noContent().build();
            }
            return Response.noContent().build();
        }
        return Response.noContent().build();
    }
    
    @POST
    @Path("shareRoom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response shareRoom(@QueryParam("sharedRoomName")  String sharedRoomName,
                              @QueryParam("sharedRoomOwner") String sharedRoomOwner,
                              @QueryParam("sharedWith")      String sharedWith) {
        System.out.println("1");
        if (sharedRoomName != null && sharedRoomOwner != null && sharedWith != null) {
            System.out.println("2");
            List<Users> sharedWithUser = null;
            sharedWithUser = em.createQuery("SELECT u FROM Users u WHERE u.username = :sharedWith", Users.class)
                    .setParameter("sharedWith", sharedWith)
                    .getResultList();
            if(sharedWithUser.size() != 0){
                Users u = (Users) sharedWithUser.get(0);
                int userID = u.getUserID();
                List<Rooms> room = null;
                room = em.createQuery("SELECT r FROM Rooms r WHERE r.roomName = :sharedRoomName AND r.userID.username =:sharedRoomOwner", Rooms.class)
                    .setParameter("sharedRoomName", sharedRoomName)
                    .setParameter("sharedRoomOwner", sharedRoomOwner)
                    .getResultList();
                if(room.size() != 0){
                    Rooms roomToShare = (Rooms) room.get(0);
                    Sharedrooms sr = new Sharedrooms();
                    em.persist(sr);
                    sr.setRoomID(roomToShare);
                    sr.setSharedWith(userID);
                    return Response.ok("Room shared: " + sharedRoomName).build();
                }  
            }
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("sharedRooms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sharedrooms> sharedRooms(@QueryParam("sharedWith") String sharedWith){
        List<Sharedrooms> sr = null;
        List<Users> sharedWithUser = null;
        if (sharedWith != null) { 
            sharedWithUser = em.createQuery("SELECT u FROM Users u WHERE u.username = :sharedWith", Users.class)
                    .setParameter("sharedWith", sharedWith)
                    .getResultList();
            if (sharedWithUser.size() != 0) {
                Users u = (Users) sharedWithUser.get(0);
                int userID = u.getUserID();
                
                sr = em.createQuery("SELECT s FROM Sharedrooms s WHERE s.sharedWith = :userID", Sharedrooms.class)
                        .setParameter("userID", userID)
                        .getResultList();
                return sr;
            }
        }
        return sr != null ? sr : Collections.EMPTY_LIST;
    }
}
