package de.jdufner.doppelt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.jdufner.doppelt.domain.security.Benutzer;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

}
