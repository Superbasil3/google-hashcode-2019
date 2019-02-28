package google.hash_code_2019.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Slide {
    public Photo photo1;
    public Photo photo2 = null;
    public Set<String> tags;


    public Slide(Photo photo1, Photo photo2) {
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.tags = calculateTags();
    }

    public Slide(Photo p1) {
        this.photo1 = p1;
        this.tags = calculateTags();
    }
    public Slide() {
    }

    public Set<String> getTags() {
        return this.tags;
    }

    private Set<String> calculateTags() {
        Set<String> tags = new HashSet<>();
        if (photo2 != null) {
            tags.addAll(photo2.tags);
        }
        tags.addAll(photo1.tags);
        return tags;
    }

    public void addPhoto2(Photo photo) {
        photo2 = photo;
        calculateTags();
    }
    public void addPhoto1(Photo photo) {
        photo1 = photo;
        calculateTags();
    }

}
