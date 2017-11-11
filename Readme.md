# Trie Readme
------------

## Class TrieTree
> 这是一棵字典树的java实现版本
> 节点和边信息都存储于内存中
> 未来视项目使用情况，存在将存储结构改动，修改至外部存储的可能性

* TrieTree 实现了 接口 Serializable，可以被序列化存储到本地，从而避免每一次启动时都需要重新建立整个树的问题
```
FileOutputStream fs = new FileOutputStream("TrieTree.bin");
ObjectOutputStream oos = new ObjectOutputStream(fs);
oos.writeObject(tr);
oos.close();
fs.close();
FileInputStream fin = new FileInputStream("TrieTree.bin");

ObjectInputStream oin = new ObjectInputStream(fin);
tr = (TrieTree) oin.readObject();
```

* TrieTree提供了两个接口用于树的建立，一个是添加单个输入/记录的**AddWords**和批量读取的**ReadRecords**。**ReadRecords**要求每一行一个输入/记录
* **QuerySubTree**适用于查找用户热门输入的补全，**QuerySubTree**是由于查找所有输入
* **MatchKeyWords**适用于查找一行或者多行内，有没有此前被加入到TrieTree中的关键字。
