import java.util.Map;

class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, Set<Integer>> graph = createGrapth(prerequisites);
        return topologicSort(numCourses, graph);
    }
    private Map<Integer, Set<Integer>> createGrapth (int[][] prerequisites) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
            int pre = prerequisite[1];
            int curr = prerequisite[0];
            System.out.println("pre is :" + pre);
            System.out.println("curr is :" + curr);
            graph.putIfAbsent(pre, new HashSet<>());
            graph.get(pre).add(curr);
        }
        System.out.println("grapth size is :" + graph.size());
        return graph;
    }

    private Map<Integer, Integer> getIndegree(int numCourses, Map<Integer, Set<Integer>> graph) {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            indegree.put(i, 0);
        }
        for (int graphKey : graph.keySet()) {
            for (int next : graph.get(graphKey)) {
                indegree.put(next, indegree.get(next) + 1);
            }
        }
        return indegree;
    }
    private boolean topologicSort(int numCourses, Map<Integer, Set<Integer>> graph) {
        Map<Integer, Integer> indegree = getIndegree(numCourses, graph);
        Queue<Integer> queue = new LinkedList<>();
        for (int key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                System.out.println("head : " + key);
                queue.add(key);
            }
        }
        List<Integer> courses = new ArrayList<>();
        while(!queue.isEmpty()) {
            int curr = queue.poll();
            courses.add(curr);
            if (graph.get(curr) != null) {
                for (int next : graph.get(curr)) {
                    indegree.put(next, indegree.get(next) - 1);
                    if (indegree.get(next) == 0) {
                        queue.offer(next);
                    }
                }
            }
        }
        if (courses.size() == numCourses) {
            return true;
        }
        return false;
    }
}