import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();
        HashMap<Point, GridNode> openList = new HashMap<Point, GridNode>();
        Comparator<GridNode> compareGNs = Comparator.comparing(GridNode::getF).reversed().thenComparing(GridNode::getG).thenComparing(GridNode::getH).reversed();
        PriorityQueue<GridNode> nextBestPoint = new PriorityQueue<GridNode>(1000, compareGNs);
        HashMap<Point, Point> closedList = new HashMap<Point, Point>();


        GridNode currentNode = new GridNode(start,0, start.distanceSquared(end), null);
        openList.put(currentNode.getCurrent(), currentNode);

        while (!withinReach.test(currentNode.getCurrent(),end)) {
            List<Point> neighbors = potentialNeighbors.apply(currentNode.getCurrent()).filter(canPassThrough).collect(Collectors.toList());
            for (Point adj : neighbors) {
                if (!closedList.containsKey(adj) && !openList.containsKey(adj)) {
                    GridNode next = new GridNode(adj, currentNode.getG() + 1, adj.distanceSquared(end), currentNode.getCurrent());
                    openList.put(adj, next);
                    nextBestPoint.add(next);
                }
            }

            closedList.put(currentNode.getCurrent(), currentNode.getPrior());
            openList.remove(currentNode.getCurrent());
            currentNode = nextBestPoint.peek();
            try {
                nextBestPoint.remove();
            } catch (Exception e) {
                return path;
            }
        }
        path.add(0,currentNode.getCurrent());
        Point toAdd = currentNode.getPrior();
        while (toAdd != start){
            path.add(0,toAdd);
            toAdd = closedList.get(toAdd);
        }
        return path;
    }
}
