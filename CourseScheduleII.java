class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List[] edges = new ArrayList[numCourses];
        int[] degree = new int[numCourses];
        //下一个点的可能的点由arraylist保存
        for (int i = 0; i < numCourses; i++) {
            edges[i] = new ArrayList<>();
        }
        // degree保存每个点有多少个上一个点， 如果没有，这个点可以作为开头
        // edges保存每个点对应的下一个点都是什么
        for (int i = 0; i < prerequisites.length; i++) {
            degree[prerequisites[i][0]]++;
            edges[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        // queue中开始压入没有上一个点的点
        Queue<Integer> queue = new LinkedList<>();
        for (int i=  0; i < degree.length; i++) {
            if (degree[i] == 0) {
                queue.offer(i);
            }
        }
        int count = 0;
        List<Integer> res = new ArrayList<>();
        while(!queue.isEmpty()) {
            int course = queue.poll();
            res.add(course);
            count++;
            // 取出这个头部有几个下一个点
            int n = edges[course].size();
            for (int i = 0; i < n; i++) {
                int next = (int)edges[course].get(i);
                //拿出的这个点，它的 # of上一个点就会减少一个
                degree[next]--;
                // 如果前面没有电了，说明可以压入queue
                if (degree[next] == 0) {
                    queue.add(next);
                }
            }
        }
        if (count == numCourses) {
            int[] schedule = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                schedule[i] = res.get(i);
            }
            return schedule;
        } else {
            return new int[0];
        }
        // return count == numCourses ? res.toArray() : new int[0];
    }
}