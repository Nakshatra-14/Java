package nn.binary_tree;

import java.util.StringJoiner;

import pb.dsa.treeprinter.BinaryTreePrinter;
import pb.dsa.treeprinter.PrintableBinTree;

// import pb.dsa.treeprinter.BinaryTreePrinter;
// import pb.dsa.treeprinter.PrintableBinTree;

public class BST<K extends Comparable<K>> implements PrintableBinTree<K> {
    private K key;
    private BST<K> left;
    private BST<K> right;

    public BST(K data) {
        this.key = data;
    }

    // public boolean searchKey(BST<K extends Comparable<K>> root, K k)
    // {

    // }

    // public boolean searchKeyRecusive(BST<K extends Comparable<K>> root, K k)
    // {

    // }

    public void setLeft(K data) {
        this.left = new BST<>(data);
    }

    public void setRight(K data) {
        this.right = new BST<>(data);
    }

    public static <K extends Comparable<K>> void inOrder(BST<K> t) {
        if (t != null) {
            inOrder(t.left);
            System.out.println(t.key);
            inOrder(t.right);
        }
    }

    private static <K extends Comparable<K>> void inOrderToString(StringJoiner sj, BST<K> t) {
        if (t != null) {
            inOrderToString(sj, t.left);
            sj.add(t.key.toString());
            inOrderToString(sj, t.right);
        }
        // return sb.toString();
    }

    public String inOrderToString() {
        StringJoiner sj = new StringJoiner(", ");
        inOrderToString(sj, this);
        return sj.toString();
    }

    private static <K extends Comparable<K>> void preOrderToString(StringJoiner sj, BST<K> t) {
        if (t != null) {
            sj.add(t.key.toString());
            preOrderToString(sj, t.left);
            preOrderToString(sj, t.right);
        }
        // return sb.toString();
    }

    public String preOrderToString() {
        StringJoiner sb = new StringJoiner(", ");
        preOrderToString(sb, this);
        return sb.toString();
    }

    private static <K extends Comparable<K>> void postOrderToString(StringJoiner sj, BST<K> t) {
        if (t != null) {
            postOrderToString(sj, t.left);
            postOrderToString(sj, t.right);
            sj.add(t.key.toString());
        }
        // return sb.toString();
    }

    public String postOrderToString() {
        StringJoiner sb = new StringJoiner(", ");
        postOrderToString(sb, this);
        return sb.toString();
    }

    public static BST<Character> createTreeOne() {
        BST<Character> root = new BST<Character>('A');
        root.setLeft('B');
        root.left.setLeft('D');
        root.left.left.setRight('G');
        root.setRight('C');
        root.right.setLeft('E');
        root.right.setRight('F');
        root.right.left.setLeft('H');
        root.right.left.setRight('I');

        return root;
    }

    public static BST<Character> reconstuctionPreIn(String pre, String in) {
        if (pre.length() == 0)
            return null;
        BST<Character> root = new BST<>(pre.charAt(0));

        int index = in.indexOf(pre.charAt(0));

        root.left = reconstuctionPreIn(pre.substring(1, 1 + index), in.substring(0, index));
        root.right = reconstuctionPreIn(pre.substring(index + 1), in.substring(index + 1));

        return root;
    }

    // public static BinaryTree<Character> reconstuctionPostIn(String post, String
    // in)
    // {
    // if(post.length() == )
    // BinaryTree<Character> root = new BinaryTree<>(post.charAt(post.length()));

    // }

    @Override
    public String toString() {
        return inOrderToString();
    }

    public static <K extends Comparable<K>> BST<K> reconstuctionPreIn(K pre[], int preLb, int preUb, K in[], int inLb,
            int inUb) {
        if (preLb > preUb)
            return null;

        BST<K> root = new BST<K>(pre[preLb]);
        int index = 0;
        for (int i = inLb; i <= inUb; i++) {
            if (in[i].equals(pre[preLb])) {
                index = i;
                break;
            }
        }
        int n = index - inLb;

        root.left = reconstuctionPreIn(pre, preLb + 1, preLb + n, in, inLb, index - 1);
        root.right = reconstuctionPreIn(pre, preLb + n + 1, preUb, in, index + 1, inUb);

        return root;
    }

