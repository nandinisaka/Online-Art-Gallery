package com.art.galley.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.galley.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
