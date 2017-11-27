/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glutenfreesoftware.shareableshoppinglist.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "sharedrooms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sharedrooms.findAll", query = "SELECT s FROM Sharedrooms s")
    , @NamedQuery(name = "Sharedrooms.findBySharedRoomID", query = "SELECT s FROM Sharedrooms s WHERE s.sharedRoomID = :sharedRoomID")
    , @NamedQuery(name = "Sharedrooms.findBySharedWith", query = "SELECT s FROM Sharedrooms s WHERE s.sharedWith = :sharedWith")
    , @NamedQuery(name = "Sharedrooms.findBySharedRoomVersion", query = "SELECT s FROM Sharedrooms s WHERE s.sharedRoomVersion = :sharedRoomVersion")})
public class Sharedrooms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sharedRoomID")
    private Integer sharedRoomID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sharedWith")
    private int sharedWith;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SharedRoomVersion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sharedRoomVersion;
    @JoinColumn(name = "roomID", referencedColumnName = "roomID")
    @ManyToOne(optional = false)
    private Rooms roomID;

    public Sharedrooms() {
    }

    public Sharedrooms(Integer sharedRoomID) {
        this.sharedRoomID = sharedRoomID;
    }

    public Sharedrooms(Integer sharedRoomID, int sharedWith, Date sharedRoomVersion) {
        this.sharedRoomID = sharedRoomID;
        this.sharedWith = sharedWith;
        this.sharedRoomVersion = sharedRoomVersion;
    }

    public Integer getSharedRoomID() {
        return sharedRoomID;
    }

    public void setSharedRoomID(Integer sharedRoomID) {
        this.sharedRoomID = sharedRoomID;
    }

    public int getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(int sharedWith) {
        this.sharedWith = sharedWith;
    }

    public Date getSharedRoomVersion() {
        return sharedRoomVersion;
    }

    public void setSharedRoomVersion(Date sharedRoomVersion) {
        this.sharedRoomVersion = sharedRoomVersion;
    }

    public Rooms getRoomID() {
        return roomID;
    }

    public void setRoomID(Rooms roomID) {
        this.roomID = roomID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sharedRoomID != null ? sharedRoomID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sharedrooms)) {
            return false;
        }
        Sharedrooms other = (Sharedrooms) object;
        if ((this.sharedRoomID == null && other.sharedRoomID != null) || (this.sharedRoomID != null && !this.sharedRoomID.equals(other.sharedRoomID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.glutenfreesoftware.shareableshoppinglist.domain.Sharedrooms[ sharedRoomID=" + sharedRoomID + " ]";
    }
    
}
