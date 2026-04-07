package dev.ppakst.grayAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ppakst.grayAI.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
