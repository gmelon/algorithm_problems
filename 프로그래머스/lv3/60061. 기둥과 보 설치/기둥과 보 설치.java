import java.util.*;
import java.util.stream.*;

class Solution {
    
    static class Position{
        int x, y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Position adjustedNewPosition(int dx, int dy) {
            return new Position(x + dx, y + dy);
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof Position)) {
                return false;
            }
            Position p = (Position) o;
            return this.x == p.x && this.y == p.y;
        }
    }
    
    public int[][] solution(int n, int[][] build_frame) {
        List<Position> columns = new ArrayList<>(); // 기둥(0)
        List<Position> rows = new ArrayList<>(); // 보(1)
        
        // 시뮬레이션 시작
        for (int[] build : build_frame){
            Position newPosition = new Position(build[0], build[1]); // 가로, 세로
            if (build[3] == 1) { // 설치
                if (build[2] == 0) {
                    // 기둥 설치 조건 검사
                    if(canBuildColumn(columns, rows, newPosition)) {
                        columns.add(newPosition);
                    }
                    
                } else {
                    // 보 설치 조건 검사
                    if (canBuildRow(columns, rows, newPosition)) {
                        rows.add(newPosition);
                    }
                }
            } else { // 삭제
                boolean canRemove = true;
                
                if (build[2] == 0) {
                    // 임시로 기둥 삭제
                    columns.remove(newPosition);
                    
                    // 기존 기둥, 보가 유지 가능한지 검사
                    for(Position column: columns) {
                        canRemove = canBuildColumn(columns, rows, column);
                        if (!canRemove) {
                            break;
                        }
                    }
                    if (canRemove) {
                        // 최적화를 위함
                        for(Position row: rows) {
                            canRemove = canBuildRow(columns, rows, row);
                            if (!canRemove) {
                                break;
                            }
                        }                        
                    }
                    
                    // 삭제 불가능하면 다시 추가
                    if (!canRemove) {
                        columns.add(newPosition);
                    }
                } else {
                    // 임시로 보 삭제
                    rows.remove(newPosition);
                    
                    // 기존 기둥, 보가 유지 가능한지 검사
                    for(Position column: columns) {
                        canRemove = canBuildColumn(columns, rows, column);
                        if (!canRemove) {
                            break;
                        }
                    }
                    if (canRemove) {
                        for(Position row: rows) {
                            canRemove = canBuildRow(columns, rows, row);
                            if (!canRemove) {
                                break;
                            }
                        }
                    }
                    
                    // 삭제 불가능하면 다시 추가
                    if (!canRemove) {
                        rows.add(newPosition);
                    }
                }
            }
        } // for-loop(시뮬레이션) 완료
        
        // 배열로 만들고 정렬해서 반환
        return Stream.concat(
            columns.stream().map(column -> new int[]{column.x, column.y, 0}),
            rows.stream().map(row -> new int[]{row.x, row.y, 1}))
            .sorted(Comparator.comparingInt((int[] arr) -> arr[0])
                   .thenComparingInt(arr -> arr[1])
                   .thenComparingInt(arr -> arr[2]))
            .toArray(int[][]::new);
    }
    
    public boolean canBuildColumn(List<Position> columns, List<Position> rows, Position newPosition) {
        // 주어진 좌표가 바닥인가?
        if (newPosition.y == 0) {
            return true;
        }

        // 주어진 좌표 "아래"에 기둥이 있는가?
        if (columns.contains(newPosition.adjustedNewPosition(0, -1))) {
            return true;
        }

        // 해당 좌표 || "좌측" 좌표에 보가 있는가?
        if (rows.contains(newPosition) ||
            rows.contains(newPosition.adjustedNewPosition(-1, 0))) {
            return true;
        }
        
        return false;
    }

    public boolean canBuildRow(List<Position> columns, List<Position> rows, Position newPosition) {
        // 주어진 좌표 아래 "나" 주어진 좌표 우측 아래에 기둥이 있는가?
        if (columns.contains(newPosition.adjustedNewPosition(0, -1)) ||
           columns.contains(newPosition.adjustedNewPosition(1, -1))) {
            return true;
        }
        
        // 현재 좌표의 좌측 좌표"와" 우측 좌표에 보가 있는가?
        if (rows.contains(newPosition.adjustedNewPosition(-1, 0)) &&
           rows.contains(newPosition.adjustedNewPosition(1, 0))) {
            return true;
        }
        
        return false;
    }
    
}