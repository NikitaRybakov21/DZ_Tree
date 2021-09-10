import java.util.Random;

public class Test {
    private static final Random random = new Random();
    private static final Tree[] tree = new Tree[20];

    public static void main(String[] args){

        for (int i = 0; i < 20; i++) {
            tree[i] = new TreeImpl<Integer>(4);
            for (int j = 0; j < 20; j++) {
                tree[i].add(random.nextInt(25));
            }
        }

        tree[0].traverse(Tree.TraversMode.IN_ORDER);
        System.out.println(tree[0].isBalanced(tree[0].getRoot()));
    }
}
