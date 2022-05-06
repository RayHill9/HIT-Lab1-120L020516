package P3.test;

import P3.FriendshipGraph;
import P3.Person;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FriendshipGraphTest {

    @Test
    public void test_addVertex() {
        FriendshipGraph G = new FriendshipGraph(3);
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person[] persons;
        G.addVertex(a);
        G.addVertex(b);
        G.addVertex(c);
        persons = G.getPersons();
        assertEquals(a, persons[0]);
        assertEquals(b, persons[1]);
        assertEquals(c, persons[2]);

    }

    @Test
    public void test_addEdge() {
        FriendshipGraph G = new FriendshipGraph(3);
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        G.addVertex(a);
        G.addVertex(b);
        G.addVertex(c);
        G.addEdge(a.getName(), b.getName());
        G.addEdge(b.getName(), a.getName());
        assertEquals(1, G.arr[0][1]);
        assertEquals(1, G.arr[1][0]);
    }

    @Test
    public void test_getDistance() {
        FriendshipGraph G = new FriendshipGraph(3);
        Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        G.addVertex(a);
        G.addVertex(b);
        G.addVertex(c);
        G.addEdge(a.getName(), b.getName());
        G.addEdge(b.getName(), a.getName());
        assertEquals(G.getDistance(a, b), 1);
        assertEquals(G.getDistance(b, a), 1);
        assertEquals(G.getDistance(a, a), 0);
        assertEquals(G.getDistance(b, b), 0);
        assertEquals(G.getDistance(c, c), 0);
        assertEquals(G.getDistance(a, c), -1);
        assertEquals(G.getDistance(c, a), -1);
        assertEquals(G.getDistance(b, c), -1);
        assertEquals(G.getDistance(c, b), -1);
    }
}
