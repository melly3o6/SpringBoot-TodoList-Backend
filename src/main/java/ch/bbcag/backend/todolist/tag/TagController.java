package ch.bbcag.backend.todolist.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(TagController.PATH)
public class TagController {

    // Local variables

    public static final String PATH = "/tags";
    private final TagService tagService;

    // Constructor

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // GET
    // Find all tags
    // --- Documentation ---
    @GetMapping
    @Operation(summary = "Find tags with a given name. If no name given, all tags are returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tags found",
                content = @Content(schema = @Schema(implementation = TagResponseDTO.class)))
    })

    // Method
    public ResponseEntity<?> findTags(
            @Parameter(description = "Tag name to search")
            @RequestParam(required = false) String name
    ) {
        try {
            List<Tag> tags = name != null
                    ? tagService.findByName(name)
                    : tagService.findAll();

            return ResponseEntity.ok(tags.stream()
                    .map(TagMapper::toResponseDTO)
                    .toList());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tags were not found");

        }
    }

    // Find a tag by id
    // --- Documentation ---
    @GetMapping("/{id}")
    @Operation(summary = "Get a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag found",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag not found",
                    content = @Content)
    })

    // Method
    public ResponseEntity<?> findById(
            @Parameter(description = "Id of tag to get")
            @PathVariable("id") Integer id
    ) {
        try {
            Tag tag = tagService.findById(id);
            return ResponseEntity.ok(TagMapper.toResponseDTO(tag));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag was not found");
        }
    }

    // POST
    // Create a tag
    // Documentation
    @PostMapping
    @Operation(summary = "Create a new tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag was added successfully",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "There was a conflict while creating the tag",
                    content = @Content)
    })

    // Method
    public ResponseEntity<?> insert(
            @Parameter(description = "The new user to create")
            @Valid @RequestBody TagRequestDTO newTagDTO
    ) {
        try {
            Tag newTag = TagMapper.fromRequestDTO(newTagDTO);
            Tag savedTag = tagService.insert(newTag);
            return ResponseEntity.status(HttpStatus.CREATED).body(TagMapper.toResponseDTO(savedTag));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag couldn't be created");
        }
    }

    // PATCH
    // Update a tag
    // Documentation
    @PatchMapping("/{id}")
    @Operation(summary = "Update a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag was updated successfully",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the tag",
                    content = @Content)
    })

    // Method
    public ResponseEntity<?> update(
            @Parameter(description = "The tag to update")
            @RequestBody TagRequestDTO updateTagDTO,

            @Parameter(description = "Id of tag to update")
            @PathVariable("id") Integer id) {
        try {
            Tag updateTag = TagMapper.fromRequestDTO(updateTagDTO);
            Tag savedTag = tagService.update(updateTag, id);
            return ResponseEntity.status(HttpStatus.OK).body(TagMapper.toResponseDTO(savedTag));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the tag");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found");
        }
    }

    // DELETE
    // Delete a tag
    // Documentation
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tag could not be deleted",
                    content = @Content)
    })

    // Method
    public ResponseEntity<?> delete(
            @Parameter(description = "Id of tag to delete")
            @PathVariable("id") Integer id
    ) {
        try {
            tagService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag was not found");
        }
    }

}
