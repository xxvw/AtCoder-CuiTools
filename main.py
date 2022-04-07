N = int(input())
S = [[0]*(2*N+1) for i in range(2*N+1)]
for i in range(1, 2*N):
  tmp = list(map(int, input().split()))
  for j in range(len(tmp)):
    S[i][j+(i+1)] = tmp[j]
    S[j+(i+1)][i] = tmp[j]
import sys
sys.setrecursionlimit(10**6)      
used = [0] * (2*N + 1)
used[0] = 1
ans = 0
score = 0
def find_first():
  for i,u in enumerate(used):
    if u == 0:
      return i
def dfs(A):
  global ans, score
  if len(A) == N:
    ans = max(ans, score)
    return
  first = find_first()
  nxts = []
  for nxt in range(first+1, 2*N+1):
    if not used[nxt]:
      nxts.append(nxt)
  for nxt in nxts:
    A.append([first, nxt])
    used[first] = 1
    used[nxt] = 1
    score ^= S[first][nxt]
    dfs(A)
    A.pop()
    used[first] = 0
    used[nxt] = 0
    score ^= S[first][nxt]
dfs([])
print(ans)