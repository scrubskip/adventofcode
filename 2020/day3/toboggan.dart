import "dart:io";

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);
  List<String> inputStr = new File(argResults.rest[0]).readAsLinesSync();
  Forest forest = new Forest(inputStr);
  stdout.writeln(forest.getTreeCount(3, 1));
  stdout.writeln(forest.getMultiple([
    [1, 1],
    [3, 1],
    [5, 1],
    [7, 1],
    [1, 2]
  ]));
}

class Forest {
  List<String> _data;

  Forest(this._data);

  bool isTree(int x, int y) {
    String row = _data[y];
    return row[x % row.length] == '#';
  }

  int getTreeCount(int xStride, int yStride) {
    int currentX = 0;
    int treeCount = 0;
    for (int currentY = 0; currentY < _data.length; currentY += yStride) {
      if (isTree(currentX, currentY)) {
        treeCount += 1;
      }
      currentX += xStride;
    }
    return treeCount;
  }

  int getMultiple(List<List<int>> inputSlopes) {
    return inputSlopes
        .map((element) => getTreeCount(element[0], element[1]))
        .reduce((value, element) => value * element);
  }
}
