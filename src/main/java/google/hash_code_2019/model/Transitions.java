package google.hash_code_2019.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Transitions {
    public Photo photo1;
    public Photo photo2;


    public Transitions(Photo photo1, Photo photo2) {
        this.photo1 = photo1;
        this.photo2 = photo2;
    }

    public Transitions(Photo p1) {
        this.photo1 = p1;
    }

    public Set<String> getTags() {
        Set<String> tags = new HashSet<>();
        if (photo2 != null) {
            tags.addAll(photo2.tags);
        }
        tags.addAll(photo1.tags);
        return tags;
    }
}
