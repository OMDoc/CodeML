class Test {

public int find (int x) {
    if (s[x] < 0) return x;
    return find (s[x]);
}

}
