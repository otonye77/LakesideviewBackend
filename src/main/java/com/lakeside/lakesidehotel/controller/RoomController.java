package com.lakeside.lakesidehotel.controller;

import com.lakeside.lakesidehotel.models.Room;
import com.lakeside.lakesidehotel.response.RoomResponse;
import com.lakeside.lakesidehotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final IRoomService roomService;
    @PostMapping(value = "/add/new-room", produces = "application/json")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(
                savedRoom.getId(),
                savedRoom.getRoomType(),
                savedRoom.getRoomPrice()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/room-types")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }

}
