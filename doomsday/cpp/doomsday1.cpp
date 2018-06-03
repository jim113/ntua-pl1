#include <iostream>
#include <queue>
#include <fstream>
#include <vector>
#include <algorithm>
#include <climits>
#include <map>
#include <utility>
using namespace std;
#define MAXN 1000
#include <tuple>
char grid[MAXN+2][MAXN+2];
int depth[MAXN + 2][MAXN + 2];

typedef tuple<int,int> tl;
vector< tl > v;

vector < tl > Neighbors(tl k){
	vector < tl > neig;
	int a=get<0>(k);
	int b=get<1>(k);
	if (grid[a-1][b] != 'x') neig.push_back(tl(a-1,b));
	if (grid[a][b-1] != 'x') neig.push_back(tl(a,b-1));
	if (grid[a+1][b] != 'x') neig.push_back(tl(a+1,b));
	if (grid[a][b+1] != 'x') neig.push_back(tl(a,b+1));
	return neig;
}

int M;
int N;
//taken from Marios Papachristou
void printGrid() {
  for (int i = 1; i <= N; i++) {
    for (int j = 1; j <= M; j++) {
      cout << grid[i][j];
    }
    cout << endl;
  }
}



int solution(bool& safe){

	int time=0;
	int flag=1; int flag1=1;
	vector < tl > ne;
	while(flag==1 && flag1==1){
		// printGrid();
		flag1=0;
		for (int i=1; i<=N; i++){
			for (int j=1; j<=M; j++){
				tl k=tl(i,j);

				if((grid[i][j]=='+' || grid[i][j]=='-') && depth[i][j]==time){
					flag1=1;
					vector<tl> neig=Neighbors(k);
					for (tl kl:neig){
						int a=get<0>(kl);
						int b=get<1>(kl);
						if(grid[a][b]!='x' && grid[a][b]=='.'){
								grid[a][b]=grid[i][j];
								depth[a][b]=time+1;

						}
						else if (grid[a][b]==grid[i][j]) continue;
						else if ((grid[i][j]=='+' || grid[i][j]=='-') && (grid[a][b]=='+' || grid[a][b]=='-') && grid[a][b]!=grid[i][j]){
							ne.push_back(tl(a,b));
							flag=0;
						}
					}

				}

			}
		}
		time=time+1;
	}
	if (flag==0){

		for (unsigned int i=0; i<ne.size(); i++){
			int a=get<0>(ne[i]);
			int b=get<1>(ne[i]);
			grid[a][b]='*';
			safe = false;
		}
		// scout<<"kaboom"<<'\n';
	}

	return time;
}



int main(int argc, char **argv) {

	  ifstream myReadFile;
		bool safe = true;
	  myReadFile.open(argv[1]);
	  string output;
	  N = 0;
	  M = 0;
	  if (myReadFile.is_open()) {
	  while (!myReadFile.eof()) {

	     myReadFile >> output;
	     if (N == 0) M = (int) output.size();
	     N++;
	     for (int j = 1; j <= M; j++) {
	       grid[N][j] = output[j - 1];
	       depth[N][j] = 0;
	    }
	  }
	 }
	 N--;
	 myReadFile.close();



for (int i = 0; i <= M+1; i++) { grid[i][0] = 'x'; grid[i][M + 1] = 'x'; }
for (int i = 0; i <= N+1; i++) { grid[0][i] = 'x'; grid[N + 1][i] = 'x'; }

int time=solution(safe);
if(safe){
	cout<<"the world is saved"<<'\n';
} else cout<<time<<'\n';
printGrid();
}