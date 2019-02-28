package google.hash_code_2019.model;

import java.util.ArrayList;
import java.util.List;

public class Tags implements Comparable<Tags>{

  public Long iteration ;

  public String name ;
  public List<Integer> listPhotoId = new ArrayList<>();



  public Tags(String tag, int idPhoto) {
    name = tag;
    listPhotoId.add(idPhoto);
    iteration = 1L;
  }

  public void addPhoto(int idPhoto) {
    listPhotoId.add(idPhoto);
    iteration++;
  }


  @Override
  public int compareTo(Tags o) {
    return o.iteration.compareTo(iteration);
  }
}
