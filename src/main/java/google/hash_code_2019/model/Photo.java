package google.hash_code_2019.model;

import java.util.HashSet;
import java.util.Set;

public class Photo {

  public boolean horizontal;
  public Set<String> tags;


  public Photo(String[] lineParsed) {
    horizontal = "H".equals(lineParsed[0]);
    tags = new HashSet<>();
    for(int i = 2;i < lineParsed.length;i++){
        tags.add(lineParsed[i]);
    }
  }
}
