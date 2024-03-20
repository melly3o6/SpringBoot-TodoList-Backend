package ch.bbcag.backend.todolist.controllers;

import ch.bbcag.backend.todolist.tag.Tag;
import ch.bbcag.backend.todolist.tag.TagController;
import ch.bbcag.backend.todolist.tag.TagService;
import ch.bbcag.backend.todolist.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TagController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Test
    public void checkPost_whenValidNewTag_thenIsCreated() throws Exception {
        Mockito.when(tagService.insert(any(Tag.class))).thenReturn(TestDataUtil.getTestTag());

        mockMvc.perform(post(TagController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Tag1\", \"personId\":\"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Item1")));
    }

    @Test
    public void checkPost_whenInvalidTag_thenIsBadRequest() throws Exception {

        Mockito.when(tagService.insert(any(Tag.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post(TagController.PATH)
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Tag1\"}"))
                .andExpect(status().isBadRequest());
    }


}
