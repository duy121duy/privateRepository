package BaiTap1.Bai5;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Maze {
    public static void main(String[] args) {
        //
        JFrame frame = new JFrame();
        frame.setSize(650, 470);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MazePanel mp = new MazePanel();
        frame.add(mp);
        frame.setVisible(true);
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point poin = (Point) o;
            return x == poin.x && y == poin.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static class MazePanel extends JPanel {
        private static final long serialVersionUID = -566807999447681130L;
        private int[][] maze = { // khởi tạo ma trận mảng 2 chiều
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 2, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        private int sizeh, sizew, start, end;

        public MazePanel() {
            // Kích thước ma trận
            sizeh = 21;
            sizew = 31;
            start = 10;
            end = 15;
            solve();
            repaint(); // vẽ ma trận và lời giải
        }

        /**
         * Những point kề với point đang xét
         *
         * @param startPoint   vị trí hiện tại
         * @param visitedPoint point đã đi qua
         * @return các point kề với startPoint
         */
        List<Point> adjacentPoint(Point startPoint, Set<Point> visitedPoint) {
            List<Point> nextNode = new LinkedList<>();
            Point point;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    point = new Point(startPoint.x + i, startPoint.y + j);
                    if (startPoint.y + j >= 0 && startPoint.x + i == 10 && startPoint.y + j == 15 &&
                            i + j != 0 && i * j == 0) {
                        nextNode.clear();
                        nextNode.add(new Point(10, 15));
                        return nextNode;
                    }
                    if (startPoint.y + j >= 0 && maze[startPoint.x + i][startPoint.y + j] == 0 &&
                            i + j != 0 && i * j == 0 && !visitedPoint.contains(new Point(startPoint.x + i, startPoint.y + j))) {
                        nextNode.add(point);
                    }
                }
            }
            return nextNode;
        }

        /**
         * Đường đi từ vị trí bắt đầu đến vị trí kết thúc.
         *
         * @param adjacentPoint các vị trí liền kề
         * @param startPoint    vị trí bắt đầu
         * @param endPoint      vị trí kết thúc
         * @return đường đi
         */
        public List<Point> getPath(Map<Point, Point> adjacentPoint, Point startPoint, Point endPoint) {
            List<Point> path = new ArrayList<>();
            while (startPoint != endPoint) {
                path.add(endPoint);
                endPoint = adjacentPoint.get(endPoint);
            }
            return path;
        }

        /**
         * Implement một phương pháp tìm đường nào đó.
         * <p>
         * Yêu cầu : Vẽ đường đi từ điểm bắt đầu đến điểm kết thúc. Không hiện
         * các phần thừa - là các phần đường cụt hoặc đường đi bị sai. Chỉ vẽ
         * tuyến đường chính đi từ điểm đầu (màu vàng) đến màu đỏ.
         * <p>
         * Đường đi từ điểm đầu đến điểm cuối được tô màu xanh dương, để tô màu
         * xanh dương hãy set giá trị của điểm trên ma trận sang một số > 2
         */

        public void solve() {
            // Hàm này chứa phương pháp tìm đường từ điểm start đến vị
            // trí màu đỏ trên ma trận
            Maze x = new Maze();
            Point startPosition = new Point(10, 0);
            Point endPosition = new Point(10, 15);
            Point state;
            Set<Point> visitedPoints = new LinkedHashSet<>();

            Stack<Point> stack = new Stack<>();
            stack.add(startPosition);

            Map<Point, Point> path = new HashMap<>();
            while (!stack.isEmpty()) {
                state = stack.pop();
                if (state.equals(endPosition)) {
                    break;
                }
                visitedPoints.add(state);
                for (Point nextState : adjacentPoint(state, visitedPoints)) {
                    if (!visitedPoints.contains(nextState)) {
                        stack.add(nextState);
                        path.put(nextState, state);
                    }
                }

            }

            for (Point i : getPath(path, startPosition, endPosition)) {
                if (visitedPoints.contains(i)) {
                    maze[i.x][i.y] = 2;
                }
            }
        }

        public void paintComponent(Graphics g) // vẽ ma trận + lời giải
        {
            super.paintComponent(g);
            for (int i = 0; i < sizeh; i++)
                for (int j = 0; j < sizew; j++) {
                    if (i == start && j == end)
                        g.setColor(Color.yellow);
                    else if (maze[i][j] == 0)
                        g.setColor(Color.white);
                    else if (maze[i][j] == 1)
                        g.setColor(Color.black);
                    else if (maze[i][j] == 2)
                        g.setColor(Color.red);
                    else if (maze[i][j] == 4)
                        g.setColor(Color.cyan);
                    else
                        g.setColor(Color.blue); // blue là màu của lời giải
                    g.fillRect(j * 20, i * 20, 20, 20);
                }
        }
    }
}
