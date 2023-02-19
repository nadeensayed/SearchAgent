package code;

import java.util.*;

public class searchProcedure {

    Collection<Node> queue;
    List<String> previous = new LinkedList<String>();

    public Collection<Node> expand(Node node, searchProblem problem) throws CloneNotSupportedException {
        String p = node.state.cgX + "," + node.state.cgY + "," + node.state.passengersCarried + ","
                + node.state.blackboxesTaken + "," + node.state.passengersSaved;

        if(previous.contains(p))
            return new LinkedList<Node>();
        previous.add(p);

        Collection<Node> nodes = new LinkedList<Node>();
        Collection<Actions> operators = problem.getOperators(node.getState());
        for (Actions operator : operators) {
            State next = problem.getNextState(node.getState(), operator);
            boolean ancestorFound = false;
            List<Node> ancestors = node.getPathFromRoot();
            for (Node ancestor : ancestors) {
                if (ancestor.repeated(next.cgX, next.cgY, next.passengersCarried, next.blackboxesTaken, next.passengersSaved)) {
                    ancestorFound = true;
                    break;
                }
            }
            if (!ancestorFound) {
                nodes.add(new Node(next, node, operator));
            }
        }
        return nodes;
    }

    public Node BF(searchProblem problem) throws CloneNotSupportedException {
        queue = new LinkedList<Node>();
        queue.addAll(expand(new Node(problem.getInitialState()), problem));
        boolean done = false;
        Node solution = null;
        while (!done) {
            if (queue.isEmpty()) {
                done = true;
            } else {
                Node node = ((LinkedList<Node>) queue).removeFirst();
                if (problem.isGoal(node.getState())) {
                    solution = node;
                    done = true;
                } else {
                    queue.addAll(expand(node, problem));
                }
            }
        }
        return solution;
    }

    public Node DF(searchProblem problem) throws CloneNotSupportedException {
        queue = new LinkedList<Node>();
        queue.addAll(expand(new Node(problem.getInitialState()), problem));
        boolean done = false;
        Node solution = null;
        while (!done) {
            if (queue.isEmpty()) {
                done = true;
            } else {
                Node node = ((LinkedList<Node>) queue).removeLast();
                if (problem.isGoal(node.getState())) {
                    solution = node;
                    done = true;
                } else {
                    queue.addAll(expand(node, problem));
                }
            }
        }
        return solution;
    }

    public Node depthLimited(searchProblem problem, int depthLimit) throws CloneNotSupportedException {
        queue = new LinkedList<Node>();
        queue.addAll(expand(new Node(problem.getInitialState()), problem));
        boolean done = false;
        Node solution = null;
        while (!done) {
            if (queue.isEmpty()) {
                done = true;
            } else {
                Node node = ((LinkedList<Node>) queue).removeLast();
                if (problem.isGoal(node.getState())) {
                    solution = node;
                    done = true;
                } else if (node.getDepth() < depthLimit) {
                    queue.addAll(expand(node, problem));
                }
            }
        }
        return solution;
    }

    public Node ID(searchProblem problem) throws CloneNotSupportedException {
        int depthLimit = 0;
        Node solution = null;
        while (solution == null) {
            previous = new LinkedList<String>();
            solution = depthLimited(problem, depthLimit);
            depthLimit++;
        }
        return solution;
    }

    public Node GR1(searchProblem problem) throws CloneNotSupportedException {
        queue = new PriorityQueue<Node>();
        queue.addAll(Node.setSearchStrategy(expand(new Node(problem.getInitialState()), problem), 1));
        boolean done = false;
        Node solution = null;
        while (!done) {
            if (queue.isEmpty()) {
                done = true;
            } else {
                Node node = ((PriorityQueue<Node>) queue).remove();
                if (problem.isGoal(node.getState())) {
                    solution = node;
                    done = true;
                } else {
                    queue.addAll(Node.setSearchStrategy(expand(node, problem), 1));
                }
            }
        }
        return solution;
    }

    public Node GR2(searchProblem problem) throws CloneNotSupportedException {
        queue = new PriorityQueue<Node>();
        queue.addAll(Node.setSearchStrategy(expand(new Node(problem.getInitialState()), problem), 2));
        boolean done = false;
        Node solution = null;
        while (!done) {
            if (queue.isEmpty()) {
                done = true;
            } else {
                Node node = ((PriorityQueue<Node>) queue).remove();
                if (problem.isGoal(node.getState())) {
                    solution = node;
                    done = true;
                } else {
                    queue.addAll(Node.setSearchStrategy(expand(node, problem), 2));
                }
            }
        }
        return solution;
    }

    public Node AS1(searchProblem problem) throws CloneNotSupportedException {
        queue = new PriorityQueue<Node>();
        queue.addAll(Node.setSearchStrategy(expand(new Node(problem.getInitialState()), problem), 3));
        boolean done = false;
        Node solution = null;
        while (!done) {
            if (queue.isEmpty()) {
                done = true;
            } else {
                Node node = ((PriorityQueue<Node>) queue).remove();
                if (problem.isGoal(node.getState())) {
                    solution = node;
                    done = true;
                } else {
                    queue.addAll(Node.setSearchStrategy(expand(node, problem), 2));
                }
            }
        }
        return solution;
    }

    public Node AS2(searchProblem problem) throws CloneNotSupportedException {
        queue = new PriorityQueue<Node>();
        queue.addAll(Node.setSearchStrategy(expand(new Node(problem.getInitialState()), problem), 4));
        boolean done = false;
        Node solution = null;
        while (!done) {
            if (queue.isEmpty()) {
                done = true;
            } else {
                Node node = ((PriorityQueue<Node>) queue).remove();
                if (problem.isGoal(node.getState())) {
                    solution = node;
                    done = true;
                } else {
                    queue.addAll(Node.setSearchStrategy(expand(node, problem), 4));
                }
            }
        }
        return solution;
    }

}
