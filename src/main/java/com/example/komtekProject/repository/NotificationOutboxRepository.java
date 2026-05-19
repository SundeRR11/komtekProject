package com.example.komtekProject.repository;

import com.example.komtekProject.entity.NotificationOutbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationOutboxRepository extends JpaRepository<NotificationOutbox, Long> {

    @Query("""
            SELECT n FROM NotificationOutbox n
            WHERE n.lastAttemptTime <= :cutoffTime
            ORDER BY n.lastAttemptTime ASC
            """)
    List<NotificationOutbox> findRetryable(@Param("cutoffTime") LocalDateTime cutoffTime,
                                           Pageable pageable);
}