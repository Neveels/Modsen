package org.blueliner.libraryservice.repository;

import org.blueliner.libraryservice.model.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLogRepository extends JpaRepository<BookLog, Long> {
}
