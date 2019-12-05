import "dart:io";
import "dart:math";
import "dart:collection";
import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);
  List<String> input = await new File(_argResults.rest[0]).readAsLines();
  List<Nanobot> bots = input.map(Nanobot.parse).toList();
  stdout.writeln(getNeighboringBots(bots));
}

int getNeighboringBots(List<Nanobot> bots) {
  bots.sort((a, b) => b.radius.compareTo(a.radius));
  Nanobot greatest = bots.first;
  int total = 1;
  bots.skip(1).forEach((bot) {
    if (greatest.getDistance(bot.position) <= greatest.radius) {
      total++;
    }
  });
  return total;
}

class Nanobot {
  final List<int> position;
  final int radius;

  static final RegExp PARSE =
      new RegExp(r"pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(-?\d+)");

  Nanobot(this.position, this.radius);

  static Nanobot parse(String input) {
    RegExpMatch match = PARSE.firstMatch(input);
    if (match != null) {
      return Nanobot(
          [match.group(1), match.group(2), match.group(3)]
              .map(int.parse)
              .toList(),
          int.parse(match.group(4)));
    }
    throw new ArgumentError("Couldn't parse $input");
  }

  int getDistance(List<int> otherPosition) {
    return (otherPosition[0] - position[0]).abs() +
        (otherPosition[1] - position[1]).abs() +
        (otherPosition[2] - position[2]).abs();
  }
}
