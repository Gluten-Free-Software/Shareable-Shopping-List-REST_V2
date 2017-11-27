package com.glutenfreesoftware.shareableshoppinglist;

import com.glutenfreesoftware.shareableshoppinglist.domain.*;
//import com.glutenfreesoftware.shareableshoppinglist.dbtest.*;
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
@Path("lists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListServices {
    @PersistenceContext
    EntityManager em;
    
    @GET
    @Path("getLists")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lists> getLists(@QueryParam("listRoom")  String listRoom,
                                @QueryParam("listOwner") String listOwner){
        //List<Users> owner = null;
        List<Rooms> room = null;
        List<Lists> lists = null;
        if(listRoom != null && listOwner != null){
            room = em.createQuery("SELECT r FROM Rooms r WHERE r.roomName = :listRoom AND r.userID.username = :listOwner", Rooms.class)
                    .setParameter("listRoom", listRoom)
                    .setParameter("listOwner", listOwner)
                    .getResultList();
            if(room.size() == 1){
                Rooms roomToReturn = (Rooms) room.get(0);
                int roomID = roomToReturn.getRoomID();
                lists = em.createQuery("SELECT l FROM Lists l WHERE l.roomID.roomID = :roomID", Lists.class)
                        .setParameter("roomID", roomID)
                        .getResultList();
                if(lists.size() != 0){
                    return lists;
                }
                return Collections.EMPTY_LIST;   
            }
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }
    
    @POST
    @Path("addList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addList(@QueryParam("listRoom")  String listRoom,
                            @QueryParam("listName")  String listName,
                            @QueryParam("listOwner") String listOwner){
        List<Rooms> room = null;
        List<Lists> lists = null;
        if(listRoom != null && listName != null && listOwner != null){
            room = em.createQuery("SELECT r FROM Rooms r WHERE r.roomName = :listRoom AND r.userID.username = :listOwner", Rooms.class)
                    .setParameter("listRoom", listRoom)
                    .setParameter("listOwner", listOwner)
                    .getResultList();
            if(room.size() == 1){
                Rooms roomToReturn = (Rooms) room.get(0);
                Lists newList = new Lists();
                em.persist(newList);
                newList.setListName(listName);
                newList.setRoomID(roomToReturn);
                return Response.ok("List created: " + listName).build();
            }
            return Response.noContent().build();
        }
        return Response.noContent().build();
    }  
    
    @POST
    @Path("removeList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeList(@QueryParam("listOwner") String listOwner,
                               @QueryParam("listID") int listID){
        List <Lists> list = null;
        if (listOwner != null && listID != 0) {
            list = em.createQuery("SELECT l FROM Lists l WHERE l.listID = :listID", Lists.class)
                    .setParameter("listID", listID)
                    .getResultList();
            if(list.size() != 0){
                Lists listToDelete = (Lists) list.get(0);
                String deletedList = listToDelete.getListName();
                Rooms room = listToDelete.getRoomID();
                Users user = room.getUserID();
                String username = user.getUsername();
                if(username.equals(listOwner)){
                    int changed = em.createQuery("DELETE FROM Lists l WHERE l.listID = :listID", Lists.class)
                            .setParameter("listID", listID)
                            .executeUpdate();
                    if(changed != 0){
                        return Response.ok("List deleted: " + deletedList).build();
                    }
                }
            }
            
        }
        return Response.noContent().build();
    }
    
    @POST
    @Path("shareList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response shareList(@QueryParam("listID") int listID,
                              @QueryParam("sharedWith") String sharedWith){
        List<Users> shareWith = null;
        List<Lists> shareList = null;
        if(sharedWith != null && listID != 0){
            shareWith = em.createQuery("SELECT u FROM Users u WHERE u.username = :sharedWith", Users.class)
                    .setParameter("sharedWith", sharedWith)
                    .getResultList();
            if (shareWith.size() == 1){
                shareList = em.createQuery("SELECT l FROM Lists l WHERE l.listID = :listID" , Lists.class)
                        .setParameter("listID", listID)
                        .getResultList();
                if (shareList.size() == 1){
                    Lists listToShare = (Lists) shareList.get(0);
                    Users userToShareWith = (Users) shareWith.get(0);
                    int userID = userToShareWith.getUserID();
                    Sharedlists sl = new Sharedlists();
                    em.persist(sl);
                    sl.setListID(listToShare);
                    sl.setSharedWith(userID);
                    return Response.ok("List shared: " + listToShare.getListName()).build();
                }
            }
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("sharedLists")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sharedlists> sharedLists(@QueryParam("sharedWith") String sharedWith){
        List<Sharedlists> sl = null;
        List<Users> sharedWithUser = null;
        if (sharedWith != null) { 
            sharedWithUser = em.createQuery("SELECT u FROM Users u WHERE u.username = :sharedWith", Users.class)
                    .setParameter("sharedWith", sharedWith)
                    .getResultList();
            if (sharedWithUser.size() != 0) {
                Users u = (Users) sharedWithUser.get(0);
                int userID = u.getUserID();
                
                sl = em.createQuery("SELECT s FROM Sharedlists s WHERE s.sharedWith = :userID", Sharedlists.class)
                        .setParameter("userID", userID)
                        .getResultList();
                return sl;
            }
        }
        return sl != null ? sl : Collections.EMPTY_LIST;
    }
    
    @POST
    @Path("addListItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addListItem(@QueryParam("listItemName")  String listItemName,
                                @QueryParam("listID") int listID){
        List<Lists> list = null; 
        if(listItemName != null && listID != 0){
            list = em.createQuery("SELECT l FROM Lists l WHERE l.listID = :listID", Lists.class)
                    .setParameter("listID", listID)
                    .getResultList();
            if(list.size() == 1){
                Lists addToList = (Lists) list.get(0);
                Listitems li = new Listitems();
                em.persist(li);
                li.setListItemName(listItemName);
                li.setListID(addToList);
                return Response.ok("Item added: " + listItemName).build();
            }
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("getListItems")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Listitems> getListItems(@QueryParam("listID") int listID) {
        List<Listitems> result = null;
        if (listID != 0) {
            result = em.createQuery("SELECT l FROM Listitems l WHERE l.listID.listID = :listID", Listitems.class)
                    .setParameter("listID", listID)
                    .getResultList();
            if(result.size() != 0){
                return result;
            }
        }
        return result != null ? result : Collections.EMPTY_LIST;
    }
    
    @POST
    @Path("removeListItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeListItem(@QueryParam("listItemName")  String listItemName,
                                   @QueryParam("listID") int listID){
        if(listItemName != null && listID != 0){
            int changed = em.createQuery("DELETE FROM Listitems l WHERE l.listItemName = :listItemName AND l.listID.listID = :listID", Listitems.class)
                    .setParameter("listItemName",listItemName)
                    .setParameter("listID", listID)
                    .executeUpdate();
            if(changed != 0){
                return Response.ok("List item removed: " + listItemName).build();
            }
            
        }
        return Response.noContent().build();
    }
}
