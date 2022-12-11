import java.util.LinkedList;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode(' ');
    }

    public static class TrieNode {
        char data;
        boolean isEnd;
        int count;
        LinkedList<TrieNode> childList;

        public TrieNode(char c) {
            childList = new LinkedList<>();
            isEnd = false;
            data = c;
            count = 0;
        }

        public TrieNode getChild(char c) {
            if (childList != null) {
                for (TrieNode eachChild : childList) {
                    if (eachChild.data == c) {
                        return eachChild;
                    }
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "TrieNode{" +
                    "data=" + data +
                    ", isEnd=" + isEnd +
                    ", count=" + count +
                    ", childList=" + childList +
                    '}';
        }
    }

    public void insert(String word) {
        if (search(word) == true) {
            return;
        }
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            TrieNode child = current.getChild(ch);
            if (child != null) {
                current = child;
            } else {
                current.childList.add(new TrieNode(ch));
                current = current.getChild(ch);
            }
            current.count++;
        }
        current.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (current.getChild(ch) == null) {
                return false;
            } else {
                current = current.getChild(ch);
            }
        }
        if (current.isEnd == true) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("hello");
        trie.insert("hell");
        trie.insert("cong");
        if (trie.search("hell")== true) {
            System.out.println("hell is present");
        } else {
            System.out.println("hell is not present");
        }

        if (trie.search("hello0")== true) {
            System.out.println("hello0 is present");
        } else {
            System.out.println("hello0 is not present");
        }
        System.out.print(trie.root.childList);
    }

}