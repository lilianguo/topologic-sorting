class CourseScheduleIII {
    /*
    Time complexity : O(nlog(n). At most n elements are added to the queue. 
    Adding each element is followed by heapification, which takes O(log(n) time.

    Space complexity : O(n). 
    The queuequeue containing the durations of the courses taken can have atmost n elements
    */

    public int scheduleCourse(int[][] courses) {
        if (courses == null || courses.length == 0) {
            return 0;
        }
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        int time = 0;
        for (int i = 0; i < courses.length; i++) {
            if (time + courses[i][0] <= courses[i][1]) {
                time += courses[i][0];
                queue.offer(courses[i][0]);
            }
            else if (!queue.isEmpty() && courses[i][0] < queue.peek()) {
                time += courses[i][0] - queue.poll();
                queue.offer(courses[i][0]);
            }
        }
        return queue.size();
    }
}