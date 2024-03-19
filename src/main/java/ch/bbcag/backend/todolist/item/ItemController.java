package ch.bbcag.backend.todolist.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
// @RestController annotation marks a controller for an API, @Controller would be used for a controller that isn't
// for an API.

// @RequestMapping annotation marks the base Path of the controller
@RestController
@RequestMapping(ItemController.PATH)
public class ItemController {

    // Local variables

    // Path to items, as URL: http://localhost:8080/items
    // Variable PATH is the base for the following paths for items
    // Variable PATH is a constant
    public static final String PATH = "/items";

    // Item-service is for business-logic
    private final ItemService itemService;

    // Constructor for ItemController, requires Item-service
    // @Autowired annotation
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // GET requests
    // Find items by name or tagName
    // --- Documentation ---
    // @GetMapping annotation marks method as method for GET request -> followed by the URI http://localhost:8080/items/?name={name}
    @GetMapping("item")
    // @Operation annotation
    @Operation(summary = "Get all items.")
    // @ApiResponses annotation
    @ApiResponses(value = {
            // @ApiResponse annotation
            @ApiResponse(responseCode = "200", description = "Items found",
                    // @Content annotation
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class)))
    })

    // Method to find either all items or all items with a certain name
    // -> @PathVariable annotation for 'name' means variable 'name' is used in URI
    public ResponseEntity<?> findItems(@RequestParam(required = false) String name) {
        try {
            // List for entity items is decelerated
            List<Item> items;
            if (name == null) {
                items = itemService.findAll();
            } else {
                items = itemService.findByName(name);
            }
            return ResponseEntity.ok(items.stream()
                    .map(ItemMapper::toResponseDTO)
                    .toList());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Items were not found");
        }

    }

    // Find item by id
    // --- Documentation ---
    // @GetMapping annotation declares method as GET method
    // --> ("{id}") adds a PathVariable to the @GetMapping annotation
    @GetMapping("{id}")
    // @Operation annotation
    @Operation(summary = "Get an item")
    // @ApiResponse annotation
    @ApiResponses(value = {
            // @ApiResponse annotation
            @ApiResponse(responseCode = "200", description = "Item found",
                    // @Content & @Schema annotations
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item was not found",
                    content = @Content)
    })

    // Method to find a specific item by its id
    public ResponseEntity<?> findById(@Valid @Parameter(description = "Id of item to get") @PathVariable Integer id) {
        try {
            Item item = itemService.findById(id);
            return ResponseEntity.ok(ItemMapper.toResponseDTO(item));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item was not found");
        }
    }

    // POST
    // Create an item
    // Documentation
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

    // Method
    public ResponseEntity<?> insert(@Valid @RequestBody ItemRequestDTO newItemDTO) {
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

    // PATCH
    // Update an item
    // Documentation
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

    // Method

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

    // DELETE
    // Delete an item
    // Documentation
    @DeleteMapping("{id}")
    @Operation(summary = "Delete an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Item could not be deleted",
                    content = @Content)
    })

    // Method

    public ResponseEntity<?> delete(@Valid @Parameter(description = "Id of item to delete") @PathVariable Integer id) {
        try {
            itemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item was not found");
        }
    }
}
