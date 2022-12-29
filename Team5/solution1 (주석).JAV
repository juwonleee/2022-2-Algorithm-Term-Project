package part;

import java.util.LinkedList;
import java.util.Queue;
import java.util.*;



class Solution {
	
	final static int row = 3;
	final static int col = 6;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner kb = new Scanner(System.in);
		
		
		String[] arr = new String[row];
		
		// input the table information
        // 유저로부터 테이블 상태 입력받기
		for (int i = 0; i < row; i++) {
			arr[i] = kb.nextLine();
		}
		
		System.out.println();		
		
		int ans;
		
        // solution function
		ans = solution(arr);
		
		System.out.print(ans + " ");
		
	}
	
	
	
    public static int solution(String[] places) {
        int answer;
        
        String[] p = places;

        // isOk 변수
        // 거리두기가 지켜지고 있는지 확인하기 위한 변수
        boolean isOk = true;

        // 모든 자리에서 bfs 실행
        // 사람이 앉아있는 자리 중, 한 자리라도 거리두기가 지켜지지 않으면 false 반환
        for (int r = 0; r < row && isOk; r++) {
            for (int c = 0; c < col && isOk; c++) {
                if (p[r].charAt(c) == 'P') {
                    if (!bfs(r, c, p))
                        isOk = false;
                }
            }
        }

        // 거리두기가 지켜지면 1, 지켜지지 않으면 0 반환
        answer = isOk ? 1 : 0;
        
        return answer;
    }

    // 각 자리에서 거리두기를 확인하기 위한 bfs 함수
    private static boolean bfs(int r, int c, String[] p) {
        int dr[] = { -1, 1, 0, 0 };
        int dc[] = { 0, 0, -1, 1 };

        // 거리두기 비교하기 위한 queue
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[] { r, c });

        while (!queue.isEmpty()) {
            int[] position = queue.poll();

            // i가 0일 경우 왼쪽으로 1칸
            // i가 1일 경우 오른쪽으로 1칸
            // i가 2일 경우 위로 1칸
            // i가 3일 경우 아래로 1칸
            // for loop를 통해 맨하튼거리 = 1인 자리 비교
            for (int i = 0; i < 4; i++) {
                int nr = position[0] + dr[i];
                int nc = position[1] + dc[i];

                // 가장자리인 경우 빈자리, 거리두기 확인할 필요 없음
                // 처음 시작점 P는 탐색 제외 (nr == r, nc == c)인 부분
                if (nr < 0 || nc < 0 || nr >= row || nc >= col || (nr == r && nc == c))
                    continue;
                
                // 각 자리로부터 맨하튼 거리 d
                int d = Math.abs(nr - r) + Math.abs(nc - c);

                // 파티션이 있는 경우 : 탐색 진행 X 더 이상 찾을 필요 없음
                // 맨하튼거리가 2 이하이면서 다른 사람이 있는 경우 (P), false 반환
                if (p[nr].charAt(nc) == 'P' && d <= 2)
                    return false;
                // 맨하튼 거리가 2 미만이면서 빈자리인 경우 (O) 다음 탐색
                // 다음 탐색을 할 경우 맨하튼 거리가 1 증가하므로 d < 2인 칸만 탐색
                else if (p[nr].charAt(nc) == 'O' && d < 2)
                    queue.offer(new int[] { nr, nc });
            }
        }

        return true;
    }
}