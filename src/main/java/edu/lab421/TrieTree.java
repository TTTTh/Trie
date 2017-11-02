package edu.lab421;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TrieTree implements Serializable {
    public static Logger logger;
    private TrieNode root;

    public TrieTree(){
        root = new TrieNode("root");
    }

    /*往字典树中添加内容*/
    public boolean AddWords(String words){

//        logger.info("add words:" + words);
        TrieNode curNode = root;

        char [] cs = words.toCharArray();
        for(char c: cs){
//            System.out.println(c);
            String val = String.valueOf(c);
            if(!curNode.edge.containsKey(val)){
                TrieNode nxt = new TrieNode(val);
                curNode.edge.put(val, nxt);
            }
            curNode.hit++;
            curNode = curNode.edge.get(val);
        }

        //叶子节点标记
        curNode.isLeaf = true;
        return true;
    }

    public ArrayList<String> QueryAllSubTree(String prefix){
        ArrayList<String> ans = new ArrayList<String>();
        TrieNode curNode = root;
        char [] cs = prefix.toCharArray();
//        TrieNode nxt = null;
        for(char c: cs){
            String nxtName = String.valueOf(c);
            if(curNode.edge.containsKey(nxtName)){
//                nxt = curNode.edge.get(nxtName);
//                curNode = nxt;
                curNode = curNode.edge.get(nxtName);
            }
            else{
                curNode = null;
                break;
            }
        }
        if(curNode != null){
//            double threshold = curNode.hit * 0.1f;
            DeepSearchLeaves(prefix, curNode, ans, 0);
        }
        return ans;
    }

    public ArrayList<String> QuerySubTree(String prefix){
        ArrayList<String> ans = new ArrayList<String>();
        TrieNode curNode = root;
        char [] cs = prefix.toCharArray();
//        TrieNode nxt = null;
        for(char c: cs){
            String nxtName = String.valueOf(c);
            if(curNode.edge.containsKey(nxtName)){
//                nxt = curNode.edge.get(nxtName);
//                curNode = nxt;
                curNode = curNode.edge.get(nxtName);
            }
            else{
                curNode = null;
                break;
            }
        }
        if(curNode != null){
            double threshold = curNode.hit * 0.1f;
            DeepSearchLeaves(prefix, curNode, ans, threshold);
        }
        return ans;
    }

    public void ShowLeaves(){
        ArrayList<String> words = new ArrayList<String>();
        DeepSearchLeaves("", root, words, 0);
        for(String str: words){
            System.out.println(str);
        }
    }

    public static List<String> readFileByLines(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            return null;
        }

        List<String> content = new ArrayList<String>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String lineContent = "";
            while ((lineContent = reader.readLine()) != null) {
                content.add(lineContent);
                //System.out.println(lineContent);
            }

            fileInputStream.close();
            inputStreamReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public boolean ReadRecords(String filePath){
        List<String> lines = readFileByLines(filePath);
        for(String line: lines){
            AddWords(line);
        }
        return true;
    }

    /*遍历当前节点子树*/
    public void DeepSearchLeaves(String prefix, TrieNode curNode, ArrayList<String> rstLst, double threshold){
//        System.out.println("prefix = " + prefix);
        boolean isLogicalLeaf = true;
        if(curNode.isLeaf){
            rstLst.add(prefix);
        }

        for(Map.Entry<String, TrieNode> entry: curNode.edge.entrySet()){
//            if(isLeaf){
//                isLeaf = false;
//            }
            String nxtPrefix = prefix + entry.getKey();
            TrieNode nxtNode = entry.getValue();
            /*界定一个阈值，小于阈值的节点不再进行*/
            if(nxtNode.hit < threshold){
                continue;
            }
            if(isLogicalLeaf){
                isLogicalLeaf = false;
            }
            DeepSearchLeaves(nxtPrefix, nxtNode, rstLst, threshold);
        }

        if(isLogicalLeaf){
//            System.out.println(prefix);
            rstLst.add(prefix);
        }
    }



    public static void main(String ... args) throws Exception{
        logger = Logger.getLogger("testLoger");
        logger.info("begin test the Trie Tree");

        TrieTree tr = new TrieTree();
        String testRecordPath = "/Users/tenghaibin/Downloads/TestSearchRecord.txt";
        tr.ReadRecords(testRecordPath);
//        tr.AddWords("今天新的藻类治理方案和指标出来了吗");
//        tr.AddWords("今天新的藻类1233333");
//        tr.AddWords("昨天新的藻类治理方案和指标出来了吗");
//        tr.ShowLeaves();

        /*测试序列化存储*/
        /*
        FileOutputStream fs = new FileOutputStream("TrieTree.bin");

        ObjectOutputStream oos = new ObjectOutputStream(fs);

        oos.writeObject(tr);

        oos.close();
        fs.close();

        FileInputStream fin = new FileInputStream("TrieTree.bin");

        ObjectInputStream oin = new ObjectInputStream(fin);

//        tr = null;

        tr = (TrieTree) oin.readObject();
        */

        System.out.println("query all result for: 146");
        ArrayList<String> ans = tr.QueryAllSubTree("146");
        for(String str: ans){
            System.out.println(str);
        }

        System.out.println("query frequency result for: 146");
        ans = tr.QuerySubTree("146");
        for(String str: ans){
            System.out.println(str);
        }
        System.out.println("query frequency result for: 1468");
        ans = tr.QuerySubTree("1469");
        for(String str: ans){
            System.out.println(str);
        }
    }
}
