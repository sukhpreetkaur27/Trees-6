import java.util.*;

// LC 987

/**
 * Intuition: Do as directed
 * <p>
 * Keep a (row, col) coordinate for every node, such that:
 * 1. Left node = (row + 1, col - 1)
 * 2. Right node = (row + 1, col + 1)
 * <p>
 * Maintain a HashMap < col, Map < row, List < Integer>>>
 * <p>
 * We need row mapping as we may have multiple nodes for a row on the same col.
 * AS per ques., we need to sort them by values.
 * <p>
 * NOTE: we perform bucket sort rather than maintaining a TreeMap to avoid the extra log n
 * <p>
 * NOTE: Both DFS and BFS works here
 * <p>
 * TC: O(n)
 * DFS SC: O(height) == O(n) for skewed tree
 * BFS SC: O(leaf nodes count)
 */
public class VerticalOrderTreeTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Tuple {
        int r;
        int c;
        TreeNode node;

        Tuple(int rr, int cc, TreeNode nodee) {
            r = rr;
            c = cc;
            node = nodee;
        }
    }

    public List<List<Integer>> verticalTraversal_bfs(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        int[] minCol = {0};
        int[] maxCol = {0};
        int[] maxRow = {0};
        Map<Integer, Map<Integer, List<Integer>>> map = new HashMap<>();
        Deque<Tuple> q = new ArrayDeque<>();
        q.offer(new Tuple(0, 0, root));

        while (!q.isEmpty()) {
            Tuple curr = q.poll();

            map.putIfAbsent(curr.c, new HashMap<>());
            Map<Integer, List<Integer>> rowCol = map.get(curr.c);
            rowCol.computeIfAbsent(curr.r, k -> new ArrayList<>()).add(curr.node.val);

            // max depth == max row reached
            maxRow[0] = Math.max(maxRow[0], curr.r);

            if (curr.node.left != null) {
                // minimum exists on the left
                minCol[0] = Math.min(minCol[0], curr.c);
                q.offer(new Tuple(curr.r + 1, curr.c - 1, curr.node.left));
            }
            if (curr.node.right != null) {
                // maximum exists on the right
                maxCol[0] = Math.max(maxCol[0], curr.c);
                q.offer(new Tuple(curr.r + 1, curr.c + 1, curr.node.right));
            }
        }
        fetchResult(minCol, maxCol, map, maxRow, res);
        return res;
    }

    private void fetchResult(int[] minCol, int[] maxCol, Map<Integer, Map<Integer, List<Integer>>> map,
                             int[] maxRow, List<List<Integer>> res) {
        for (int i = minCol[0]; i <= maxCol[0]; i++) {
            List<Integer> sub = new ArrayList<>();
            Map<Integer, List<Integer>> rowMap = map.get(i);
            for (int j = 0; j <= maxRow[0]; j++) {
                List<Integer> row = rowMap.get(j);
                if (row != null) {
                    // sort if multiple values in a row
                    Collections.sort(row);
                    sub.addAll(row);
                }
            }
            res.add(sub);
        }
    }

    public List<List<Integer>> verticalTraversal_dfs(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        int[] minCol = {0};
        int[] maxCol = {0};
        int[] maxRow = {0};
        Map<Integer, Map<Integer, List<Integer>>> map = new HashMap<>();
        dfs(new Tuple(0, 0, root), map, minCol, maxCol, maxRow);

        fetchResult(minCol, maxCol, map, maxRow, res);
        return res;
    }

    private void dfs(Tuple root, Map<Integer, Map<Integer, List<Integer>>> map, int[] minCol, int[] maxCol, int[] maxRow) {
        if(root.node == null) {
            return;
        }
        map.putIfAbsent(root.c, new HashMap<>());
        Map<Integer, List<Integer>> rowCol = map.get(root.c);
        rowCol.computeIfAbsent(root.r, k -> new ArrayList<>()).add(root.node.val);

        maxRow[0] = Math.max(maxRow[0], root.r);

        // minimum exists on the left
        minCol[0] = Math.min(minCol[0], root.c);
        dfs(new Tuple(root.r + 1, root.c - 1, root.node.left), map, minCol, maxCol, maxRow);

        // maximum exists on the right
        maxCol[0] = Math.max(maxCol[0], root.c);
        dfs(new Tuple(root.r + 1, root.c + 1, root.node.right), map, minCol, maxCol, maxRow);
    }
}
