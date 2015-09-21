package de.jdufner.doppelt.domain.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "benutzer")
public class Benutzer {

  @Id
  @Column(name = "benu_name", unique = true, nullable = false, length = 25)
  private String name;

  @Column(name = "benu_kennwort", nullable = false, length = 25)
  private String kennwort;

  @ManyToMany
  @JoinTable(name = "benutzer_rollen", joinColumns = {
      @JoinColumn(name = "bero_benu_name", referencedColumnName = "benu_name") }, inverseJoinColumns = {
          @JoinColumn(name = "bero_roll_name", referencedColumnName = "roll_name") })
  private Set<Rolle> rollen = new HashSet<Rolle>();

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getKennwort() {
    return kennwort;
  }

  public void setKennwort(final String kennwort) {
    this.kennwort = kennwort;
  }

  public Set<Rolle> getRollen() {
    return rollen;
  }

  public void setRollen(final Set<Rolle> rollen) {
    this.rollen = rollen;
  }

}
