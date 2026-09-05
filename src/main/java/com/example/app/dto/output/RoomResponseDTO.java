package com.example.app.dto.output;

public record RoomResponseDTO(
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
