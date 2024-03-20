package ch.bbcag.backend.todolist.controllers;

import ch.bbcag.backend.todolist.item.Item;
import ch.bbcag.backend.todolist.item.ItemController;
import ch.bbcag.backend.todolist.item.ItemService;
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

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void checkPost_whenValidNewItem_thenIsCreated() throws Exception {
        Mockito.when(itemService.insert(any(Item.class))).thenReturn(TestDataUtil.getTestItem());

        mockMvc.perform(post(ItemController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Item1\", \"personId\":\"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Item1")));
    }

    @Test
    public void checkPost_whenInvalidItem_thenIsBadRequest() throws Exception {

        Mockito.when(itemService.insert(any(Item.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post(ItemController.PATH)
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Item1\"}"))
                .andExpect(status().isBadRequest());
    }

}
