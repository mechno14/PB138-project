package cz.muni.fi.pb138.videolibrary.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Category {
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Set<Medium> media) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
