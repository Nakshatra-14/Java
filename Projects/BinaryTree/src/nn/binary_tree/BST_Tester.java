package nn.binary_tree;

public class BST_Tester {
    public static void main(String[] args) {

        // int keys[] = {53, 28, 32, 80, 65, 70, 60, 16, 25, 20, 27};
        int keys[] = {53, 28};
        BST<Integer> root = null;

        for(int key : keys)
            // root = BST.addKey(root, key);
            // root = BST.addKeyRecursive(root, key);
            root = BST.showEveryInsertTree(root, key);
        // root.print();
        int value = 53;
        BST.removeKey(root, value);
        System.out.println("====================================");
        root.print();
    }
}
