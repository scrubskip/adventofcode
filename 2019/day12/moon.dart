import "dart:io";
import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  List<String> input = await new File(_argResults.rest[0]).readAsLines();
  List<Moon> moons = input.map(Moon.parse).toList();
  // for (int index = 0; index < 1000; index++) {
  //   runStep(moons);
  // }
  // int totalEnergy = getTotalEnergy(moons);
  // stdout.writeln("$totalEnergy");
  List<int> reps = getReps(moons);
  stdout.writeln("$reps");
}

void runStep(List<Moon> moons) {
  // First find each unique pair of moons
  for (int index = 0; index < moons.length; index++) {
    for (int indexInner = index + 1; indexInner < moons.length; indexInner++) {
      // stdout.writeln("Pair $index $indexInner");
      moons[index].adjustVelocity(moons[indexInner]);
    }
  }
  // Now that all velocities are up to date, update moons.
  moons.forEach((moon) => moon.applyVelocity());
}

int getTotalEnergy(List<Moon> moons) {
  return moons.map((moon) => moon.getTotalEnergy()).reduce((x, y) => x + y);
}

List<int> getReps(List<Moon> moons) {
  // Loop through until we have identified when each axis is the same as the beginning.
  int xStep, yStep, zStep;
  // Make a copy
  List<Moon> startState = moons.map(Moon.fromMoon).toList();
  int step = 1;
  while (xStep == null || yStep == null || zStep == null) {
    runStep(moons);
    int sameXCount = 0;
    int sameYCount = 0;
    int sameZCount = 0;
    for (int index = 0; index < moons.length; index++) {
      // Check a dimension
      if (xStep == null && moons[index].dimensionEqual(startState[index], 0)) {
        sameXCount++;
      }
      if (yStep == null && moons[index].dimensionEqual(startState[index], 1)) {
        sameYCount++;
      }
      if (zStep == null && moons[index].dimensionEqual(startState[index], 2)) {
        sameZCount++;
      }
    }
    if (xStep == null && sameXCount == moons.length) {
      xStep = step;
    }
    if (yStep == null && sameYCount == moons.length) {
      yStep = step;
    }
    if (zStep == null && sameZCount == moons.length) {
      zStep = step;
    }
    step++;
  }
  return [xStep, yStep, zStep];
}

int getGcd(int a, int b) {
  int t;
  while (b != 0) {
    t = b;
    b = a % b;
    a = t;
  }
  return a;
}

class Moon {
  List<int> position;
  List<int> velocity;

  static final RegExp PARSER = new RegExp(r"<x=(-?\d+), y=(-?\d+), z=(-?\d+)>");

  Moon(this.position, this.velocity);

  static Moon parse(String input) {
    var match = PARSER.firstMatch(input);
    if (match != null) {
      return Moon(
          [match.group(1), match.group(2), match.group(3)]
              .map(int.parse)
              .toList(),
          [0, 0, 0]);
    }
    throw new ArgumentError("Could not parse $input");
  }

  static Moon fromMoon(Moon other) {
    return Moon([other.position[0], other.position[1], other.position[2]],
        [other.velocity[0], other.velocity[1], other.velocity[2]]);
  }

  void adjustVelocity(Moon other) {
    for (int index = 0; index < 3; index++) {
      if (position[index] > other.position[index]) {
        velocity[index]--;
        other.velocity[index]++;
      } else if (position[index] < other.position[index]) {
        velocity[index]++;
        other.velocity[index]--;
      }
    }
  }

  void applyVelocity() {
    position[0] += velocity[0];
    position[1] += velocity[1];
    position[2] += velocity[2];
  }

  int getTotalEnergy() {
    return position.reduce((x, y) => x.abs() + y.abs()) *
        velocity.reduce((x, y) => x.abs() + y.abs());
  }

  @override
  String toString() {
    return "pos=$position, vel=$velocity";
  }

  bool dimensionEqual(Moon other, int dimension) {
    return position[dimension] == other.position[dimension] &&
        velocity[dimension] == other.velocity[dimension];
  }
}
