package com.project.doit.controller;

import com.project.doit.dto.ItemDto;
import com.project.doit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/additem")
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto, @RequestParam Long taskId) {
        return new ResponseEntity<>(itemService.create(itemDto, taskId), HttpStatus.CREATED);
    }

    @PutMapping("/att")
    public ResponseEntity<ItemDto> updateItem(@RequestParam Long itemId, @RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.update(itemDto, itemId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDto>> getAllItems(@RequestParam Long taskId) {
        return ResponseEntity.ok(itemService.getTaskItems(taskId));
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }
}
