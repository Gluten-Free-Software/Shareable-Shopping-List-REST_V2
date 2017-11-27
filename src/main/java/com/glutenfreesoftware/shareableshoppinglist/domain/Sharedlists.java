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
@Table(name = "sharedlists")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sharedlists.findAll", query = "SELECT s FROM Sharedlists s")
    , @NamedQuery(name = "Sharedlists.findBySharedListID", query = "SELECT s FROM Sharedlists s WHERE s.sharedListID = :sharedListID")
    , @NamedQuery(name = "Sharedlists.findBySharedWith", query = "SELECT s FROM Sharedlists s WHERE s.sharedWith = :sharedWith")
    , @NamedQuery(name = "Sharedlists.findBySharedListVersion", query = "SELECT s FROM Sharedlists s WHERE s.sharedListVersion = :sharedListVersion")})
public class Sharedlists implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sharedListID")
    private Integer sharedListID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sharedWith")
    private int sharedWith;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sharedListVersion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sharedListVersion;
    @JoinColumn(name = "listID", referencedColumnName = "listID")
    @ManyToOne(optional = false)
    private Lists listID;

    public Sharedlists() {
    }

    public Sharedlists(Integer sharedListID) {
        this.sharedListID = sharedListID;
    }

    public Sharedlists(Integer sharedListID, int sharedWith, Date sharedListVersion) {
        this.sharedListID = sharedListID;
        this.sharedWith = sharedWith;
        this.sharedListVersion = sharedListVersion;
    }

    public Integer getSharedListID() {
        return sharedListID;
    }

    public void setSharedListID(Integer sharedListID) {
        this.sharedListID = sharedListID;
    }

    public int getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(int sharedWith) {
        this.sharedWith = sharedWith;
    }

    public Date getSharedListVersion() {
        return sharedListVersion;
    }

    public void setSharedListVersion(Date sharedListVersion) {
        this.sharedListVersion = sharedListVersion;
    }

    public Lists getListID() {
        return listID;
    }

    public void setListID(Lists listID) {
        this.listID = listID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sharedListID != null ? sharedListID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sharedlists)) {
            return false;
        }
        Sharedlists other = (Sharedlists) object;
        if ((this.sharedListID == null && other.sharedListID != null) || (this.sharedListID != null && !this.sharedListID.equals(other.sharedListID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.glutenfreesoftware.shareableshoppinglist.domain.Sharedlists[ sharedListID=" + sharedListID + " ]";
    }
    
}
