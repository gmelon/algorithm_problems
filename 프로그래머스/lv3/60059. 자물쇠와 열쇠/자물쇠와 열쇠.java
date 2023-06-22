class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        // key와 lock을 겹쳤을 때 모든 값이 1이 되면 됨
        // 이때, lock의 범위를 넘어서는 값들은 고려 X
        
        // 4방향에 대하여 모든 경우의 수를 확인
        // 1. key를 돌릴 수 있어야 함
        // 2. key와 lock이 겹치는 모든 경우의 수를 이동할 수 있어야 함
        
        // NxN lock을 -> M * 2 + (N - 2) 사이즈로 키움 (바깥은 -1로 패딩)
        // 기존 lock의 좌표를 기억해두고 해당 구역의 모든 좌표 값이 더했을 때 1이되는지 확인
        
        
        // 먼저 MxM을 M * 2 + (N - 2) 로 패딩
        int paddingSize = key.length * 2 + (lock.length - 2);
        int[][] paddingKey = new int[paddingSize][paddingSize];
        
        // 기존 M을 paddingM 좌측상단으로 복사
        for(int i = 0 ; i < key.length ; i++) {
            for(int j = 0 ; j < key.length ; j++) {
                paddingKey[i][j] = key[i][j];
            }
        }
      
        int[][] rotatedKey = paddingKey;
        for (int rotateCount = 0 ; rotateCount < 4 ; rotateCount++) {
            // 여기서 paddingKey을 90도씩 4번 돌림
            rotatedKey = rotateKey(key.length, rotatedKey);
            
            // 내부를 이동하며 lock에 대입 가능한지 확인
            // N + (M - 1) 번 이동 가능 (우/하 동일)
            int shiftSize = lock.length + key.length - 1;
            for (int startX = 0 ; startX < shiftSize ; startX++) {
                for (int startY = 0 ; startY < shiftSize ; startY++) {
                    int[][] shiftedKey = shiftKey(key.length, rotatedKey, startX, startY);

                    // 여기서 lock과의 비교 수행
                    boolean isOne = true;
                    for (int x = 0; x < lock.length ; x++) {
                        for (int y = 0 ; y < lock.length ; y++) {
                            int keyX = key.length - 1 + x;
                            int keyY = key.length - 1 + y;
                            
                            if (lock[x][y] + shiftedKey[keyX][keyY] != 1) {
                                isOne = false;
                            }
                        }
                        if (!isOne) {
                            // 하나의 좌표라도 합이 1이 아니면 해당 shift는 실패
                            break;
                        }
                    }
                    if (isOne) {
                        // 한번이라도 shiftedKey의 모든 좌표 합이 1이면 성공
                        return true;
                    }
                    // 아니면 다음 shiftedKey와 비교 진행
                }
            }
        }
        
        return false; // 여기 도달하면 불가능
    }
    
    // 배열 이동 메서드
    public int[][] shiftKey(int M, int[][] paddingKey, int startX, int startY) {
        // 매번 새로운 배열을 만들어서 반환
        int[][] shiftedKey = new int[paddingKey.length][paddingKey.length];
        
        for (int i = 0 ; i < M ; i++) {
            for (int j = 0 ; j < M ; j++) {
                // 이동 작업
                shiftedKey[i + startX][j + startY] = paddingKey[i][j];
            }
        }
        
        return shiftedKey;
    }
    
    // 배열 회전 메서드
    public int[][] rotateKey(int M, int[][] paddingKey) {
        int[][] rotatedKey = new int[paddingKey.length][paddingKey.length];
        
        // 90도로 돌리기 위해선 새로운x -> 기존y / 새로운y -> (max좌표값 - 기존x)
        int maxIndex = M - 1;
        for(int i = 0 ; i < M ; i++) {
            for(int j = 0 ; j < M ; j++) {
                rotatedKey[i][j] = paddingKey[maxIndex - j][i];
            }
        }
        
        return rotatedKey;
    }
    
}
