package google.hash_code_2019.model;

import java.util.HashSet;
import java.util.Set;

public class Photo {

  public boolean horizontal;
  public Set<String> tags;
  public int idPhoto;


  public Photo(int idPhoto, String[] lineParsed) {
    horizontal = "H".equals(lineParsed[0]);
    tags = new HashSet<>();
    this.idPhoto =idPhoto;
    for(int i = 2;i < lineParsed.length;i++){
        tags.add(lineParsed[i]);
    }
  }

  @Override
  public String toString() {
    return "Photo{" +
            "horizontal=" + horizontal +
            ", tags=" + tags +
            ", idPhoto=" + idPhoto +
            '}';
  }
}
