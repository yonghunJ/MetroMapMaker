/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;
import java.util.Stack;
/**
 *
 * @author jyh
 */
public class Graph {
    static int inf = 99999; // 무한대 값
    //int[][] weightMatrix;
    int [][] graph;
    int[] historyPath;
    int[] distance;
    boolean[] isVisits;
    int vCount;
    int nextVertex;
    int min;
    int [][]weightMatrix;
    int vertex;
    int result;
    int []resultpath;
    int resultStationNum;
//            = {
//                //  0    1    2    3    4    5    6
//        /* 0 */ {   1,   1, inf, inf,   1, inf, inf},
//        /* 1 */ { inf, inf, inf,   1, inf, inf, inf},
//        /* 2 */ {   1,   1,   0, inf, inf,   1, inf},
//        /* 3 */ { inf,   1,   2, inf,   1,   1,   1},
//        /* 4 */ {   1,   1, inf,   1,   1, inf,   1},
//        /* 5 */ {  1,  inf,   1,   1, inf,   1, inf},
//        /* 6 */ { inf, inf, inf,   1,   5, inf,   1}
//        };
    //int[][] weightMatrix;
    public Graph(int vertex){
        resultStationNum=0;
        resultpath = new int[vertex];
        weightMatrix = new int[vertex][vertex];
        this.vertex = vertex;
        for(int i=0;i<vertex;i++){
            for(int j=0;j<vertex;j++){
                weightMatrix[i][j] = inf;
            }
        }
    }
    public void setMatrix(int start, int end){
        weightMatrix[start][end] = 1;
    }
    public int[][] getMatrix(){
        return weightMatrix;
    }
    public void dikstra(int start, int end){
        vCount = weightMatrix[0].length; // 정점의 수
        isVisits = new boolean[vCount]; // 방문 배열
        distance = new int[vCount]; // 거리배열
        historyPath = new int[vCount]; // 히스토리 배열
        
        nextVertex = start; // distance 배열의 최소값의 정점
        min = 0; // distance 배열의 최소값
        // 초기화
        for (int i = 0; i < vCount; i++) {
            isVisits[i] = false; // 방문 한 곳 없다고 초기화
            distance[i] = inf; // 전부 다 무한대로 초기화
            historyPath[i] = inf; // 전부 다 무한대로 초기화
        }
        distance[start] = 0; // 시작점 0 으로 초기화
 
        // 다익스트라 실행
        while (true) {
            min = inf; // 최소값을 infinity 초기화
            for (int j = 0; j < vCount; j++) {
                if (isVisits[j] == false && distance[j] < min) { // 가장 먼저 방문했던
                                                                    // 노드는 제외한다,
                                                                    // 또한 최소값을
                                                                    // 찾기위한
                                                                    // 조사(선택정렬을
                                                                    // 생각하면 된다.)
                    nextVertex = j; // 다음으로 이동할 정점 선택
                    min = distance[j]; // 다음으로 이동한 최소값
                }
            }
            if (min == inf)
                break; // 최소값이 infinity이면 모든 정점을 지났다는 것, 최소값이 모든 정점을 지났으면
                        // infinity
            isVisits[nextVertex] = true; // 다음으로 이동할 정점 방문
 
            for (int j = 0; j < vCount; j++) {
                int distanceVertex = distance[nextVertex]
                        + weightMatrix[nextVertex][j]; // 정점에서 방문한 다른 정점의 거리
                if (distance[j] > distanceVertex) // 정점에서 다른 정점에서의 거리가 distance
                                                    // 배열보다 적다면 교체해 준다.
                {
                    distance[j] = distanceVertex; // 교체해 준다.
                    historyPath[j] = nextVertex; // 교체 된다면 그 지점의 정점을 기록을 남긴다.
                }
            }
        }
 
//        System.out.print(start + " 부터 " + "도착점 : " + end);
//        System.out.println(" 최단거리 : " + distance[end]); // 최종에는 start 정점부터 모든
        result= distance[end];                                                // 최단경로가 생성된다.
        printPath(historyPath, start, end);
    }
    
    public void printPath(int[] historyPath, int start, int end) {
        Stack<Integer> path = new Stack<Integer>();
        int vertex = end; // 거꾸로 탐색한다.
        while (true) {
            path.push(vertex);
            if (vertex == start)
                break; // 시작점이면 리턴한다.
//            if(vertex ==inf){
//                System.err.println("못찾겟따.");
//                break;
//            }
            vertex = historyPath[vertex]; // 탐색
        }
 
//        System.out.print(start + " 부터 " + end + " 까지 경로는 : ");
        resultStationNum=0;
        while (!path.isEmpty()){
            // 출력
            //System.out.print(" " + path.pop());
            
            resultpath[resultStationNum] = path.pop();
            resultStationNum++;
        }
    }
    public int[] resultPath(){
        return resultpath;
    }
    public int result(){
        return result;
    }
    public int getResultStationNum(){
        return resultStationNum;
    }
}
