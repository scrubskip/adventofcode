import 'dart:collection';
import "dart:io";
import 'dart:math';

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

SplayTreeMap<double, List<Point>> getPointsByAngle(
    Point basePoint, List<Point> points) {
  SplayTreeMap<double, List<Point>> map =
      new SplayTreeMap<double, List<Point>>();

  for (Point point in points) {
    if (point != basePoint) {
      // Get the angle;
      double angle = basePoint.getAngle(point);
      map.putIfAbsent(angle, () => []);
      // Now do insertion sort based on the manhattan distance.

    }
  }

  return map;
}

class Point {
  int x, y;

  Point(this.x, this.y);

  double getAngle(Point other) {
    return atan2(other.x - this.x, other.y - this.y);
  }

  int getDistance(Point other) {
    return (other.x - this.x).abs() + (other.y - this.y).abs();
  }
}
