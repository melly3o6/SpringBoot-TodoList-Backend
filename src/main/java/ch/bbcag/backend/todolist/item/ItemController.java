package ch.bbcag.backend.todolist.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(ItemController.PATH)
public class ItemController {
    public static final String PATH = "/items";
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("?name={name}")
    @Operation(summary = "Get all items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items found",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class)))
    })

    public ResponseEntity<?> findItems(@PathVariable String name,
                                       @RequestParam(required = false) String tagName) {

        List<Item> items;

        if (name != null && tagName != null) {
            items = itemService.findByNameAndTagName(name, tagName);
        } else if (name == null) {
            items = itemService.findByTag(tagName);
        } else if (tagName == null) {
            items = itemService.findByName(name);
        } else {
            items = itemService.findAll();
        }
        return ResponseEntity.ok(items.stream()
                .map(ItemMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item was not found",
                    content = @Content)
    })
    public ResponseEntity<?> findById(@Parameter(description = "Id of item to get") @PathVariable Integer id) {
        try {
            Item item = itemService.findById(id);
            return ResponseEntity.ok(ItemMapper.toResponseDTO(item));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item was not found");
        }
    }

    @PostMapping
    @Operation(summary = "Create an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item created",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Item couldn't be created",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Item was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while creating the item",
                    content = @Content)
    })
    public ResponseEntity<?> insert(@RequestBody ItemRequestDTO newItemDTO) {
        try {
            Item newItem = ItemMapper.fromRequestDTO(newItemDTO);
            Item savedItem = itemService.insert(newItem);
            ItemResponseDTO responseDTO = ItemMapper.toResponseDTO(savedItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item couldn't be created");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item was not found");
        }
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item was updated successfully",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the item",
                    content = @Content)
    })
    public ResponseEntity<?> update(@Parameter(description = "The item to update") @RequestBody ItemRequestDTO updateItemDTO, @PathVariable Integer id) {
        try {
            Item updateItem = ItemMapper.fromRequestDTO(updateItemDTO);
            Item savedItem = itemService.update(updateItem, id);
            return ResponseEntity.ok(ItemMapper.toResponseDTO(savedItem));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the item");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Item could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> delete(@Parameter(description = "Id of item to delete") @PathVariable Integer id) {
        try {
            itemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item was not found");
        }
    }
}
