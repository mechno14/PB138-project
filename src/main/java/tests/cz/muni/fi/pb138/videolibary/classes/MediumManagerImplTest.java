package tests.cz.muni.fi.pb138.videolibary.classes;

import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;
import cz.muni.fi.pb138.videolibrary.manager.MediumManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class MediumManagerImplTest {

    private MediumManagerImpl manager;

    private MediumBuilder medium42Untouchables() {
        return new MediumBuilder()
                .name("Untouchables")
                .releaseYear(1995)
                .category(new Category("Movies"))
                .mediumType(MediumType.DVD)
                .actors(new HashSet<>())
                .genres(new HashSet<>());
    }

    @BeforeEach
    void setXmlDBManager() throws Exception {
        XMLDBManagerImpl xmlDBManager = new XMLDBManagerImpl();
        manager =  new MediumManagerImpl(xmlDBManager);
    }

    @Test
    void createNullMedium() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            manager.createMedium(null);
        });
    }

    @Test
    void createMediumWithNullName() {
        Medium medium = new MediumBuilder().name(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }

    @Test
    void createMediumWithNullMediumType() {
        Medium medium = new MediumBuilder().mediumType(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }

    @Test
    void createMediumWithNullCategory() {
        Medium medium = new MediumBuilder().category(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }

    @Test
    void createMediumWithNullActors() {
        Medium medium = new MediumBuilder().actors(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }

    @Test
    void createMediumWithNullGenres() {
        Medium medium = new MediumBuilder().genres(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }

    @Test
    void createMediumWithIncorrectLength() {
        Medium medium = new MediumBuilder().length(-150).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }

    @Test
    void createMediumWithEmptyName() {
        Medium medium = new MediumBuilder().name("").build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }

    @Test
    void createMediumWithNullId() {
        Medium medium = new MediumBuilder().id(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createMedium(medium);
        });
    }


    @Test
    void deleteNullMedium() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            manager.deleteMedium(null);
        });
    }

    @Test
    void deleteMediumWithNullName() {
        Medium medium = new MediumBuilder().name(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void deleteMediumWithNullMediumType() {
        Medium medium = new MediumBuilder().mediumType(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void deleteMediumWithNullCategory() {
        Medium medium = new MediumBuilder().category(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void deleteMediumWithNullActors() {
        Medium medium = new MediumBuilder().actors(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void deleteMediumWithNullGenres() {
        Medium medium = new MediumBuilder().genres(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void deleteMediumWithIncorrectLength() {
        Medium medium = new MediumBuilder().length(-150).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void deleteMediumWithEmptyName() {
        Medium medium = new MediumBuilder().name("").build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void deleteMediumWithNullId() {
        Medium medium = new MediumBuilder().id(null).build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteMedium(medium);
        });
    }

    @Test
    void findMediumWithNullId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            manager.findMediumById(null);
        });
    }

    @Test
    void findMediumById() {
        Medium medium = medium42Untouchables().build();
        Long id = medium.getId();
        manager.createMedium(medium);
        Assertions.assertTrue(manager.findMediumById(id).getName().equals("Untouchables"));
    }

    @Test
    void removeMedium() {
        Medium medium = medium42Untouchables().build();
        Long id = medium.getId();
        manager.createMedium(medium);
        Assertions.assertTrue(manager.findMediumById(id) != null);
        manager.deleteMedium(medium);
        Assertions.assertTrue(manager.findMediumById(id) == null);
    }

    @Test
    void findMediumWithNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            manager.findMediumByName(null);
        });
    }

    @Test
    void findMediumWithEmptyName() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            manager.findMediumByName("");
        });
    }

    /*
    @Test
    void findMediumByName() {
        Medium medium = medium42Untouchables().build();
        Long id = medium.getId();
        manager.createMedium(medium);
        Assertions.assertTrue(manager.findMediumByName("Untouchables").getId().equals(id));
    }*/




}
