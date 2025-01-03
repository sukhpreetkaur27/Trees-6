import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Perform any traversal.
 * Used here Level Order Traversal.
 */
public class SerializeDeserializeBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * NOTE: we cannot add nulls to Q.
     * <p>
     * So, add to serialized string while checking children of the current polled node.
     * If null, add "null".
     * else, add to string as well as to the Q.
     * <p>
     * NOTE: for the root node, we need to add it to the serialized string and Q by default.
     * <p>
     * TC: O(n)
     * SC: O(n)
     *
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) {
            return sb.toString();
        }
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        sb.append(root.val);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                sb.append(",");
                TreeNode curr = q.poll();

                if (curr.left != null) {
                    sb.append(curr.left.val);
                    q.offer(curr.left);
                } else {
                    sb.append("null");
                }
                sb.append(",");
                if (curr.right != null) {
                    sb.append(curr.right.val);
                    q.offer(curr.right);
                } else {
                    sb.append("null");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Perform the reverse of serialization.
     * NOTE: we need to split the string based on ",".
     * By default, add to the Q root node.
     * For polled nodes, add its children if not null and also to the Q.
     * <p>
     * NOTE: String pointer for the serialized input keeps on incresing by default.
     * <p>
     * TC: O(n)
     * SC: O(n)
     *
     * @param data
     * @return
     */
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] nodes = data.split(",");
        int index = 0;
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode root = new TreeNode(Integer.parseInt(nodes[index]));
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode curr = q.poll();
            String left = nodes[++index];
            if (!left.equals("null")) {
                int leftVal = Integer.parseInt(left);
                curr.left = new TreeNode(leftVal);
                q.offer(curr.left);
            }
            String right = nodes[++index];
            if (!right.equals("null")) {
                int rightVal = Integer.parseInt(right);
                curr.right = new TreeNode(rightVal);
                q.offer(curr.right);
            }
        }
        return root;
    }
}
