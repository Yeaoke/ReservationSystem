package com.example.app.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dto.input.CreateRoomDTO;
import com.example.app.exceptions.UserNotFoundException;
import com.example.app.models.Room;
import com.example.app.models.User;
import com.example.app.repos.RoomRepository;
import com.example.app.repos.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Room createRoom(
        CreateRoomDTO dto,
        UUID userId
    ) throws UserNotFoundException {
        Room room = new Room();

        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

        room.setId(UUID.randomUUID());
        room.setHomeType(dto.homeType());
        room.setAddress(dto.address());
        room.setHasTV(dto.hasTV());
        room.setHasInternet(dto.hasInternet());
        room.setHasAirCon(dto.hasAirCon());
        room.setPrice(dto.price());
        room.setOwner(user);
        room.setLatitude(dto.latitude());
        room.setLongitude(dto.longitude());

        
        return roomRepository.save(room);
    }

}
