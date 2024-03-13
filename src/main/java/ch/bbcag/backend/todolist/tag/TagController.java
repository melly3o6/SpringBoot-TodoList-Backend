package ch.bbcag.backend.todolist.tag;

import ch.bbcag.backend.todolist.item.Item;
import ch.bbcag.backend.todolist.item.ItemMapper;
import ch.bbcag.backend.todolist.item.ItemRequestDTO;
import ch.bbcag.backend.todolist.item.ItemResponseDTO;
import ch.bbcag.backend.todolist.person.PersonResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(TagController.PATH)
public class TagController {
    public static final String PATH = "/tags";
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("?name={name}")
    @Operation(summary = "Get all items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items found",
                content = @Content(schema = @Schema(implementation = TagResponseDTO.class)))
    })
    public ResponseEntity<?> findTags(@PathVariable String name) {
        List<Tag> tags;
        if (name == null) {
            tags = tagService.findAll();
        } else {
            tags = tagService.findByName(name);
        }
        return ResponseEntity.ok(tags.stream()
                .map(TagMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag found",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag was not found",
                    content = @Content)
    })
    public ResponseEntity<?> findById(@Parameter(description = "Id of tag to get") @PathVariable Integer id) {
        try {
            Tag tag = tagService.findById(id);
            return ResponseEntity.ok(TagMapper.toResponseDTO(tag));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag was not found");
        }
    }

    @PostMapping
    @Operation(summary = "Create a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag created",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Tag couldn't be created",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tag was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while creating the tag",
                    content = @Content)
    })
    public ResponseEntity<?> insert(@RequestBody TagRequestDTO newTagDTO) {
        try {
            Tag newTag = TagMapper.fromRequestDTO(newTagDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(tagService.insert(newTag));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while creating the tag");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag was not found");
        }
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag was updated successfully",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the tag",
                    content = @Content)
    })
    public ResponseEntity<?> update(@Parameter(description = "The tag to update") @RequestBody TagRequestDTO updateTagDTO, @PathVariable Integer id) {
        try {
            Tag updateTag = TagMapper.fromRequestDTO(updateTagDTO);
            Tag savedTag = tagService.update(updateTag, id);
            return ResponseEntity.ok(TagMapper.toResponseDTO(savedTag));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the tag");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found");
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tag could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> delete(@Parameter(description = "Id of tag to delete") @PathVariable Integer id) {
        try {
            tagService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag was not found");
        }
    }

}
