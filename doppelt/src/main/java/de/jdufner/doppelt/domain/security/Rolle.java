package de.jdufner.doppelt.domain.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rollen")
public class Rolle {

  @Id
  @Column(name = "roll_name", unique = true, nullable = false, length = 25)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

}
