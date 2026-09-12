package com.example.app.dto.room.output;

public record RoomResponse(
    String hometype,

    String address,

    Boolean hasTV,

    Boolean hasInternet,

    Boolean hasKitchen,

    Boolean hasAirCon,

    Long price,

    Double latitude,

    Double longitude
) {}