    public static <K extends Comparable<K>> BST<K> reconstuctionPreIn(K pre[], K in[]) {
        return reconstuctionPreIn(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    public static <K extends Comparable<K>> BST<K> reconstuctionPostIn(K post[], int postLb, int postUb, K in[],
            int inLb, int inUb) {
        if (postLb > postUb)
            return null;

        BST<K> root = new BST<K>(post[postUb]);
        int index = 0;
        for (int i = inLb; i <= inUb; i++) {
            if (in[i].equals(post[postUb])) {
                index = i;
                break;
            }
        }

        root.left = reconstuctionPostIn(post, postLb, index - 1, in, inLb, index - 1);
        root.right = reconstuctionPostIn(post, index, postUb - 1, in, index + 1, inUb);

        return root;
    }

    public static <K extends Comparable<K>> BST<K> reconstuctionPostIn(K post[], K in[]) {
        return reconstuctionPostIn(post, 0, post.length - 1, in, 0, in.length - 1);
    }

    public void print() {
        BinaryTreePrinter.printBothSided(this);
    }

    // private static int getHeight(BinaryTree<K extends Comparable<K>> root)
    // {
    // if(root == null)
    // return 0;

    // return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    // }

    public int getHeight() {
        int lht, rht;
        if (left == null)
            lht = 0;
        else
            lht = left.getHeight();

        if (right == null)
            rht = 0;
        else
            rht = right.getHeight();

        return 1 + Math.max(lht, rht);
    }

    public int countNode() {
        int lht, rht;
        if (left == null)
            lht = 0;
        else
            lht = left.countNode();

        if (right == null)
            rht = 0;
        else
            rht = right.countNode();

        return 1 + lht + rht;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BST t && this.key.equals(t.key) && this.left.equals(t.left)
                && this.right.equals(t.right);
    }

    public static <K extends Comparable<K>> int getLevel(BST<K> root, BST<K> node) {
        if (root == null)
            return -2;
        if (root == node)
            return 0;
        else {
            int lvl = getLevel(root.left, node);
            if (lvl == -2)
                lvl = getLevel(root.right, node);

            if (lvl < 0)
                return lvl;
            else
                return 1 + lvl;
        }
    }

    public static void main(String[] args) {
        BST<Character> treeOne = createTreeOne();
        // System.out.println("Preorder :" + preOrderToString(treeOne));
        // System.out.println("Inorder :" + inOrderToString(treeOne));
        // System.out.println("Postorder:" + postOrderToString(treeOne));

        // String inorder = treeOne.inOrderToString();
        // String preOrder = treeOne.preOrderToString();
        // System.out.println(preOrder);
        // System.out.println(inorder);
        // BinaryTree<Character> tree = reconstuctionPreIn(preOrder, inorder);
        // System.out.println(tree.postOrderToString());
        // System.out.println(tree);

        // Integer preOrder[] = {14, 4, 3, 9, 7, 5, 15, 18, 16, 17, 20};
        // Integer inOrder[] = {3, 4, 5, 7, 9, 14, 15, 16, 17, 18, 20};
        // Integer postOrder[] ={3, 5, 7, 9, 4, 17, 16, 20, 18, 15, 14};

        // Integer preOrder[] = {250, 120, 530, 650, 830, 160, 260, 190, 370};
        // Integer inOrder[] = {530, 650, 120, 250, 260, 160, 190, 830, 370};
        // Integer postOrder[] ={650, 530, 120, 260, 190, 160, 370, 830, 250};

        Integer preOrder[] = { 53, 28, 16, 25, 20, 27, 32, 80, 65, 60, 70 };
        Integer inOrder[] = { 16, 20, 25, 27, 28, 32, 53, 60, 65, 70, 80 };

        BST<Integer> root = reconstuctionPreIn(preOrder, inOrder);

        // BinaryTree<Integer> root = reconstuctionPostIn(postOrder, inOrder);

        System.out.println(root.preOrderToString());
        System.out.println(root);
        System.out.println(root.postOrderToString());
        // System.out.println(root.postOrderToString());
        root.print();

        System.out.println("Height: " + root.getHeight());
        System.out.println("Nodes: " + root.countNode());

        int v = 69;
        if (searchKey(root, v))
            System.out.println("Found " + v + " in root");
        else
            System.out.println("Not Found " + v + " in root");

        root = addKey(root, 100);
        root.print();
    }

    @Override
    public K getData() {
        return key;
    }

    @Override
    public PrintableBinTree<K> getLeft() {
        return left;
    }

    @Override
    public PrintableBinTree<K> getRight() {
        return right;
    }

    public static <K extends Comparable<K>> boolean searchKeyRecursive(BST<K> root, K key) {
        if (root == null)
            return false;
        int c = key.compareTo(root.key);
        if (c == 0)
            return true;
        else if (c < 0) // key < root.key
            return searchKeyRecursive(root.left, key);
        else {
            return searchKeyRecursive(root.right, key);
        }
    }

    public static <K extends Comparable<K>> boolean searchKey(BST<K> root, K key) {
        BST<K> tmp = root;
        while (tmp != null) {
            int c = key.compareTo(tmp.key);

            if (c == 0)
                break;
            else if (c < 0)
                tmp = tmp.left;
            else
                tmp = tmp.right;
        }
        return tmp != null;
    }

    public static <K extends Comparable<K>> BST<K> addKey(BST<K> root, K key) {
        if (root == null)
            return new BST<K>(key);
        BST<K> tmp = root;

        while (tmp != null) {
            int c = key.compareTo(tmp.key);

            if (c == 0)
                return root;
            else if (c < 0) {
                if (tmp.left != null)
                    tmp = tmp.left;
                else {
                    tmp.left = new BST<K>(key);
                    return root;
                }
            } else if (tmp.right != null)
                tmp = tmp.right;
            else {
                tmp.right = new BST<K>(key);
                return root;
            }
        }
        return root;
    }

    public static <K extends Comparable<K>> BST<K> addKeyRecursive(BST<K> root, K key) {
        if (root == null)
            return new BST<K>(key);

        int c = key.compareTo(root.key);
        if (c < 0) {
            root.left = addKeyRecursive(root.left, key);
        } else if (c > 0) {
            root.right = addKeyRecursive(root.right, key);
        }

        return root;
    }

    public static <K extends Comparable<K>> void removeKey(BST<K> root, K delKey) {
        BST<K> n = root;
        BST<K> p = null;
        while (n != null) {
            int c = delKey.compareTo(n.key);

            if (c == 0)
                break;
            else if (c < 0) {
                p = n;
                n = n.left;
            } else {
                p = n;
                n = n.right;
            }
        }

        if (n != null) {
            // if(tmp.right == null && tmp.left == null) //n has no non empty child
            // {
            // //set corrosponding (to which tmp is child of p) child of p to null
            // if(tmp == p.left)
            // p.left = null;
            // else
            // p.right = null;
            // }
            // else //n has 1 non empty child

            // if(p.left == null)
            // if(n.right != null)
            // p.right = n.right;
            // else
            // p.right = n.left;

            // else if(p.right == null)
            // if(n.left != null)
            // p.left = n.right;
            // else
            // p.left = n.left;
            // else //tmp has both the children

            if (n.right == null) // if n has no children or only the left child
            {
                // set corrosponding link of p to the left child of n
                if (n == p.left)
                    p.left = n.left;
                // else if(p.left == null)
                //     p = 
                else
                    p.right = n.left;
            } else if (n.left == null) // n has no child or only the right child
            {
                if (n == p.left)
                    p.left = n.right;
                else
                    p.right = n.right;
            } else // n has both the children
            {
                // find the inorder successor of n and make p point to the parent of s
                // go to right of n once
                p = n;
                BST<K> s = n.right;
                // continously go to left
                while (s.left != null) {
                    p = s;
                    s = s.left;
                }
                // now copy
                n.key = s.key;
                // System.out.println("p.right.key = " + p.right.key);
                // System.out.println("p.right = " + p.right);
                // now delete the node s
                if (s == p.left)
                    p.left = s.right;
                else
                    p.right = s.right;
                // p.right.key = null;
            }
        }

    }

    public static <K extends Comparable<K>> BST<K> showEveryInsertTree(BST<K> root, K key)
    {
        if (root == null)
            return new BST<K>(key);
        addKeyRecursive(root, key);
        root.print();
        return root;
    }
}
