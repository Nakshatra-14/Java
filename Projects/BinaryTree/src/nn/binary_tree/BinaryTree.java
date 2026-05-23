package nn.binary_tree;

import java.util.StringJoiner;

import pb.dsa.treeprinter.BinaryTreePrinter;
import pb.dsa.treeprinter.PrintableBinTree;

// import pb.dsa.treeprinter.BinaryTreePrinter;
// import pb.dsa.treeprinter.PrintableBinTree;

public class BinaryTree<T> implements PrintableBinTree<T>
{
    private T data;
    private BinaryTree<T> left;
    private BinaryTree<T> right;
    
    public BinaryTree(T data) {
        this.data = data;
    }

    

    public void setLeft(T data) {
        this.left = new BinaryTree<>(data);
    }



    public void setRight(T data) {
        this.right = new BinaryTree<>(data);
    }



    public static <T> void inOrder(BinaryTree<T> t)
    {
        if(t != null)
        {
            inOrder(t.left);
            System.out.println(t.data);
            inOrder(t.right);
        }
    }

    private static <T> void inOrderToString(StringJoiner sj, BinaryTree<T> t)
    {
        if(t != null)
        {
            inOrderToString(sj, t.left);
            sj.add(t.data.toString());
            inOrderToString(sj, t.right);
        }
        // return sb.toString();
    }

    public String inOrderToString()
    {
        StringJoiner sj = new StringJoiner(", ");
        inOrderToString(sj, this);
        return sj.toString();
    } 

    private static <T> void preOrderToString(StringJoiner sj, BinaryTree<T> t)
    {
        if(t != null)
        {
            sj.add(t.data.toString());
            preOrderToString(sj, t.left);
            preOrderToString(sj, t.right);
        }
        // return sb.toString();
    }

    public String preOrderToString()
    {
        StringJoiner sb = new StringJoiner(", ");
        preOrderToString(sb, this);
        return sb.toString();
    } 

    private static <T> void postOrderToString(StringJoiner sj, BinaryTree<T> t)
    {
        if(t != null)
        {
            postOrderToString(sj, t.left);
            postOrderToString(sj, t.right);
            sj.add(t.data.toString());
        }
        // return sb.toString();
    }

    public String postOrderToString()
    {
        StringJoiner sb = new StringJoiner(", ");
        postOrderToString(sb, this);
        return sb.toString();
    } 


