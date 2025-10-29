package org.bunuelolovers.sgmms.sgmms_proyect.graph;

import java.util.*;

public class Graph {

    private final Map<String, Vertex> vertices = new HashMap<>();

    public void addVertex(String id, double x, double y) {
        vertices.put(id, new Vertex(id, x, y));
    }

    public void connectVertices(String id1, String id2) {
        Vertex v1 = vertices.get(id1);
        Vertex v2 = vertices.get(id2);
        if (v1 != null && v2 != null) {
            v1.addNeighboor(v2);
            v2.addNeighboor(v1); // Si el grafo es no dirigido
        }
    }

    public Vertex getVertex(String id) {
        return vertices.get(id);
    }

    public List<Vertex> getRutaMasCorta(String origenId, String destinoId) {
        Vertex origen = vertices.get(origenId);
        Vertex destino = vertices.get(destinoId);
        Map<Vertex, Vertex> predecesor = new HashMap<>();
        Queue<Vertex> cola = new LinkedList<>();
        Set<Vertex> visitados = new HashSet<>();
        List<Vertex> ruta = new ArrayList<>();

        if (origen == null || destino == null) return ruta;

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            Vertex actual = cola.poll();
            if (actual.equals(destino)) break;
            for (Vertex vecino : actual.getneighboor()) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    predecesor.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        // Reconstruir la ruta
        Vertex paso = destino;
        while (paso != null && predecesor.containsKey(paso)) {
            ruta.add(0, paso);
            paso = predecesor.get(paso);
        }
        if (origen.equals(destino)) {
            ruta.add(origen);
        } else if (!ruta.isEmpty()) {
            ruta.add(0, origen);
        }
        return ruta;
    }

    public Collection<Vertex> getTodosLosVertices() {
        return vertices.values();
    }

}
