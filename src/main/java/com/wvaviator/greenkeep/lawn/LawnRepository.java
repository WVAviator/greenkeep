package com.wvaviator.greenkeep.lawn;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LawnRepository extends JpaRepository<Lawn, Long> {
    List<Lawn> findAllByUserId(Long userId);
}
