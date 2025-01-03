import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Perform any traversal.
 * <p>
 * TC: O(n)
 * SC: O(n)
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
    public String serialize_bfs(TreeNode root) {
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
    public TreeNode deserialize_bfs(String data) {
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

    public String serialize_pre(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString();
    }

    private void preOrder(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null");
            return;
        }
        sb.append(root.val);
        sb.append(",");
        preOrder(root.left, sb);
        sb.append(",");
        preOrder(root.right, sb);
    }

    private TreeNode preOrder(int[] index, String[] nodes) {
        String val = nodes[index[0]];
        if (val.equals("null")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(val));
        index[0]++;
        root.left = preOrder(index, nodes);
        index[0]++;
        root.right = preOrder(index, nodes);
        return root;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize_pre(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] nodes = data.split(",");
        return preOrder(new int[]{0}, nodes);
    }

    public String serialize_post(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        postOrder(root, sb);
        return sb.toString();
    }

    private void postOrder(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null");
            return;
        }


        postOrder(root.left, sb);
        sb.append(",");
        postOrder(root.right, sb);
        sb.append(",");
        sb.append(root.val);
    }

    private TreeNode postOrder(int[] index, String[] nodes) {
        String val = nodes[index[0]];
        if (val.equals("null")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(val));
        index[0]--;
        root.right = postOrder(index, nodes);
        index[0]--;
        root.left = postOrder(index, nodes);


        return root;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize_post(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] nodes = data.split(",");
        int n = nodes.length;
        return postOrder(new int[]{n - 1}, nodes);
    }
}
