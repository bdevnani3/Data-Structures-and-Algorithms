import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Iterator;


public class Huffman {

    // Do NOT add any variables (instance or static)

    /**
     * Builds a frequency map of characters for the given string.
     *
     * This should just be the count of each character.
     * No character not in the input string should be in the frequency map.
     *
     * @param s the string to map
     * @return the character -> Integer frequency map
     */
    public static Map<Character, Integer> buildFrequencyMap(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        HashMap<Character, Integer> result = new HashMap<Character, Integer>();

        for (int i = 0; i < s.length(); i++) {
            Integer intObj = result.get(s.charAt(i));
            int tmpCount;
            if (intObj == null) {
                tmpCount = 0;
            } else {
                tmpCount = result.get(s.charAt(i));
            }
            result.put(s.charAt(i), ++tmpCount);
        }

        return result;
    }

    /**
     * Build the Huffman tree using the frequencies given.
     *
     * Add the nodes to the tree based on their natural ordering (the order
     * given by the compareTo method).
     * The frequency map will not necessarily come from the
     * {@code buildFrequencyMap()} method. Every entry in the map should be in
     * the tree.
     *
     * @param freq the frequency map to represent
     * @return the root of the Huffman Tree
     */
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freq) {
        if (freq == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        PriorityQueue<HuffmanNode> hfTrees = new PriorityQueue<HuffmanNode>();

        for (Map.Entry<Character, Integer> item : freq.entrySet()) {
            hfTrees.add(new HuffmanNode(item.getKey() , item.getValue()));
        }

        while (hfTrees.size() > 1) {
            HuffmanNode left = hfTrees.poll();
            HuffmanNode right = hfTrees.poll();
            hfTrees.offer(new HuffmanNode(left, right));
        }

        return hfTrees.poll();
    }

    /**
     * Traverse the tree and extract the encoding for each character in the
     * tree.
     *
     * The tree provided will be a valid huffman tree but may not come from the
     * {@code buildHuffmanTree()} method.
     *
     * @param tree the root of the Huffman Tree
     * @return the map of each character to the encoding string it represents
     */
    public static Map<Character, EncodedString> buildEncodingMap(
            HuffmanNode tree) {
        
        if (tree == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        
        Map<Character, EncodedString> ret = new HashMap<>();
        
        if (tree.getLeft() == null && tree.getRight() == null) {
            EncodedString e = new EncodedString();
            e.zero();
            ret.put(tree.getCharacter(), e);
            return ret;
        }
        
       
        buildEncoding(tree, new EncodedString(), ret);
        
        return ret;
    }
    
    private static void buildEncoding(HuffmanNode node, EncodedString curr, Map<Character, EncodedString> outMap) {
        if (node.getLeft() == null && node.getRight() == null) {
            outMap.put(node.getCharacter(), curr);
        }
        
        if (node.getLeft() != null) {
            EncodedString nextEnc = new EncodedString();
            nextEnc.concat(curr);
            nextEnc.zero();
            buildEncoding(node.getLeft(), nextEnc, outMap);
        }

        if (node.getRight() != null) {
            EncodedString nextEnc = new EncodedString();
            nextEnc.concat(curr);
            nextEnc.one();
            buildEncoding(node.getRight(), nextEnc, outMap);
        }
    }

    /**
     * Encode each character in the string using the map provided.
     *
     * If a character in the string doesn't exist in the map ignore it.
     *
     * The encoding map may not necessarily come from the
     * {@code buildEncodingMap()} method, but will be correct for the tree given
     * to {@code decode()} when decoding this method's output.
     *
     * @param encodingMap the map of each character to the encoding string it
     * represents
     * @param s the string to encode
     * @return the encoded string
     */
    public static EncodedString encode(Map<Character, EncodedString>
            encodingMap, String s) {
        if (encodingMap == null || s == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        EncodedString ret = new EncodedString();
        
        for (char key : s.toCharArray()) {
            EncodedString es = encodingMap.get(key);
            if (es != null) {
                ret.concat(es);
            }
        }
        
        return ret;
    }

    /**
     * Decode the encoded string using the tree provided.
     *
     * The encoded string may not necessarily come from {@code encode()}, but
     * will be a valid string for the given tree.
     *
     * (tip: use StringBuilder to make this method faster -- concatenating
     * strings is SLOW.)
     *
     * @param tree the tree to use to decode the string
     * @param es the encoded string
     * @return the decoded string
     */
    public static String decode(HuffmanNode tree, EncodedString es) {
        if (tree == null || es == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        
        StringBuilder ret = new StringBuilder();
        
        HuffmanNode curr = tree;
        Iterator<Byte> itor = es.iterator();
        while (itor.hasNext()) {
            
            Byte bit = itor.next();
            if (curr.getCharacter() == 0){
                if (bit == 0) {
                curr = curr.getLeft();
                } else {
                curr = curr.getRight();
                }
            }
            if (curr.getCharacter() != 0) {
                ret.append(curr.getCharacter());
                curr = tree;
            }
        }
        
        return ret.toString();
    }
}

