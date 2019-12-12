import 'dart:collection';
import "dart:io";
import 'dart:math' as math;

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  List<String> input = await new File(_argResults.rest[0]).readAsLines();
  List<Point> points = parsePoints(input);
  Point point = findGreatestPoint(points);
  int x = point.x;
  int y = point.y;
  stdout.writeln("$x $y");
}

List<Point> parsePoints(List<String> input) {
  List<Point> returnList = [];
  int y = 0;
  for (String line in input) {
    for (int index = 0; index < line.length; index++) {
      if (line[index] == '#') {
        returnList.add(new Point(index, y));
      }
    }
    y++;
  }
  return returnList;
}

Point findGreatestPoint(List<Point> points) {
  int greatestCount = 0;
  Point greatestPoint = null;
  for (Point basePoint in points) {
    Set<double> angles = new Set<double>();
    points.forEach(
        (searchPoint) => {angles.add(basePoint.getAngle(searchPoint))});
    if (angles.length > greatestCount) {
      greatestCount = angles.length;
      greatestPoint = basePoint;
    }
  }
  stdout.writeln(greatestCount);
  return greatestPoint;
}

List<Point> getVaporizationOrder(List<Point> points, Point basePoint) {
  SplayTreeMap<double, List<Point>> pointsByAngle =
      getPointsByAngle(points, basePoint);
  List<Point> returnPoints = [];
  double angle = pointsByAngle.firstKey();
  while (angle != null) {
    List<Point> anglePoints = pointsByAngle[angle];
    // Pop the first one off.
    returnPoints.add(anglePoints.removeAt(0));
    if (anglePoints.isEmpty) {
      pointsByAngle.remove(angle);
    }
    angle = pointsByAngle.firstKeyAfter(angle);
    if (angle == null) {
      angle = pointsByAngle.firstKey();
    }
  }
  return returnPoints;
}

SplayTreeMap<double, List<Point>> getPointsByAngle(
    List<Point> points, Point basePoint) {
  SplayTreeMap<double, List<Point>> map =
      new SplayTreeMap<double, List<Point>>();

  for (Point point in points) {
    if (point != basePoint) {
      // Get the angle;
      double angle = point.getAngle(basePoint);
      map.putIfAbsent(angle, () => []);
      // For list, insertion sort it.
      List<Point> anglePoints = map[angle];
      int index = 0;
      double distance = point.getDistance(basePoint);
      while (index < anglePoints.length) {
        // This can't be equal, otherwise the points would not be unique.
        if (anglePoints[index].getDistance(basePoint) > distance) {
          break;
        }
        index++;
      }
      if (index >= anglePoints.length || index == 0) {
        anglePoints.add(point);
      } else {
        anglePoints.insert(index - 1, point);
      }
    }
  }

  return map;
}

class Point {
  int x, y;

  Point(this.x, this.y);

  double getAngle(Point other) {
    return ((math.atan2(other.x - this.x, other.y - this.y) * 180.0 / math.pi) +
            360) %
        360;
  }

  double getDistance(Point other) {
    return math.sqrt((other.x - this.x) ^ 2 + (other.y - this.y) ^ 2);
  }

  @override
  toString() {
    return "[${this.x}, ${this.y}]";
  }
}
