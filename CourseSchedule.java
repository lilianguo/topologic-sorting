/*
207. Course Schedule
There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

Example 1:

Input: 2, [[1,0]] 
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
Note:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.

*/
class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, Set<Integer>> graph = createGraph(numCourses, prerequisites);
        return topologicSort(numCourses, graph);
    }
    private Map<Integer, Set<Integer>> createGraph(int numCourses, int[][] prerequisites) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            graph.putIfAbsent(i, new HashSet<>());
        }
        for (int[] prerequisite : prerequisites) {
            int pre = prerequisite[1];
            int curr = prerequisite[0];
            graph.get(pre).add(curr);
        }
        return graph;
    }

    private boolean topologicSort(int numCourses, Map<Integer, Set<Integer>> graph) {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            indegree.putIfAbsent(i, 0);
        }
        for (int key : graph.keySet()) {
            for (int next : graph.get(key)) {
                indegree.put(next, indegree.get(next) + 1);
            }
        } 

        Queue<Integer> queue = new LinkedList<>();
        for (int key : indegree.keySet()){
            if (indegree.get(key) == 0) {
                queue.offer(key);
            }
        }

        if (queue.size() == numCourses) {
            return true;
        }

        List<Integer> courses = new ArrayList<>();
        while(!queue.isEmpty()){
            int curr = queue.poll();
            System.out.println("curr is " + curr);
            courses.add(curr);
            for (int next : graph.get(curr)) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }
        return courses.size() == numCourses;
    }
}