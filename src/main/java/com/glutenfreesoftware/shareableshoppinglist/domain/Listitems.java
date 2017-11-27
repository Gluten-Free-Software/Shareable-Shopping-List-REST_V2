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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "listitems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listitems.findAll", query = "SELECT l FROM Listitems l")
    , @NamedQuery(name = "Listitems.findByListItemID", query = "SELECT l FROM Listitems l WHERE l.listItemID = :listItemID")
    , @NamedQuery(name = "Listitems.findByListItemName", query = "SELECT l FROM Listitems l WHERE l.listItemName = :listItemName")
    , @NamedQuery(name = "Listitems.findByListItemVersion", query = "SELECT l FROM Listitems l WHERE l.listItemVersion = :listItemVersion")})
public class Listitems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "listItemID")
    private Integer listItemID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "listItemName")
    private String listItemName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "listItemVersion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date listItemVersion;
    @JoinColumn(name = "listID", referencedColumnName = "listID")
    @ManyToOne(optional = false)
    private Lists listID;

    public Listitems() {
    }

    public Listitems(Integer listItemID) {
        this.listItemID = listItemID;
    }

    public Listitems(Integer listItemID, String listItemName, Date listItemVersion) {
        this.listItemID = listItemID;
        this.listItemName = listItemName;
        this.listItemVersion = listItemVersion;
    }

    public Integer getListItemID() {
        return listItemID;
    }

    public void setListItemID(Integer listItemID) {
        this.listItemID = listItemID;
    }

    public String getListItemName() {
        return listItemName;
    }

    public void setListItemName(String listItemName) {
        this.listItemName = listItemName;
    }

    public Date getListItemVersion() {
        return listItemVersion;
    }

    public void setListItemVersion(Date listItemVersion) {
        this.listItemVersion = listItemVersion;
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
        hash += (listItemID != null ? listItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listitems)) {
            return false;
        }
        Listitems other = (Listitems) object;
        if ((this.listItemID == null && other.listItemID != null) || (this.listItemID != null && !this.listItemID.equals(other.listItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.glutenfreesoftware.shareableshoppinglist.domain.Listitems[ listItemID=" + listItemID + " ]";
    }
    
}
