package com.example.app.repos;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.models.Reservation;
import com.example.app.models.Room;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    
    @Query("""
        SELECT COUNT(r) > 0
        FROM Reservation r
        WHERE r.room = :room
        AND r.startDate < :endDate
        AND r.endDate > :startDate
    """)
    boolean isRoomAvailableForPeriod(
            @Param("roomId") Room room,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT COUNT(r) > 0
        FROM Reservation r
        WHERE r.room = :newRoom
        AND r.id != :reservationId
        AND r.startDate < :endDate
        AND r.endDate > :startDate
    """)
    boolean isRoomAvailableForPeriodWithReservation(
            @Param("id") UUID reservationId,
            @Param("roomId") Room newRoom,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
        );
}
