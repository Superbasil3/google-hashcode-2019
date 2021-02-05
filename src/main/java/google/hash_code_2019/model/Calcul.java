package google.hash_code_2019.model;

public class Calcul {
    public int s1id;
    public Slide s1;
    public int s2id;
    public Slide s2;
    public int common;
    public int inS1Only;
    public int inS2Only;
    public int factor;

    public Calcul(int s1id, Slide s1, int s2id, Slide s2, int common, int inS1Only, int inS2Only, int factor) {
        this.s1id = s1id;
        this.s1 = s1;
        this.s2id = s2id;
        this.s2 = s2;
        this.common = common;
        this.inS1Only = inS1Only;
        this.inS2Only = inS2Only;
        this.factor = factor;
    }
}
