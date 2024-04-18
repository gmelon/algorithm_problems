import java.util.*;

class Solution {
    
    static class Position {
        int x, y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            
            Position p = (Position) o;
            return this.x == p.x && this.y == p.y;
            
        }
        
        @Override
        public int hashCode() {
            return x * 31 + y;
        }
    }
    
    static class Node {
        Set<Position> positions = new HashSet<>();
        String value;
        Node(int x, int y, String value) {
            positions.add(new Position(x, y));
            this.value = value;
        }
    }
        
    public String[] solution(String[] commands) {
        // 표 초기화
        Node[][] table = new Node[51][51];
        for(int i = 1 ; i <= 50 ; i++) {
            for(int j = 1; j <= 50 ; j++) {
                table[i][j] = new Node(i, j, null);
            }
        }
        
        // 출력
        List<String> answers = new ArrayList<>();
        
        for(String command : commands) {
            String[] args = command.split(" ");
            switch (args[0]) {
                case "UPDATE": {
                    if (args.length == 4) {
                        // 특정 위치의 셀 값을 변경
                        int x = Integer.parseInt(args[1]);
                        int y = Integer.parseInt(args[2]);
                        String value = args[3];
                        
                        // x, y 좌표와 연관된 애들의 값을 모두 변경
                        for(Position pos : table[x][y].positions) {
                            table[pos.x][pos.y].value = value;
                        }
                    } else {
                        // 특정 값을 갖는 셀들의 값을 변경
                        String prevValue = args[1];
                        String newValue = args[2];
                        
                        for(int i = 1 ; i <= 50 ; i++) {
                            for(int j = 1; j <= 50 ; j++) {
                                if (table[i][j].value != null &&
                                   table[i][j].value.equals(prevValue)) {
                                    table[i][j].value = newValue;
                                }
                            }
                        }
                    }
                    break;
                }
                case "MERGE" : {
                    int x1 = Integer.parseInt(args[1]);
                    int y1 = Integer.parseInt(args[2]);
                    int x2 = Integer.parseInt(args[3]);
                    int y2 = Integer.parseInt(args[4]);
                    
                    if (x1 == x2 && y1 == y2) {
                        continue;
                    }
                    
                    Node first = table[x1][y1];
                    Node second = table[x2][y2];
                        
                    String newValue = null;
                    // 값 결정
                    if (first.value == null || second.value == null) {
                        newValue = first.value;
                        if (newValue == null) {
                            newValue = second.value;
                        }
                    } else {
                        newValue = first.value;
                    }
                    
                    // 병합
                    Set<Position> newPositions = first.positions;
                    newPositions.addAll(second.positions);
                    
                    // 병합된 모든 셀에 새로운 값 넣기
                    for(Position pos : newPositions) {
                        table[pos.x][pos.y].positions = new HashSet<>(newPositions);
                        table[pos.x][pos.y].value = newValue;
                    }
                    
                    break;
                }
                case "UNMERGE" : {
                    int x = Integer.parseInt(args[1]);
                    int y = Integer.parseInt(args[2]);
                    
                    String newValue = table[x][y].value;
                    Set<Position> targets = table[x][y].positions;
                    
                    table[x][y].positions = new HashSet<>();
                    
                    // targets를 돌면서 병합 해제
                    for(Position target : targets) {
                        table[target.x][target.y].value = null;
                        table[target.x][target.y].positions.clear();
                        table[target.x][target.y].positions.add(new Position(target.x, target.y));
                    }
                    
                    // 최초 지정 지점에 기존 값 추가
                    table[x][y].value = newValue;
                    
                    break;
                }
                case "PRINT" : {
                    int x = Integer.parseInt(args[1]);
                    int y = Integer.parseInt(args[2]);
                    
                    String value = table[x][y].value;
                    
                    if (value == null) {
                        answers.add("EMPTY");
                    } else {
                        answers.add(value);    
                    }
                    
                    break;
                }
            }
        }
        
        String[] arrAnswers = new String[answers.size()];
        for(int i = 0 ; i < arrAnswers.length ; i++) {
            arrAnswers[i] = answers.get(i);
        }
        
        return arrAnswers;
    }
}