    public static BinaryTree<Character> createTreeOne()
    {
        BinaryTree<Character> root = new BinaryTree<Character>('A');
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

    public static BinaryTree<Character> reconstuctionPreIn(String pre, String in)
    {
        if(pre.length() == 0)
            return null;
        BinaryTree<Character> root = new BinaryTree<>(pre.charAt(0));

        int index =in.indexOf(pre.charAt(0));

        root.left = reconstuctionPreIn(pre.substring(1, 1+index), in.substring(0, index));
        root.right = reconstuctionPreIn(pre.substring(index+1), in.substring(index+1));

        return root;
    }

    // public static BinaryTree<Character> reconstuctionPostIn(String post, String in)
    // {
    //     if(post.length() == )
    //     BinaryTree<Character> root = new BinaryTree<>(post.charAt(post.length()));

    // }

    @Override
    public String toString() {
        return inOrderToString();
    }

    public static <T> BinaryTree<T> reconstuctionPreIn(T pre[], int preLb, int preUb, T in[], int inLb, int inUb)
    {
        if(preLb > preUb)
            return null;

        BinaryTree<T> root = new BinaryTree<T>(pre[preLb]);
        int index = 0;
        for(int i = inLb ; i <= inUb ; i++)
        {
            if(in[i].equals(pre[preLb]))
            {
                index = i;
                break;
            }
        }
        int n = index - inLb;

        
        root.left = reconstuctionPreIn(pre, preLb+1, preLb + n, in, inLb, index-1);
        root.right = reconstuctionPreIn(pre, preLb+n+1, preUb, in, index+1, inUb);

        return root;
    }

    public static <T> BinaryTree<T> reconstuctionPreIn(T pre[], T in[])
    {
        return reconstuctionPreIn(pre, 0, pre.length-1, in, 0, in.length-1);
    }

    public static <T> BinaryTree<T> reconstuctionPostIn(T post[], int postLb, int postUb, T in[], int inLb, int inUb)
    {
        if(postLb > postUb)
            return null;

        BinaryTree<T> root = new BinaryTree<T>(post[postUb]);
        int index = 0;
        for(int i = inLb ; i <= inUb ; i++)
        {
            if(in[i].equals(post[postUb]))
            {
                index = i;
                break;
            }
        }

        root.left = reconstuctionPostIn(post, postLb, index-1, in, inLb, index-1);
        root.right = reconstuctionPostIn(post, index, postUb-1, in, index+1, inUb);

        return root;
    }

    public static <T> BinaryTree<T> reconstuctionPostIn(T post[], T in[])
    {
        return reconstuctionPostIn(post, 0, post.length-1, in, 0, in.length-1);
    }
    
    public void print()
    {
        BinaryTreePrinter.printBothSided(this);
    }

    // private static int getHeight(BinaryTree<T> root)
    // {
    //    if(root == null)
    //     return 0;
    
    //    return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    // }

    public int getHeight()
    {
        int lht, rht;
        if(left == null)
            lht = 0;
        else
            lht = left.getHeight();

        if (right == null) 
            rht = 0;
        else
            rht = right.getHeight();

        return 1 + Math.max(lht, rht);
    }

    public int countNode()
    {
        int lht, rht;
        if(left == null)
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
    public boolean equals(Object other)
    {
        return other instanceof BinaryTree t && this.data.equals(t.data) && this.left.equals(t.left) && this.right.equals(t.right);
    }

    public static <T> int getLevel(BinaryTree<T> root, BinaryTree<T> node)
    {
        if(root == null)
            return -2;
        if(root == node)
            return 0;
        else
        {
            int lvl = getLevel(root.left, node);
            if(lvl == -2)
                lvl = getLevel(root.right, node);

            if(lvl < 0)
                return lvl;
            else
                return 1 + lvl;
        }
    }

    private static <T> BinaryTree<T> searchDataWithNode(BinaryTree<T> root, T data)
    {
        if(root == null)
            return null;

        else if(root.data.equals(data))
            return root;
        
        else
        {
            BinaryTree<T> t = searchDataWithNode(root.left, data);
            if(t == null)
                t = searchDataWithNode(root.right, data);

            return t;
        }
    }

    public BinaryTree<T> searchDataWithNode(T data)
    {
        return(searchDataWithNode(this, data));
    }
    
    public static void main(String[] args) {
        BinaryTree<Character> treeOne = createTreeOne();
        // System.out.println("Preorder :" + preOrderToString(treeOne));
        // System.out.println("Inorder  :" + inOrderToString(treeOne));
        // System.out.println("Postorder:" + postOrderToString(treeOne));

    //     String inorder = treeOne.inOrderToString();
    //     String preOrder = treeOne.preOrderToString();
    //     System.out.println(preOrder);
    //     System.out.println(inorder);
    //     BinaryTree<Character> tree = reconstuctionPreIn(preOrder, inorder);
    //     System.out.println(tree.postOrderToString());
    //     System.out.println(tree);

        Integer preOrder[] = {14, 4, 3, 9, 7, 5, 15, 18, 16, 17, 20};
        Integer inOrder[]  = {3, 4, 5, 7, 9, 14, 15, 16, 17, 18, 20};
        Integer postOrder[] ={3, 5, 7, 9, 4, 17, 16, 20, 18, 15, 14};

        // Integer preOrder[] = {250, 120, 530, 650, 830, 160, 260, 190, 370};
        // Integer inOrder[]  = {530, 650, 120, 250, 260, 160, 190, 830, 370};
        // Integer postOrder[] ={650, 530, 120, 260, 190, 160, 370, 830, 250};

        // Integer preOrder[] = {20, 10, 30};
        // Integer inOrder[]  = {10, 20, 30};

        BinaryTree<Integer> root = reconstuctionPreIn(preOrder, inOrder);

        // BinaryTree<Integer> root = reconstuctionPostIn(postOrder, inOrder);

        System.out.println(root.preOrderToString());
        System.out.println(root);
        System.out.println(root.postOrderToString());
        // System.out.println(root.postOrderToString());
        root.print();

        System.out.println("Height: " + root.getHeight());
        System.out.println("Nodes: " + root.countNode());

        BinaryTree<Integer> r = root.searchDataWithNode(17);
        if(r == null)
            System.out.println("Cant find");
        else
        {
            r.print(); 
            System.out.println("Level: " + getLevel(root, r));
        }
    }



    @Override
    public T getData() {
        return data;
    }



    @Override
    public PrintableBinTree<T> getLeft() {
        return left;
    }



    @Override
    public PrintableBinTree<T> getRight() {
        return right;
    }
    
}
