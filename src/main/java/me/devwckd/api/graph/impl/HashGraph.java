package me.devwckd.api.graph.impl;

import me.devwckd.api.graph.Graph;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author devwckd
 */
public class HashGraph<T> implements Graph<T> {

    private final HashMap<T, HashSet<T>> vertices = new HashMap<>();

    @Override
    public void addVertex(T vertex, T... edges) {
        final HashSet<T> edgeSet = getOrCreateEdgeSet(vertex);
        edgeSet.addAll(Arrays.asList(edges));
    }

    @Override
    public void addEdge(T vertex, T edge) {
        final HashSet<T> edgeSet = vertices.get(vertex);
        if(edgeSet == null) return;

        edgeSet.add(edge);
    }

    @Override
    public void removeVertex(T vertex) {
        vertices.remove(vertex);
    }

    @Override
    public void removeEdge(T vertex, T edge) {
        final HashSet<T> edgeSet = vertices.get(vertex);
        if(edgeSet == null) return;

        edgeSet.remove(edge);
    }

    @Override
    public Set<T> getVertices() {
        return vertices.keySet();
    }

    @Override
    public Set<T> getEdges(T vertex) {
        return vertices.get(vertex);
    }

    @Override
    public LinkedHashSet<T> depthFirstTraversal(T rootVertex) {
        if(!vertices.containsKey(rootVertex)) return null;

        final LinkedHashSet<T> visited = new LinkedHashSet<>();
        final Stack<T> stack = new Stack<T>() {{ push(rootVertex); }};

        while(!stack.isEmpty()) {
            final T actualVertex = stack.pop();

            if(visited.contains(actualVertex)) continue;
            visited.add(actualVertex);

            final Set<T> edgeSet = getEdges(rootVertex);
            if(edgeSet == null) continue;

            edgeSet.forEach(stack::push);
        }

        return visited;
    }

    @Override
    public LinkedHashSet<T> breadthFirstTraversal(T rootVertex) {
        if(!vertices.containsKey(rootVertex)) return null;

        final LinkedHashSet<T> visited = new LinkedHashSet<T>() {{add(rootVertex);}};
        final Queue<T> queue = new LinkedBlockingQueue<T>() {{add(rootVertex);}};

        while(!queue.isEmpty()) {
            final T actualVertex = queue.poll();

            final Set<T> edgeSet = getEdges(actualVertex);
            if(edgeSet == null) continue;

            for (T edge : edgeSet) {
                if(visited.contains(edge)) continue;

                visited.add(edge);
                queue.add(edge);
            }
        }

        return visited;
    }

    private HashSet<T> getOrCreateEdgeSet(T vertex) {
        if(vertices.containsKey(vertex)) return vertices.get(vertex);

        final HashSet<T> edgeSet = new HashSet<>();
        vertices.put(vertex, edgeSet);

        return edgeSet;
    }
}
