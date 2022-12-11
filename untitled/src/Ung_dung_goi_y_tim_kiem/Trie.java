package Ung_dung_goi_y_tim_kiem;
//Ứng dụng gợi ý tìm kiếm cho search engine(google search, google map, wikipedia, netflix, youtube,...)

import java.util.*;

public class Trie {

    public class TrieNode {
        Map<Character, TrieNode> children;
        char c;
        boolean isWord;

        public TrieNode(char c) {
            this.c = c;
            children = new HashMap<>();
        }

        public TrieNode() {
            children = new HashMap<>();
        }

        public void insert(String word) {
            if (word == null || word.isEmpty())
                return;
            char firstChar = word.charAt(0);
            TrieNode child = children.get(firstChar);
            if (child == null) {
                child = new TrieNode(firstChar);
                children.put(firstChar, child);
            }

            if (word.length() > 1)
                child.insert(word.substring(1));
            else
                child.isWord = true;
        }

    }


    static TrieNode root;
    static List<String> words;

    public Trie(List<String> words) {
        root = new TrieNode();
        for (String word : words)
            root.insert(word);

    }

    public boolean find(String prefix, boolean exact) {
        TrieNode lastNode = root;
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return false;
        }
        return !exact || lastNode.isWord;
    }

    public boolean find(String prefix) {
        return find(prefix, false);
    }

    public void suggestHelper(TrieNode root, List<String> list, StringBuffer curr) {
        if (root.isWord) {
            list.add(curr.toString());
        }

        if (root.children == null || root.children.isEmpty())
            return;

        for (TrieNode child : root.children.values()) {
            suggestHelper(child, list, curr.append(child.c));
            curr.setLength(curr.length() - 1);
        }
    }

    //Thêm từ khóa này vào words nếu nó chưa có trong words
    public static void addWord(String word) {
        root.insert(word);
        words.add(word);
    }

    //Đưa ra gợi ý các từ khóa có thể đang tìm kiếm
    public List<String> suggest(String prefix) {
        List<String> list = new ArrayList<>();
        TrieNode lastNode = root;
        StringBuffer curr = new StringBuffer();
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return list;
            curr.append(c);
        }
        suggestHelper(lastNode, list, curr);
        return list;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] info = new String[]{"hello", "dog", "hell", "cat", "a", "hel", "help", "helps", "helping"};
        words = new LinkedList<>();
        words.addAll(Arrays.asList(info));
        Trie trie = new Trie(words);
        while (true) {
            System.out.print("Enter a prefix: ");
            String prefix = sc.nextLine();
            List<String> list = trie.suggest(prefix);
            if (!trie.find(prefix)) {
                System.out.println("Not found");
                addWord(prefix);
            }
            else {
                System.out.println(list);
            }
        }
    }
}