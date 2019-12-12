import 'dart:collection';
import "dart:io";
import 'dart:math' as math;

import "package:args/args.dart";
import "package:tuple/tuple.dart";

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
    for (Point point in points) {
      double angle = basePoint.getAngle(point);
      angles.add(angle);
      // stdout.writeln("angle $angle $basePoint $point");
    }
    if (angles.length > greatestCount) {
      greatestCount = angles.length;
      greatestPoint = basePoint;
      //stdout.writeln("Found greatest point {$greatestPoint} with $greatestCount");
    }
  }
  stdout.writeln(greatestCount);
  return greatestPoint;
}

List<Point> getVaporizationOrder(List<Point> points, Point basePoint) {
  var pointsByAngle = getPointsByAngle(points, basePoint);
  List<Point> returnPoints = [];
  List<double> angles = pointsByAngle.item1;
  // Find the index of the first item that is up.

  // double angle = pointsByAngle.firstKey();
  // while (angle != null) {
  //   List<Point> anglePoints = pointsByAngle[angle];
  //   // Pop the first one off.
  //   returnPoints.add(anglePoints.removeAt(0));
  //   if (anglePoints.isEmpty) {
  //     pointsByAngle.remove(angle);
  //   }
  //   angle = pointsByAngle.firstKeyAfter(angle);
  //   if (angle == null) {
  //     angle = pointsByAngle.firstKey();
  //   }
  // }
  return returnPoints;
}

Tuple2<List<double>, Map<double, List<Point>>> getPointsByAngle(
    List<Point> points, Point basePoint) {
  Map<double, List<Point>> map = Map<double, List<Point>>();
  Set<double> angles = new Set<double>();
  for (Point point in points) {
    if (point != basePoint) {
      // Get the angle;
      double angle = point.getAngle(basePoint);
      angles.add(angle);
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

  return Tuple2(angles.toList(), map);
}

class Point {
  int x, y;

  Point(this.x, this.y);

  double getAngle(Point other) {
    return (math.pi / 2) - math.atan2(y - other.y, other.x - x);
  }

  double getAngleInDegrees(Point other) {
    return getAngle(other) * 180 / math.pi;
  }

  double getDistance(Point other) {
    return math
        .sqrt((other.x - this.x).abs() ^ 2 + (other.y - this.y).abs() ^ 2);
  }

  @override
  toString() {
    return "[${this.x}, ${this.y}]";
  }

  bool operator ==(o) => o is Point && o.x == x && o.y == y;
  int get hashCode => x.hashCode ^ y.hashCode;
}
