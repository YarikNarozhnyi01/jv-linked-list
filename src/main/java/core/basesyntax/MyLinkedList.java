package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> current = head;
        for (int i = 0;i < index;i++) {
            current = current.next;
        }
        return current;
    }

    public Node<T> getNodeByValue(Object o) {
        if (o == null) {
            return null;
        }
        Node<T> current = head;
        while (current != null) {
            if (current.value != null && current.value.equals(o)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        if (head == null && index == 0) {
            head = newNode;
            tail = newNode;
        }
        if (index == 0 && head != null) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        if (index == size) {
            newNode.prev = tail;
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
        }
        if (index > 0 && index < size) {
            Node<T> current = getNodeByIndex(index);
            newNode.prev = current.prev;
            newNode.next = current;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> searchNode = getNodeByIndex(index);
        return searchNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> searchNode = getNodeByIndex(index);
        T oldValue = searchNode.value;
        searchNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid: " + index);
        }

        // Отримуємо елемент за індексом
        Node<T> nodeToRemove = getNodeByIndex(index);
        final T oldValue = nodeToRemove.value;

        // Оновлюємо зв'язки
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
        } else {
            // Якщо це перший елемент (head)
            head = nodeToRemove.next;
        }
        if (nodeToRemove.next != null) {
            nodeToRemove.next.prev = nodeToRemove.prev;
        } else {
            // Якщо це останній елемент (tail)
            tail = nodeToRemove.prev;
        }
        nodeToRemove.value = null;
        nodeToRemove.next = null;
        nodeToRemove.prev = null;

        size--; // Зменшуємо розмір списку
        return oldValue; // Повертаємо значення видаленого елемента
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = getNodeByValue(object);

        if (nodeToRemove == null) {
            return false; // Якщо елемент не знайдено
        }

        final T valueToRemove = nodeToRemove.value;

        // Оновлюємо зв'язки для видалення
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
        } else {
            // Якщо це перший елемент (head)
            head = nodeToRemove.next;
        }

        if (nodeToRemove.next != null) {
            nodeToRemove.next.prev = nodeToRemove.prev;
        } else {
            // Якщо це останній елемент (tail)
            tail = nodeToRemove.prev;
        }

        // Очищаємо зв'язки в самому видаленому елементі
        nodeToRemove.value = null;
        nodeToRemove.next = null;
        nodeToRemove.prev = null;

        size--; // Зменшуємо розмір списку
        return true; // Повертаємо true, якщо елемент був знайдений і видалений
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
