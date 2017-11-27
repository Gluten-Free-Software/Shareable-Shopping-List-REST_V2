/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glutenfreesoftware.shareableshoppinglist.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "lists")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lists.findAll", query = "SELECT l FROM Lists l")
    , @NamedQuery(name = "Lists.findByListID", query = "SELECT l FROM Lists l WHERE l.listID = :listID")
    , @NamedQuery(name = "Lists.findByListName", query = "SELECT l FROM Lists l WHERE l.listName = :listName")
    , @NamedQuery(name = "Lists.findByListVersion", query = "SELECT l FROM Lists l WHERE l.listVersion = :listVersion")})
public class Lists implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "listID")
    private Integer listID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "listName")
    private String listName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "listVersion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date listVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listID")
    private List<Sharedlists> sharedlistsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listID")
    private List<Listitems> listitemsList;
    @JoinColumn(name = "roomID", referencedColumnName = "roomID")
    @ManyToOne(optional = false)
    private Rooms roomID;

    public Lists() {
    }

    public Lists(Integer listID) {
        this.listID = listID;
    }

    public Lists(Integer listID, String listName, Date listVersion) {
        this.listID = listID;
        this.listName = listName;
        this.listVersion = listVersion;
    }

    public Integer getListID() {
        return listID;
    }

    public void setListID(Integer listID) {
        this.listID = listID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Date getListVersion() {
        return listVersion;
    }

    public void setListVersion(Date listVersion) {
        this.listVersion = listVersion;
    }

    @XmlTransient
    public List<Sharedlists> getSharedlistsList() {
        return sharedlistsList;
    }

    public void setSharedlistsList(List<Sharedlists> sharedlistsList) {
        this.sharedlistsList = sharedlistsList;
    }

    @XmlTransient
    public List<Listitems> getListitemsList() {
        return listitemsList;
    }

    public void setListitemsList(List<Listitems> listitemsList) {
        this.listitemsList = listitemsList;
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
        hash += (listID != null ? listID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lists)) {
            return false;
        }
        Lists other = (Lists) object;
        if ((this.listID == null && other.listID != null) || (this.listID != null && !this.listID.equals(other.listID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.glutenfreesoftware.shareableshoppinglist.domain.Lists[ listID=" + listID + " ]";
    }
    
}
