
import java.util.Scanner;
import java.io.FileInputStream;

class Solution
{
	public static void main(String args[]) throws Exception
	{
	
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
	
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int n = sc.nextInt();
            
            int[][] board = new int[n][n];
            for(int i = 0 ; i < n ; i++) {
                for(int j = 0 ; j < n ; j++) {
                    board[i][j] = sc.nextInt();
                }
            }
            
            System.out.println("#" + test_case);
            
            for(int x = 0 ; x < n ; x++) {
                for(int y = 0 ; y < n ; y++) {
                    System.out.print(board[n-y - 1][x]);
                }
                System.out.print(" ");
                for(int y = 0 ; y < n ; y++) {
                    System.out.print(board[n - x - 1][n - y - 1]);
                }
                System.out.print(" ");
                for(int y = 0 ; y < n ; y++) {
                    System.out.print(board[y][n - x - 1]);
                }
                System.out.println();
            }
		}
	}
}