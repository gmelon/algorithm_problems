import java.util.*;

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
        
        // [설치 - 가능 조건]
        // 기둥 -> 주어진 좌표가 바닥 위인지?
        //        주어진 좌표의 아래에 기둥이 있거나, 해당 좌표 || 좌측 좌표에 보가 있는지?
        
        // 보   -> 주어진 좌표 아래에 기둥이 있는지?
        //        현재 좌표의 좌측 좌표와 우측 좌표에 보가 있는지?
        
        List<Position> columns = new ArrayList<>(); // 기둥(0)
        List<Position> rows = new ArrayList<>(); // 보(1)
        
        // 시뮬레이션 시작
        for (int[] build : build_frame){
            Position newPosition = new Position(build[0], build[1]); // 가로, 세로
            if (build[3] == 1) {
                // 설치
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
            } else {
                // 삭제
                List<Position> clonedColumns = new ArrayList<>(columns);
                List<Position> clonedRows = new ArrayList<>(rows);
                boolean canRemove = true;
                
                if (build[2] == 0) {
                    // 임시로 기둥 삭제
                    clonedColumns.remove(newPosition);
                    
                    // 기존 기둥, 보가 유지 가능한지 검사
                    for(Position clonedColumn: clonedColumns) {
                        canRemove = canBuildColumn(clonedColumns, clonedRows, clonedColumn);
                        if (!canRemove) {
                            break;
                        }
                    }
                    if (!canRemove) {
                        // 최적화를 위한 조기 종료
                        continue;
                    }
                    for(Position clonedRow: clonedRows) {
                        canRemove = canBuildRow(clonedColumns, clonedRows, clonedRow);
                        if (!canRemove) {
                            break;
                        }
                    }
                    // 실제 리스트에서 삭제
                    if (canRemove) {
                        columns.remove(newPosition);
                    }
                } else {
                    // 임시로 보 삭제
                    clonedRows.remove(newPosition);
                    
                    // 기존 기둥, 보가 유지 가능한지 검사
                    for(Position clonedColumn: clonedColumns) {
                        canRemove = canBuildColumn(clonedColumns, clonedRows, clonedColumn);
                        if (!canRemove) {
                            break;
                        }
                    }
                    if (!canRemove) {
                        // 최적화를 위한 조기 종료
                        continue;
                    }
                    for(Position clonedRow: clonedRows) {
                        canRemove = canBuildRow(clonedColumns, clonedRows, clonedRow);
                        if (!canRemove) {
                            break;
                        }
                    }
                    // 실제 삭제 가능 판단
                    if (canRemove) {
                        rows.remove(newPosition);
                    }
                }
            }
        }
        
        // 시뮬레이션 완료
        
        // 배열로 만들어서 반환
        int answerSize = columns.size() + rows.size();
        int[][] answer = new int[answerSize][3];
        
        int index = 0;
        for(Position column: columns) {
            answer[index][0] = column.x;
            answer[index][1] = column.y;
            answer[index][2] = 0; // 기둥
            index++;
        }
        for(Position row: rows) {
            answer[index][0] = row.x;
            answer[index][1] = row.y;
            answer[index][2] = 1; // 기둥
            index++;
        }
        
        // 정렬 후 반환
        Arrays.sort(answer, 
                    Comparator.comparingInt((int[] arr) -> arr[0])
                   .thenComparing((int[] arr) -> arr[1])
                   .thenComparing((int[] arr) -> arr[2]));
        
        return answer;        
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
        if (rows.contains(newPosition) || rows.contains(newPosition.adjustedNewPosition(-1, 0))) {
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