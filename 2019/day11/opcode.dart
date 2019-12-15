import 'dart:collection';
import "dart:io";
import "package:image/image.dart";

import "package:args/args.dart";

import '../day8/image.dart';

ArgResults _argResults;

final int COLOR_BLACK = Color.fromRgb(0, 0, 0);
final int COLOR_WHITE = Color.fromRgb(255, 255, 255);

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  String input = await new File(_argResults.rest[0]).readAsString();

  Hull hull = runHull(input.split(",").map(int.parse).toList());
  outputHullImage(hull, _argResults.rest[1]);
}

Hull runHull(List<int> input) {
  Hull hull = new Hull();
  hull.setPaint(Point(0, 0), 1); // part 2
  run(input, () => hull.getCurrentColor(),
      (output) => hull.handleOutput(output));

  return hull;
}

void outputHullImage(Hull hull, String fileOut) {
  File file = new File(fileOut);
  int width = (hull.maxX - hull.minX).abs();
  int height = (hull.maxY - hull.minY).abs();
  Image image = new Image(width + 1, height + 1);
  for (int x = hull.minX; x <= hull.maxX; x++) {
    for (int y = hull.minY; y <= hull.maxY; y++) {
      image.setPixel(x - hull.minX, y - hull.minY,
          hull.getColor(Point(x, y)) == 0 ? COLOR_BLACK : COLOR_WHITE);
    }
  }
  PngEncoder encoder = new PngEncoder();
  file.writeAsBytesSync(encoder.encodeImage(image));
}

List<int> run(List<int> inputCode,
    [int Function() onInput, Function(int) onOutput]) {
  // stdout.writeln("Running $inputValue");
  int programCounter = 0;
  int relativeBase = 0;
  List<int> data = inputCode.toList();
  data.addAll(List<int>.generate(1000, (index) => 0));

  while (true) {
    int rawOpcode = data[programCounter];

    // Parse the opcode
    int opcode = rawOpcode % 100;
    int param1Mode = (rawOpcode / 100).floor() % 10;
    int param2Mode = (rawOpcode / 1000).floor() % 10;
    int param3Mode = (rawOpcode / 10000).floor() % 10;

    if (opcode == 99) {
      break;
    }
    int param1Index =
        getParamIndex(param1Mode, programCounter + 1, data, relativeBase);
    int outputPos;
    int output;
    int increment = 4;
    if (opcode == 4) {
      increment = 2;
      if (onOutput != null) {
        onOutput(data[param1Index]);
      }
    } else if (opcode == 3) {
      increment = 2;
      outputPos = param1Index;
      if (onInput != null) {
        output = onInput();
        data[outputPos] = output;
      } else {
        throw new StateError("Asked for input but no input given.");
      }
    } else if (opcode == 9) {
      increment = 2;
      relativeBase += data[param1Index];
    } else {
      int param2Index =
          getParamIndex(param2Mode, programCounter + 2, data, relativeBase);
      int param3Index =
          getParamIndex(param3Mode, programCounter + 3, data, relativeBase);
      if (opcode == 1) {
        output = data[param1Index] + data[param2Index];
      } else if (opcode == 2) {
        output = data[param1Index] * data[param2Index];
      } else if (opcode == 5 || opcode == 6) {
        increment = 3;
        if ((opcode == 5 && data[param1Index] != 0) ||
            (opcode == 6 && data[param1Index] == 0)) {
          increment = 0;
          programCounter = data[param2Index];
        }
      } else if (opcode == 7 || opcode == 8) {
        if ((opcode == 7 && data[param1Index] < data[param2Index]) ||
            (opcode == 8 && data[param1Index] == data[param2Index])) {
          output = 1;
        } else {
          output = 0;
        }
      }
      if (output != null) {
        if (outputPos == null) {
          // Might have been set already, don't reset it.
          outputPos = param3Index;
        }
        data[outputPos] = output;
      }
    }

    programCounter += increment;
  }
  return data;
}

int getParamIndex(int mode, int startIndex, List<int> data, int relativeBase) {
  switch (mode) {
    case 0:
      return data[startIndex];
    case 1:
      return startIndex;
    case 2:
      return relativeBase + data[startIndex];
    default:
      throw new ArgumentError("Unknown mode $mode");
  }
}

class Hull {
  Map<Point, int> paint = new Map<Point, int>();
  Point currentPosition = new Point(0, 0);
  Direction currentDirection = Direction.NORTH;
  int paintCount = 0;
  bool handleColor = true;
  int minX = 0;
  int maxX = 0;
  int minY = 0;
  int maxY = 0;

  int getCurrentColor() {
    return getColor(currentPosition);
  }

  int getColor(Point point) {
    if (paint.containsKey(point)) {
      return paint[point];
    }
    // Everything starts out black.
    return 0;
  }

  void setPaint(Point point, int color) {
    if (!paint.containsKey(point)) {
      paintCount++;
    }
    // Create a copy so this doesn't get munged.
    paint[Point.fromPoint(point)] = color;
  }

  void handleOutput(int output) {
    if (handleColor) {
      setPaint(currentPosition, output);
    } else {
      turn(output);
      moveForward();
    }
    handleColor = !handleColor;
  }

  void turn(int direction) {
    int shift = direction == 0 ? -1 : 1;
    currentDirection = Direction
        .values[(currentDirection.index + shift) % Direction.values.length];
  }

  void moveForward() {
    switch (currentDirection) {
      case Direction.NORTH:
        currentPosition.y--;
        break;
      case Direction.SOUTH:
        currentPosition.y++;
        break;
      case Direction.EAST:
        currentPosition.x++;
        break;
      case Direction.WEST:
        currentPosition.x--;
        break;
    }
    if (currentPosition.y < minY) {
      minY = currentPosition.y;
    }
    if (currentPosition.y > maxY) {
      maxY = currentPosition.y;
    }
    if (currentPosition.x < minX) {
      minX = currentPosition.x;
    }
    if (currentPosition.x > maxX) {
      maxX = currentPosition.x;
    }
  }
}

enum Direction { NORTH, EAST, SOUTH, WEST }

class Point {
  int x, y;

  Point(this.x, this.y);

  static Point fromPoint(Point other) {
    return Point(other.x, other.y);
  }

  @override
  toString() {
    return "[${this.x}, ${this.y}]";
  }

  bool operator ==(o) => o is Point && o.x == x && o.y == y;
  int get hashCode => x.hashCode ^ y.hashCode;
}
