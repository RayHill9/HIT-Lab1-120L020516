package P3;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 图
 *
 * @author Mona
 */
public class FriendshipGraph {
    //顶点数组
    private final Person[] person;
    private int current_size;
    public int[][] arr;   //邻接矩阵--二维数组
    //当前遍历的下标

    Map<Object, Object> map = new HashMap<>();

    public FriendshipGraph(int size) {
        person = new Person[size];
        arr = new int[size][size];
    }

    /**
     * 向图中加入一个顶点
     */
    public void addVertex(Person v) {
        if(map.get(v.getName())!=null){
            System.out.println("有重复的名字");
            System.exit(0);
        }
        person[current_size++] = v;
        map.put(v.getName(),1);
    }

    public Person[] getPersons(){
        return person;
    }

    public void addEdge(String name1, String name2) {
        //找出两个顶点的下标
        int index1 = 0;
        for (int i = 0; i < person.length; i++) {
            if (person[i].getName().equals(name1)) {
                index1 = i;
                break;
            }
        }

        int index2 = 0;
        for (int i = 0; i < person.length; i++) {
            if (person[i].getName().equals(name2)) {
                index2 = i;
                break;
            }
        }

        arr[index1][index2] = 1;   //1代表连通
        arr[index2][index1] = 1;
    }

    /*
     * 	深度优先搜索算法遍历图
     *
     */
    public int getDistance(Person person1, Person person2) {
        Queue<Integer> queue = new LinkedList<>();
        int distance = 0;
        int per1 = 0;
        int per2 = 0;
        for (int i = 0; i < person.length; i++) {
            if (person[i].getName().equals(person1.getName())) {
                per1 = i;
            }
            if (person[i].getName().equals(person2.getName())) {
                per2 = i;
            }
        }
        if (per1 == per2) {
            return distance;
        } else {
            int per = per1;
            distance++;
            do {
                for (int i = 0; i < person.length; i++) {
                    if (arr[per][i] == 1) {
                        if (i == per2) {
                            return distance;
                        }
                        queue.add(i);
                        arr[per][i] = 0;
                        arr[i][per] = 0;
                        distance++;
                    }
                    if (!queue.isEmpty()) {
                        per = queue.poll();
                    }
                }

            } while (!queue.isEmpty());
        }
        return -1;
    }

    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph(4);
        Person rachel = new Person("rachel");
        Person ross = new Person("ross");
        Person ben = new Person("ben");
        Person kramer = new Person("kramer");


        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge("rachel", "ross");
        graph.addEdge("ross", "rachel");
        graph.addEdge("ross", "ben");
        graph.addEdge("ben", "ross");
        System.out.println(graph.getDistance(rachel, ross));
//should print 1
        System.out.println(graph.getDistance(rachel, ben));
//should print 2
        System.out.println(graph.getDistance(rachel, rachel));
//should print 0
        System.out.println(graph.getDistance(rachel, kramer));
//should print ‐1
    }

}