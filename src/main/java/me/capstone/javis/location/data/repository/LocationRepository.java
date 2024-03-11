package me.capstone.javis.location.data.repository;

import me.capstone.javis.location.data.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
