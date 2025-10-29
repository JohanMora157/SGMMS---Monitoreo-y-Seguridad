package org.bunuelolovers.sgmms.sgmms_proyect.structures;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {

    private class Node {
        T value;
        Node left, right;

        Node(T value) {
            this.value = value;
        }
    }

    private Node root;
    private int size = 0;

    // Insertar un elemento
    public void insert(T value) {
        root = insert(root, value);
    }

    private Node insert(Node node, T value) {
        if (node == null) {
            size++;
            return new Node(value);
        }
        int cmp = value.compareTo(node.value);
        if (cmp < 0) node.left = insert(node.left, value);
        else if (cmp > 0) node.right = insert(node.right, value);
        // Si es igual, no inserta duplicados
        return node;
    }

    // Buscar un elemento
    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node node, T value) {
        if (node == null) return false;
        int cmp = value.compareTo(node.value);
        if (cmp < 0) return contains(node.left, value);
        else if (cmp > 0) return contains(node.right, value);
        else return true;
    }

    // Eliminar un elemento
    public void remove(T value) {
        root = remove(root, value);
    }

    private Node remove(Node node, T value) {
        if (node == null) return null;
        int cmp = value.compareTo(node.value);
        if (cmp < 0) node.left = remove(node.left, value);
        else if (cmp > 0) node.right = remove(node.right, value);
        else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = getMin(node.right);
            node.value = min.value;
            node.right = remove(node.right, min.value);
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Recorrido inorden
    public List<T> inOrder() {
        List<T> res = new ArrayList<>();
        inOrder(root, res);
        return res;
    }

    private void inOrder(Node node, List<T> res) {
        if (node != null) {
            inOrder(node.left, res);
            res.add(node.value);
            inOrder(node.right, res);
        }
    }

    // Recorrido preorden
    public List<T> preOrder() {
        List<T> res = new ArrayList<>();
        preOrder(root, res);
        return res;
    }

    private void preOrder(Node node, List<T> res) {
        if (node != null) {
            res.add(node.value);
            preOrder(node.left, res);
            preOrder(node.right, res);
        }
    }

    // Recorrido postorden
    public List<T> postOrder() {
        List<T> res = new ArrayList<>();
        postOrder(root, res);
        return res;
    }

    private void postOrder(Node node, List<T> res) {
        if (node != null) {
            postOrder(node.left, res);
            postOrder(node.right, res);
            res.add(node.value);
        }
    }

    // Tamaño del árbol
    public int size() {
        return size;
    }

    // Verificar si está vacío
    public boolean isEmpty() {
        return size == 0;
    }

    // Limpiar el árbol
    public void clear() {
        root = null;
        size = 0;
    }
}