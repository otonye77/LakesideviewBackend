package com.lakeside.lakesidehotel.repository;

import com.lakeside.lakesidehotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
