public class OffByN implements CharacterComparator{

    int dist;
    public OffByN(int N) {
        dist = N;
    }

    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == dist;
    }
}
