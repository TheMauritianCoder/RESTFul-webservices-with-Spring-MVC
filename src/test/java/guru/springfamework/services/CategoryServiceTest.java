package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.mapper.CategoryMapper;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    public static final Long ID = 2L;
    public static final String NAME = "jimmy";

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE,categoryRepository);
    }

    @Test
    public void getAllCategories() {

        // given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        // when
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

        // then
        assertEquals(3,categoryDTOs.size());

    }

    @Test
    public void getCategoryByName() {

        // given
        Category category = new Category();
        category.setId(ID);
        category.setName("nuts");

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        // when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        // then
        assertEquals(ID,categoryDTO.getId());
        assertEquals(NAME,categoryDTO.getName());

    }
}