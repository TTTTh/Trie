package edu.lab421;

import java.io.Serializable;
import java.util.HashMap;

public class TrieNode implements Serializable {
    public HashMap<String, TrieNode> edge;
    public String word;
    public boolean isEnd;
    public int hit;
//    public int leafCnt;
    public TrieNode(String curWord){
        hit = 0;
        word = curWord;
//        leafCnt = 0;
        isEnd = false;
        edge = new HashMap<String, TrieNode>();
    }
}
