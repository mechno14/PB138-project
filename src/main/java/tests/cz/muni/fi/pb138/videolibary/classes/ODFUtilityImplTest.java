package tests.cz.muni.fi.pb138.videolibary.classes;

/**
 * @author Simona Bennárová
 */

import cz.muni.fi.pb138.videolibrary.manager.ODFUtility;
import cz.muni.fi.pb138.videolibrary.manager.ODFUtilityImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ODFUtilityImplTest {
    private ODFUtility odf  = new ODFUtilityImpl();

    @Test
    void readFileWithNullPath() {
        String nullString = null;
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            odf.readFile(nullString);
        });
    }

    @Test
    void readFileNullParameter() {
        File nullFile = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            odf.readFile(nullFile);
        });
    }

    @Test
    void tranformToWithNullParameter() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            odf.transformToSet(null);
        });
    }

    @Test
    void transformToDocumentNull() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            odf.transformToDocument(null);
        });
    }

    @Test
    void transformInvalidFile() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  {
            odf.transformToSet(odf.readFile("src/main/java/tests/cz/muni/fi/pb138/videolibary/testfiles/invalid_ODF.ods"));
        });
    }

    @Test
    void transformValidFile() throws IOException {
        Assertions.assertNotNull(odf.transformToSet(odf.readFile("src/main/java/tests/cz/muni/fi/pb138/videolibary/testfiles/valid_ODF.ods")));
    }
    @Test
    void readValidFile() throws IOException {
        Assertions.assertNotNull(odf.readFile("src/main/java/tests/cz/muni/fi/pb138/videolibary/testfiles/valid_ODF.ods"));
    }

    @Test
    void writeFileNullParameter() {
        File nullFile = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            odf.writeFile(nullFile, null);
        });
    }
    @Test
    void writeFilePathNullParameter() {
        String nullString = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            odf.writeFile(nullString, null);
        });
    }
}



