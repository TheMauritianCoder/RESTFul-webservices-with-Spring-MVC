package guru.springfamework.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    public static final String JOE = "joe";
    public static final Long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void categoryToCategoryDto() {

        // given
        Category category = new Category();
        category.setName(JOE);
        category.setId(ID);

        // when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDto(category);

        // then
        assertEquals(ID ,categoryDTO.getId());
        assertEquals(JOE,categoryDTO.getName());

    }
}